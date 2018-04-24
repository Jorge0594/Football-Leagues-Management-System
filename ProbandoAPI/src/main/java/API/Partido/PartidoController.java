package API.Partido;

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

import API.Acta.Acta;
import API.Acta.ActaRepository;
import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Estadio.Estadio;
import API.Incidencia.Incidencia;
import API.Incidencia.IncidenciaRepository;
import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;
import API.Liga.Liga;
import API.Liga.LigaRepository;
import API.Sancion.Sancion;
import API.Usuario.UsuarioComponent;

@RestController
@CrossOrigin
@RequestMapping("/partidos")
public class PartidoController {

	public interface PartidoView extends Estadio.BasicoAtt, Estadio.DatosAtt, Partido.InfoAtt, Partido.RestAtt,
			Jugador.EquipoAtt, Jugador.PerfilAtt, Equipo.RankAtt, Sancion.JugadorAtt, Sancion.SancionAtt {
	}

	@Autowired
	private PartidoRepository partidoRepository;
	@Autowired
	private ArbitroRepository arbitroRepository;
	@Autowired
	private ActaRepository actaRepository;
	@Autowired
	private IncidenciaRepository incidenciaRepository;
	@Autowired
	private LigaRepository ligaRepository;
	@Autowired
	private EquipoRepository equipoRepository;

