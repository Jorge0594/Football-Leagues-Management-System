package API.Acta;


import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.itextpdf.text.DocumentException;

import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Estadio.Estadio;
import API.Grupo.Grupo;
import API.Grupo.GrupoRepository;
import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Pdfs.PdfCreator;
import API.Usuario.UsuarioComponent;
import API.Vistas.VistaGrupo;
import API.Vistas.VistaJugador;
import API.Vistas.VistaTemporada;
import API.Incidencia.Incidencia;
import API.Incidencia.IncidenciaRepository;
import API.Sancion.Sancion;
import API.Sancion.SancionRepository;

@RestController
@CrossOrigin
@RequestMapping("/actas")
public class ActaController {

	public interface ActaView
			extends Acta.ActaAtt, Equipo.RankAtt, Jugador.EquipoAtt, Jugador.PerfilAtt, Jugador.ClaveAtt, Estadio.BasicoAtt, Arbitro.ActaAtt, Incidencia.IncidenciaAtt, Equipo.PerfilAtt, Sancion.JugadorAtt, Sancion.SancionAtt, VistaGrupo.VistaGrupoAtt, VistaTemporada.VistaTemporadaAtt, VistaJugador.VistaJugadorAtt {
	}

	@Autowired
	private ActaRepository actaRepository;
	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private PartidoRepository partidoRepository;
	@Autowired
	private ArbitroRepository arbitroRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	@Autowired
	private PdfCreator pdfCreator;
	@Autowired
	private IncidenciaRepository incidenciaRepository;
	@Autowired
	private SancionRepository sancionRepository;
	
