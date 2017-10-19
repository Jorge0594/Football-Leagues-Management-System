package API.Equipo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;

@RestController
@RequestMapping(value = "/equipos")

public class EquipoController {

	public interface RankView extends Equipo.RankAtt {}

	public interface PerfilView extends Equipo.RankAtt, Equipo.PerfilAtt, Jugador.EquipoAtt {}

	public interface JugadorView extends Jugador.PerfilAtt, Jugador.EquipoAtt {}

	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private JugadorRepository jugadorRepository;

	@JsonView(PerfilView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
		equipo.setImagenEquipo("imageTeamDafault.png");
		equipoRepository.save(equipo);
		return new ResponseEntity<Equipo>(equipo, HttpStatus.CREATED);
	}

	@JsonView(RankView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verEquipos() {
		return new ResponseEntity<List<Equipo>>(equipoRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(RankView.class)
	@RequestMapping(value = "/liga/{liga}", method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verEquiposLiga(@PathVariable String liga) {
		List<Equipo> equipos = equipoRepository.findByLigaIgnoreCase(liga);
		if (equipos.isEmpty()) {
			return new ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Equipo>>(equipos, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<Equipo> verEquipoNombre(@PathVariable String nombre) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	@JsonView(JugadorView.class)
	@RequestMapping(value = "/{nombre}/plantilla", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verEquipoPantilla(@PathVariable String nombre) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Jugador>>(equipo.getPlantillaEquipo(), HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> modificarPerfilEquipo(@PathVariable String nombre, @RequestBody Equipo auxEquipo) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		equipo.setNombre(auxEquipo.getNombre());
		equipo.setLiga(auxEquipo.getLiga());
		equipo.setCiudad(auxEquipo.getCiudad());

		for (Jugador jugador : equipo.getPlantillaEquipo()) {
			jugador.setEquipo(auxEquipo.getNombre());
		}

		equipoRepository.save(equipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{nombre}/resultado", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> modificarEquipoPostPartido(@PathVariable String nombre, @RequestBody Equipo auxEquipo) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		equipo.setGoles(auxEquipo.getGoles());
		equipo.setGolesEncajados(auxEquipo.getGolesEncajados());
		equipo.setPartidosEmpatados(auxEquipo.getPartidosEmpatados());
		equipo.setPartidosGanados(auxEquipo.getPartidosGanados());
		equipo.setPartidosJugados(auxEquipo.getPartidosJugados());
		equipo.setPartidosPerdidos(auxEquipo.getPartidosPerdidos());
		equipo.setPuntos(auxEquipo.getPuntos());

		equipoRepository.save(equipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{nombre}/posicion", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> modificarEquipoPosicion(@PathVariable String nombre, @RequestBody Equipo auxEquipo) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		equipo.setPosicion(auxEquipo.getPosicion());

		equipoRepository.save(equipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{nombre}/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> añadirJugadorEquipo(@PathVariable String id, @PathVariable String nombre) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		Jugador jugador = jugadorRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		if (!jugador.getEquipo().equals("")) {
			if (!equipo.getPlantillaEquipo().contains(jugador)) {
				Equipo aux = equipoRepository.findByNombreIgnoreCase(jugador.getEquipo());
				aux.getPlantillaEquipo().remove(jugador);
				equipoRepository.save(aux);
				
				jugador.setEquipo(equipo.getNombre());
				equipo.getPlantillaEquipo().add(jugador);

				jugadorRepository.save(jugador);
				equipoRepository.save(equipo);
			} else {
				return new ResponseEntity<Equipo>(HttpStatus.CONFLICT);
			}
		} else {
			jugador.setEquipo(equipo.getNombre());
			equipo.getPlantillaEquipo().add(jugador);

			jugadorRepository.save(jugador);
			equipoRepository.save(equipo);
		}
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}
	
	@JsonView(JugadorView.class)
	@RequestMapping(value = "/{id}/dorsal/{dorsal}", method = RequestMethod.PUT)
	public ResponseEntity<Jugador>asignarDorsalJugador(@PathVariable(value = "id")String id, @PathVariable (value = "dorsal")int dorsal){
		Jugador jugador = jugadorRepository.findById(id);
		
		if(jugador == null){
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(jugador.getEquipo());
		Jugador aux = jugadorRepository.findByDorsalAndEquipoIgnoreCase(dorsal, equipo.getNombre());
		
		if(aux != null && !jugador.equals(aux)){
			return new ResponseEntity<Jugador>(HttpStatus.CONFLICT);
		}else{
			jugador.setDorsal(dorsal);
			jugadorRepository.save(jugador);
			
			return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
		}
		
	}
	

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{nombre}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Equipo> eliminarJugadorEquipo(@PathVariable String id, @PathVariable String nombre) {
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombre);
		Jugador jugador = jugadorRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		if (equipo.getPlantillaEquipo().contains(jugador)) {
			jugador.setEquipo("");
			equipo.getPlantillaEquipo().remove(jugador);

			jugadorRepository.save(jugador);
			equipoRepository.save(equipo);

			return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
		}
		return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Equipo> eliminarEquipo(@PathVariable String id) {
		Equipo equipo = equipoRepository.findById(id);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		for (Jugador j : equipo.getPlantillaEquipo()) {
			j.setEquipo("");
			jugadorRepository.save(j);
		}

		equipoRepository.delete(equipo);
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}
}