	@JsonView(PartidoView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidos() {
		List<Partido> partidos = partidoRepository.findAll();
		Collections.sort(partidos);
		return new ResponseEntity<List<Partido>>(partidos, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Partido> verPartidoId(@PathVariable String id) {
		Partido entrada = partidoRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/liga/{liga}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosLiga(@PathVariable String liga) {
		List<Partido> entrada = partidoRepository.findByLigaIgnoreCase(liga);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/jornada/{jornada}/{nombreLiga}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosJornada(@PathVariable(value = "jornada") int jornada,
			@PathVariable(value = "nombreLiga") String nombreLiga) {
		List<Partido> entrada = partidoRepository.findByJornadaAndLigaIgnoreCase(jornada, nombreLiga);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NO_CONTENT);
		}
		Collections.sort(entrada);
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/equipoLocal/{equipoLocalId}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosEquipoLocal(@PathVariable String equipoLocalId) {
		List<Partido> entrada = partidoRepository.findByEquipoLocalId(equipoLocalId);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/equipoVisitante/{equipoVisitanteId}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosEquipoVisitante(@PathVariable String equipoVisitanteId) {
		List<Partido> entrada = partidoRepository.findByEquipoVisitanteId(equipoVisitanteId);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/partidosEquipo/{idEquipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosEquipo(@PathVariable String idEquipo) {
		List<Partido> partidosEquipo = partidoRepository.findByEquipoVisitanteIdOrEquipoLocalId(idEquipo, idEquipo);
		Collections.sort(partidosEquipo);
		return new ResponseEntity<List<Partido>>(partidosEquipo, HttpStatus.OK);
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/arbitro/{idArbitro}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosArbitro(@PathVariable String idArbitro) {
		List<Partido> entrada = partidoRepository.findByIdArbitro(idArbitro);
		Collections.sort(entrada);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/arbitro/{idArbitro}/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosArbitroEstado(@PathVariable String idArbitro, @PathVariable String estado) {
		List<Partido> entrada = partidoRepository.findByIdArbitroAndEstadoIgnoreCase(idArbitro,estado);
		Collections.sort(entrada);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/arbitro/{idArbitro}/{idEquipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosArbitroEquipo(@PathVariable String idArbitro, @PathVariable String idEquipo) {
		List<Partido> entrada = partidoRepository.findByIdArbitroAndEquipoLocalIdOrEquipoVisitanteId(idArbitro, idEquipo, idEquipo);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	

	/*@JsonView(PartidoView.class)
	@RequestMapping(value = "/addConvocadoLocal/{id}/{idJugador}", method = RequestMethod.PUT)
	public ResponseEntity<Partido> nuevoConvocadoLocal(@PathVariable String id, @PathVariable String idJugador) {
		Partido entrada = partidoRepository.findById(id);
		// Si el partido no existe.
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		Jugador jugadorEntrada = jugadorRepository.findById(idJugador);
		// Si no existe el jugador
		if (jugadorEntrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		// Si el partido y el jugador existen.
		// Si el usuario conectado es un árbitro.
		if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
			Arbitro arbitroConectado = arbitroRepository
					.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
			// Si el árbitro conectado es el árbitro del partido.
			if (arbitroConectado.getId().equals(entrada.getIdArbitro())) {
				if (!entrada.getConvocadosLocal().contains(jugadorEntrada)) {
					entrada.getConvocadosLocal().add(jugadorEntrada);
				}
				partidoRepository.save(entrada);
				return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
			} else {
				return new ResponseEntity<Partido>(HttpStatus.FORBIDDEN);
			}
		}
		// Si el usuario conectado no es un árbitro.
		else {
			if (!entrada.getConvocadosLocal().contains(jugadorEntrada)) {
				entrada.getConvocadosLocal().add(jugadorEntrada);
			}
			partidoRepository.save(entrada);
			return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
		}
	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/addConvocadoVisitante/{id}/{idJugador}", method = RequestMethod.PUT)
	public ResponseEntity<Partido> nuevoConvocadoVisitante(@PathVariable String id, @PathVariable String idJugador) {
		Partido entrada = partidoRepository.findById(id);
		// Si el partido no existe.
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		Jugador jugadorEntrada = jugadorRepository.findById(idJugador);
		// Si no existe el jugador
		if (jugadorEntrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		// Si el partido y el jugador existen.
		// Si el usuario conectado es un árbitro.
		if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
			Arbitro arbitroConectado = arbitroRepository
					.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
			// Si el árbitro conectado es el árbitro del partido.
			if (arbitroConectado.getId().equals(entrada.getIdArbitro())) {
				if (!entrada.getConvocadosVisitante().contains(jugadorEntrada)) {
					entrada.getConvocadosVisitante().add(jugadorEntrada);
				}
				partidoRepository.save(entrada);
				return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
			} else {
				return new ResponseEntity<Partido>(HttpStatus.FORBIDDEN);
			}
		}
		// Si el usuario conectado no es un árbitro.
		else {
			if (!entrada.getConvocadosVisitante().contains(jugadorEntrada)) {
				entrada.getConvocadosVisitante().add(jugadorEntrada);
			}
			partidoRepository.save(entrada);
			return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
		}
	}*/

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Partido> modificarPartido(@PathVariable String id, @RequestBody Partido partido) {
		Partido entrada = partidoRepository.findById(id);
		Arbitro arbitroDelPartido = arbitroRepository.findById(partido.getIdArbitro());
		//Liga ligaDelPartido = ligaRepository.findByNombreIgnoreCase(partido.getLiga());
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		} else {
			/*if (ligaDelPartido == null) {
				partido.setLiga("");
			} else {
				// Borra el partido de la anterior Liga y lo añade a la nueva
				// Liga.
				if (!ligaDelPartido.getNombre().equals(entrada.getLiga())) {
					ligaDelPartido.getPartidos().add(partido);
					ligaRepository.save(ligaDelPartido);
				}
			}
			Liga antigua = ligaRepository.findByNombreIgnoreCase(entrada.getLiga());
			if (antigua != null) {
				antigua.getPartidos().remove(entrada);
				ligaRepository.save(antigua);
			}*/
			// Borra el partido del anterior árbitro y lo añade al nuevo
			// árbitro.
			Equipo equipoLocal = equipoRepository.findById(partido.getEquipoLocalId());
			Equipo equipoVisitante = equipoRepository.findById(partido.getEquipoVisitanteId());
			
			if(equipoLocal == null || equipoVisitante == null){
				return new ResponseEntity<Partido>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			if (arbitroDelPartido == null) {
				partido.setIdArbitro("");
			} else {
				if (!arbitroDelPartido.getId().equals(entrada.getIdArbitro())) {
					arbitroDelPartido.getPartidosArbitrados().add(partido);
					arbitroRepository.save(arbitroDelPartido);
				}
			}
			Arbitro antiguo = arbitroRepository.findById(entrada.getIdArbitro());
			if (antiguo != null) {
				antiguo.getPartidosArbitrados().remove(entrada);
				arbitroRepository.save(antiguo);
			}

			partido.setId(entrada.getId());
			//Se asegura que el nombre del equipo y el ID pertenezcan al mismo equipo
			partido.setEquipoLocalNombre(equipoLocal.getNombre());
			partido.setEquipoLocalEscudo(equipoLocal.getImagenEquipo());
			partido.setEquipoVisitanteNombre(equipoVisitante.getNombre());
			partido.setEquipoVisitanteEscudo(equipoVisitante.getImagenEquipo());
			partidoRepository.save(partido);
			return new ResponseEntity<Partido>(partido, HttpStatus.OK);
		}
	}

	@JsonView(PartidoView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Partido> crearPartido(@RequestBody Partido partido) {
		Arbitro arbitroDelPartido = arbitroRepository.findById(partido.getIdArbitro());
		if (arbitroDelPartido == null) {
			partido.setIdArbitro("");
		}
		Equipo equipoLocal = equipoRepository.findById(partido.getEquipoLocalId());
		Equipo equipoVisitante = equipoRepository.findById(partido.getEquipoVisitanteId());
		
		if(equipoLocal == null || equipoVisitante == null){
			return new ResponseEntity<Partido>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		partido.setId(null);
		//Se asegura que el nombre del equipo y el ID pertenezcan al mismo equipo
		partido.setEquipoLocalNombre(equipoLocal.getNombre());
		partido.setEquipoVisitanteNombre(equipoVisitante.getNombre());
		partido.setEquipoLocalEscudo(equipoLocal.getImagenEquipo());
		partido.setEquipoVisitanteEscudo(equipoVisitante.getImagenEquipo());
		if (ligaRepository.findByNombreIgnoreCase(partido.getLiga()) == null) {
			partido.setLiga("");
			//partidoRepository.save(partido);
		} /*else {
			Liga liga = ligaRepository.findByNombreIgnoreCase(partido.getLiga());
			if (liga == null) {
				return new ResponseEntity<Partido>(HttpStatus.NO_CONTENT);
			}
			partido.setLiga(liga.getNombre());
			partidoRepository.save(partido);
			liga.getPartidos().add(partido);
			Collections.sort(liga.getPartidos());
			ligaRepository.save(liga);
		}*/
		
		partidoRepository.save(partido);
		if (arbitroDelPartido != null) {
			arbitroDelPartido.getPartidosArbitrados().add(partido);
			arbitroRepository.save(arbitroDelPartido);
		}
		return new ResponseEntity<Partido>(partido, HttpStatus.CREATED);

	}

	@JsonView(PartidoView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Partido> eliminarPartidoId(@PathVariable String id) {
		Partido entrada = partidoRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		// No está permitido borrar un partido si tiene acta
		if ((entrada.getIdActa() != null)) {
			if (!entrada.getIdActa().equals("")) {
				return new ResponseEntity<Partido>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		Arbitro arbitroDelPartido = arbitroRepository.findById(entrada.getIdArbitro());
		List<Incidencia> incidenciasDelPartido = incidenciaRepository.findByIdPartido(entrada.getId());
		//Liga ligaDelPartido = ligaRepository.findByNombreIgnoreCase(entrada.getLiga());
		if (arbitroDelPartido != null) {
			// Elimina el partido de los partidos arbitrados del árbitro.
			arbitroDelPartido.getPartidosArbitrados().remove(entrada);
			arbitroRepository.save(arbitroDelPartido);
		}
		if (!incidenciasDelPartido.isEmpty()) {
			// Elimina el partido de cada incidencia.
			for (Incidencia incidencia : incidenciasDelPartido) {
				incidencia.setIdPartido("");
				incidenciaRepository.save(incidencia);
			}
		}
		/*if (ligaDelPartido != null) {
			// Elimina el partido de su liga.
			if (!ligaDelPartido.getPartidos().isEmpty()) {
				ligaDelPartido.getPartidos().remove(entrada);
				ligaRepository.save(ligaDelPartido);
			}
		}*/
		partidoRepository.delete(entrada);
		return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
	}

}