	@JsonView(ActaView.class)
	@RequestMapping(value = "/pendientes", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActasPendientes() {
		List<Acta> entrada = actaRepository.findByAceptadaFalse();
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}
	
	@JsonView(ActaView.class)
	@RequestMapping(value = "/pendientes/grupo/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActasPendientesGrupo(@PathVariable String idGrupo) {
		List<Acta> entrada = actaRepository.findByAceptadaFalseAndGrupoIdGrupo(idGrupo);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}
	
	
	@JsonView(ActaView.class)
	@RequestMapping(value = "/aceptadas", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActasAceptadas() {
		List<Acta> entrada = actaRepository.findByAceptadaTrue();
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}
	
	@JsonView(ActaView.class)
	@RequestMapping(value = "/aceptadas/grupo/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActasAceptadasGrupo(@PathVariable String idGrupo){
		List<Acta> entrada = actaRepository.findByAceptadaTrueAndGrupoIdGrupo(idGrupo);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}
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
	@RequestMapping(value = "/generaPdf/{id}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> imprimiPdf(@PathVariable String id) {
		Acta entrada = actaRepository.findById(id);
		Partido partidoDelActa = partidoRepository.findById(entrada.getIdPartido());
		if ((entrada == null) || (partidoDelActa == null)) {
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}
		else {
		try {
			String nFichero=pdfCreator.crearPdf(entrada);
			if (nFichero!=null) {
			File fichero = new File("src/main/resources/static/actasGeneradas/"+partidoDelActa.getLiga()+"/"+nFichero);
			InputStream ficheroStream = new FileInputStream(fichero);
			  HttpHeaders headers = new HttpHeaders();
			  headers.setContentType(MediaType.parseMediaType("application/pdf"));
			  headers.add("Access-Control-Allow-Origin", "*");
			  headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			  headers.add("Access-Control-Allow-Headers", "Content-Type");
			  headers.add("Content-Disposition", "filename=" + nFichero);
			  headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			  headers.add("Pragma", "no-cache");
			  headers.add("Expires", "0");

			  ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
			    new InputStreamResource(ficheroStream), headers, HttpStatus.OK);
			  return response;
			}
			else {
				return new ResponseEntity<InputStreamResource>(HttpStatus.NO_CONTENT);
			}
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<InputStreamResource>(HttpStatus.BAD_REQUEST);
		}
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
	@RequestMapping(value = "/arbitro/{idArbitro}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaArbitro(@PathVariable String idArbitro) {
		List<Acta> entrada = actaRepository.findByIdArbitro(idArbitro);
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
// Si fueran necesarios modificar para que busquen por idEquipoLocal y idEquipOVisitante
	/*@JsonView(ActaView.class)
	@RequestMapping(value = "/equipoLocal/{equipoLocal}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoLocal(@PathVariable String equipoLocal) {
		List<Acta> entrada = actaRepository.findByEquipoLocalId(new ObjectId(equipoLocal));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}*/

	/*@JsonView(ActaView.class)
	@RequestMapping(value = "/equipoVisitante/{equipoVisitante}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoVisitante(@PathVariable String equipoVisitante) {
		List<Acta> entrada = actaRepository.findByEquipoVisitanteId(new ObjectId(equipoVisitante));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}*/
	@JsonView(ActaView.class)
	@RequestMapping(value = "/actualizar/{idActa}", method = RequestMethod.PUT)
	public ResponseEntity<Acta> actualizarActa(@PathVariable(value = "idActa") String idActa, @RequestBody Acta actaEntrada) {
		Acta acta = actaRepository.findById(idActa);
		if (acta == null || acta.isAceptada() || acta.getIdEquipoLocal() == null || acta.getIdEquipoVisitante() == null){
			return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
		}
		acta.setGolesLocal(actaEntrada.getGolesLocal());
		acta.setGolesVisitante(actaEntrada.getGolesVisitante());
		acta.setIncidencias(actaEntrada.getIncidencias());
		if(acta.getIncidencias() != null && !acta.getIncidencias().isEmpty()){
			Collections.sort(acta.getIncidencias());	
		}
		acta.setObservaciones(actaEntrada.getObservaciones());
		actaRepository.save(acta);
		return new ResponseEntity<Acta>(acta, HttpStatus.OK);
	}
	
	@JsonView(ActaView.class)
	@RequestMapping(value = "/aceptar/{idActa}", method = RequestMethod.PUT)
	public ResponseEntity<Acta> aceptarActa(@PathVariable(value = "idActa") String idActa) {
		Acta acta = actaRepository.findById(idActa);
		if (acta == null || acta.isAceptada() || acta.getIdEquipoLocal() == null || acta.getIdEquipoVisitante() == null){
			return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
		}
		Partido partido = partidoRepository.findById(acta.getIdPartido());
		Grupo grupo = grupoRepository.findById(partido.getGrupo().getIdGrupo());
		if (grupo == null){
			return new ResponseEntity<Acta>(HttpStatus.BAD_GATEWAY);
		}
		actualizarEquipos(acta);
		Collections.sort(grupo.getClasificacion());
		actualizarJugadores(acta, grupo);
		actualizarPartido(acta);
		acta.setAceptada(true);
		grupoRepository.save(grupo);
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
			
			if(entrada.getIncidencias() != null && !entrada.getIncidencias().isEmpty()){
				Collections.sort(entrada.getIncidencias());
			}
			
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
			if(entrada.getIncidencias() != null && !entrada.getIncidencias().isEmpty()){
				Collections.sort(entrada.getIncidencias());
			}
			if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
				if (!arbitroConectado.getId().equals(acta.getIdArbitro())) {
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
					acta.setIdCapitanLocal(entrada.getIdCapitanLocal());
					acta.setIdCapitanVisitante(entrada.getIdCapitanVisitante());
					acta.setIdsPorterosLocal(entrada.getIdsPorterosLocal());
					acta.setIdsPorterosVisitante(entrada.getIdsPorterosVisitante());
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


	private void actualizarEquipos(Acta acta) {

		Equipo local = equipoRepository.findById(acta.getIdEquipoLocal());
		Equipo visitante = equipoRepository.findById(acta.getIdEquipoVisitante());
		
		if(acta.getObservaciones().contains("No presentado equipo local")) {
			visitante.setPartidosGanados(visitante.getPartidosGanados() + 1);
			local.setPartidosPerdidos(local.getPartidosPerdidos() + 1);
			visitante.setPuntos();
			visitante.setPartidosJugados();
			local.setPartidosJugados();
		} else if (acta.getObservaciones().contains("No presentado equipo visitante")) {
			local.setPartidosGanados(local.getPartidosGanados() + 1);
			visitante.setPartidosPerdidos(visitante.getPartidosPerdidos() + 1);
			local.setPuntos();
			local.setPartidosJugados();
			visitante.setPartidosJugados();
		}else {

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
		}
		equipoRepository.save(local);
		equipoRepository.save(visitante);
	}
	
	private void actualizarJugadores(Acta acta, Grupo grupo) {
	
		for(Incidencia incidencia: acta.getIncidencias()) {
			if(!incidencia.getIdJugador().equals("000")) {
				Jugador jugador = jugadorRepository.findById(incidencia.getIdJugador());
				if (incidencia.getTipo().equals("GOL")) {
					jugador.setGoles(jugador.getGoles()+1);
				}else if (incidencia.getTipo().equals("AMARILLA")) {
					jugador.setTarjetasAmarillas(jugador.getTarjetasAmarillas()+1);
				}else {
					jugador.setTarjetasRojas(jugador.getTarjetasRojas()+1);
				}
				incidenciaRepository.save(incidencia);
				jugadorRepository.save(jugador);
			}
		}
		
		grupoRepository.save(grupo);
		
		List<Sancion> sancionesActivas = sancionRepository.findByEnVigorTrue();
		List<String> jugadoresId = new ArrayList<String>();
		Equipo local = equipoRepository.findById(acta.getIdEquipoLocal());
		Equipo visitante = equipoRepository.findById(acta.getIdEquipoVisitante());
		
		for(Jugador jugador: local.getPlantillaEquipo()) {
			jugadoresId.add(jugador.getId());
		}
		
		for(Jugador jugador: visitante.getPlantillaEquipo()) {
			jugadoresId.add(jugador.getId());
		}
		
		for(Sancion sancion: sancionesActivas) {
			for(String idJugador: jugadoresId) {
				if(sancion.getSancionadoId().equals(idJugador)) {
					sancion.cumplirPartidoSancion();
					if(sancion.getPartidosRestantes()==0) 
						sancion.setEnVigor(false);
					sancionRepository.save(sancion);
				}
			}
		}
	}
	
	private void actualizarPartido(Acta acta) {
		Partido partido = partidoRepository.findById(acta.getIdPartido());
		partido.setEstado("Disputado");
		partido.setGolesLocal(acta.getGolesLocal());
		partido.setGolesVisitante(acta.getGolesVisitante());
		partido.setIncidencias(incidenciaRepository.findByIdPartidoIgnoreCase(partido.getId()));
		partido.setIdActa(acta.getId());
		partidoRepository.save(partido);
	}
}
