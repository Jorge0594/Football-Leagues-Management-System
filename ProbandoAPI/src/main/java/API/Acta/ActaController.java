package API.Acta;

import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Estadio.Estadio;
import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;
import API.Liga.Liga;
import API.Liga.LigaRepository;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Usuario.UsuarioComponent;

@RestController
@CrossOrigin
@RequestMapping("/actas")
public class ActaController {

	public interface ActaView
			extends Acta.ActaAtt, Equipo.RankAtt, Jugador.EquipoAtt, Estadio.BasicoAtt, Arbitro.ActaAtt {
	}

	@Autowired
	ActaRepository actaRepository;
	@Autowired
	EquipoRepository equipoRepository;
	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	LigaRepository ligaRepository;
	@Autowired
	PartidoRepository partidoRepository;
	@Autowired
	ArbitroRepository arbitroRepository;
	@Autowired
	UsuarioComponent usuarioComponent;

	@JsonView(ActaView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActas() {
		return new ResponseEntity<List<Acta>>(actaRepository.findAll(), HttpStatus.OK);

	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Acta> verActaId(@PathVariable String id) {
		Acta entrada = actaRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/partido/{idPartido}")
	public ResponseEntity<Acta> verActaIdPartido(@PathVariable String idPartido) {
		Acta entrada = actaRepository.findByIdPartidoIgnoreCase(idPartido);
		if (entrada == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/arbitro/{arbitro}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaArbitro(@PathVariable String arbitro) {
		List<Acta> entrada = actaRepository.findByArbitroId(new ObjectId(arbitro));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/fecha/{fecha}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaFecha(@PathVariable String fecha) {
		List<Acta> entrada = actaRepository.findByFecha(fecha);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/equipoLocal/{equipoLocal}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoLocal(@PathVariable String equipoLocal) {
		List<Acta> entrada = actaRepository.findByEquipoLocalId(new ObjectId(equipoLocal));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/equipoVisitante/{equipoVisitante}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoVisitante(@PathVariable String equipoVisitante) {
		List<Acta> entrada = actaRepository.findByEquipoVisitanteId(new ObjectId(equipoVisitante));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}
	
	@JsonView(ActaView.class)
	@RequestMapping(value = "/aceptar/{idActa}", method = RequestMethod.PUT)
	public ResponseEntity<Acta> aceptarActa(@PathVariable(value = "idActa") String idActa) {
		Acta acta = actaRepository.findById(idActa);
		if (acta == null || acta.isAceptada() || acta.getEquipoLocal() == null || acta.getEquipoVisitante() == null){
			return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
		}
		acta.setAceptada(true);
		
		Liga liga = ligaRepository.findByNombreIgnoreCase(acta.getEquipoLocal().getLiga());
		if (liga == null){
			return new ResponseEntity<Acta>(HttpStatus.BAD_GATEWAY);
		}
		
		actualizarEquipos(acta);
		
		Collections.sort(liga.getClasificacion());
		//falta actualizar a los jugadores
		//Por cada jugador que haya metido gol llamar a reordenarGoleadores(jugador) o reordenarGoleadoresLista(List<Jugador>jugadores)
		
		ligaRepository.save(liga);
		actaRepository.save(acta);
		return new ResponseEntity<Acta>(acta, HttpStatus.OK);
	}

	

	@JsonView(ActaView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Acta> crearActa(@RequestBody Acta entrada) {
		Partido partidoDelActa = partidoRepository.findById(entrada.getIdPartido());
		// Si el partido cuyo acta estamos creando no existe.
		if (partidoDelActa == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			// Si el usuario conectado es un árbitro.
			if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
				Arbitro arbitroConectado = arbitroRepository.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
				// Si un árbitro intenta crear un acta que no sea de un partido
				// que ha
				// arbitrado.
				if (!arbitroConectado.getPartidosArbitrados().contains(partidoDelActa)) {
					return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
				} else {
					entrada.setId(null);
					actaRepository.save(entrada);
					Acta actaConId = actaRepository.findById(entrada.getId());
					partidoDelActa.setIdActa(actaConId.getId());
					partidoRepository.save(partidoDelActa);
					return new ResponseEntity<Acta>(entrada, HttpStatus.CREATED);
				}
			}
			// Si el usuario conectado es un miembro del comité o un
			// administrador.
			else {
				entrada.setId(null);
				actaRepository.save(entrada);
				Acta actaConId = actaRepository.findById(entrada.getId());
				partidoDelActa.setIdActa(actaConId.getId());
				partidoRepository.save(partidoDelActa);
				return new ResponseEntity<Acta>(entrada, HttpStatus.CREATED);
			}
		}
	}

	@JsonView(ActaView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Acta> modificarActa(@PathVariable String id, @RequestBody Acta entrada) {
		Acta acta = actaRepository.findById(id);
		if (acta == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		} else {
			Partido partidoDelActa = partidoRepository.findById(acta.getIdPartido());
			Arbitro arbitroConectado = arbitroRepository
					.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
			// Si el usuario conectado es un árbitro
			if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
				if (!arbitroConectado.getId().equals(acta.getArbitro().getId())) {
					return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
				} else {
					acta.setFecha(entrada.getFecha());
					acta.setHora(entrada.getHora());
					acta.setConvocadosLocal(entrada.getConvocadosLocal());
					acta.setConvocadosVisitante(entrada.getConvocadosVisitante());
					acta.setGolesLocal(entrada.getGolesLocal());
					acta.setGolesVisitante(entrada.getGolesVisitante());
					acta.setIncidencias(entrada.getIncidencias());
					acta.setObservaciones(entrada.getObservaciones());
					actaRepository.save(acta);
					partidoDelActa.setIdActa(acta.getId());
					partidoRepository.save(partidoDelActa);
					return new ResponseEntity<Acta>(acta, HttpStatus.OK);
				}

			}
			// Si el usuario conectado es un miembro del Comité o un árbitro
			else {
				entrada.setId(acta.getId());
				actaRepository.save(entrada);
				partidoDelActa.setIdActa(entrada.getId());
				partidoRepository.save(partidoDelActa);
				return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
			}
		}
	}
	
	public void actualizarEquipos(Acta acta) {

		Equipo local = equipoRepository.findById(acta.getEquipoLocal().getId());
		Equipo visitante = equipoRepository.findById(acta.getEquipoVisitante().getId());

		if (acta.getGolesLocal() > acta.getGolesVisitante()) {

			local.setPartidosGanados(local.getPartidosGanados() + 1);
			local.setGoles(local.getGoles() + acta.getGolesLocal());
			local.setGolesEncajados(local.getGolesEncajados() + acta.getGolesVisitante());
			local.setPuntos();
			local.setPartidosJugados();
			
			visitante.setPartidosPerdidos(visitante.getPartidosPerdidos() + 1);
			visitante.setGoles(visitante.getGoles() + acta.getGolesVisitante());
			visitante.setGolesEncajados(visitante.getGolesEncajados() + acta.getGolesLocal());
			visitante.setPartidosJugados();

		} else if (acta.getGolesLocal() < acta.getGolesVisitante()) {

			visitante.setPartidosGanados(visitante.getPartidosGanados() + 1);
			visitante.setGoles(visitante.getGoles() + acta.getGolesVisitante());
			visitante.setGolesEncajados(visitante.getGolesEncajados() + acta.getGolesLocal());
			visitante.setPuntos();
			visitante.setPartidosJugados();
			
			local.setPartidosPerdidos(local.getPartidosPerdidos() + 1);
			local.setGoles(local.getGoles() + acta.getGolesLocal());
			local.setGolesEncajados(local.getGolesEncajados() + acta.getGolesVisitante());
			local.setPartidosJugados();

		} else {

			local.setPartidosEmpatados(local.getPartidosEmpatados() + 1);
			local.setGoles(local.getGoles() + acta.getGolesLocal());
			local.setGolesEncajados(local.getGolesEncajados() + acta.getGolesVisitante());
			local.setPuntos();
			local.setPartidosJugados();

			visitante.setPartidosEmpatados(visitante.getPartidosEmpatados() + 1);
			visitante.setGoles(visitante.getGoles() + acta.getGolesVisitante());
			visitante.setGolesEncajados(visitante.getGolesEncajados() + acta.getGolesLocal());
			visitante.setPuntos();
			visitante.setPartidosJugados();
		}
		
		equipoRepository.save(local);
		equipoRepository.save(visitante);
	}
}
