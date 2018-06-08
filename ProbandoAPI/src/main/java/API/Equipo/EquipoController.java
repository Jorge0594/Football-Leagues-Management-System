package API.Equipo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import API.Images.ImageService;
import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;
import API.Liga.Liga;
import API.Liga.LigaRepository;
import API.Sancion.Sancion;
import API.Usuario.UsuarioComponent;
import API.UsuarioTemporal.UsuarioTemporal;
import API.UsuarioTemporal.UsuarioTemporalRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/equipos")

public class EquipoController {

	public interface RankView extends Equipo.RankAtt {
	}

	public interface PerfilView extends Equipo.RankAtt, Equipo.PerfilAtt, Jugador.EquipoAtt {
	}

	public interface JugadorView extends Jugador.PerfilAtt, Jugador.EquipoAtt {
	}

	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private LigaRepository ligaRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	@Autowired
	private UsuarioTemporalRepository temporalRepository;
	@Autowired
	private ImageService imageService;

	@JsonView(PerfilView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
		if (equipoRepository.findByNombreIgnoreCase(equipo.getNombre()) != null) {
			return new ResponseEntity<Equipo>(HttpStatus.NOT_ACCEPTABLE);
		}
		equipo.setAceptado(true);
		equipo.setGoles(0);
		equipo.setGolesEncajados(0);
		equipo.setPartidosEmpatados(0); 
		equipo.setPartidosGanados(0);
		equipo.setPartidosPerdidos(0);
		equipo.setPartidosJugados();
		equipo.setPuntos();
		equipo.setId(null);
		equipo.setPlantillaEquipo(new ArrayList<Jugador>());
		
		Liga ligaEquipo = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
		
		equipo.setPosicion(ligaEquipo.getClasificacion().size()+1);
		equipoRepository.save(equipo);
		
		ligaEquipo.getClasificacion().add(equipo);
		ligaRepository.save(ligaEquipo);
		
		return new ResponseEntity<Equipo>(equipo, HttpStatus.CREATED);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/temporal", method = RequestMethod.POST)
	public ResponseEntity<Equipo> crearEquipoTemporal(@RequestBody Equipo equipo) {
		UsuarioTemporal usuario = temporalRepository
				.findByNombreUsuarioIgnoreCase(usuarioComponent.getLoggedUser().getNombreUsuario());
		if (usuario == null) {
			return new ResponseEntity<Equipo>(HttpStatus.UNAUTHORIZED);
		}
		equipo.setId(null);
		equipo.setLiga(usuario.getLiga());
		equipo.setAceptado(false);
		equipo.setImagenEquipo("shield.png");
		
		equipoRepository.save(equipo);
		
		List<Jugador>listaJugadores = new ArrayList<>();
		
		for(Jugador jugador: equipo.getPlantillaEquipo()){
			
			jugador.setEquipo(equipo.getId());
			jugador.setAceptado(false);
			jugador.setId(null);
			jugador.setFotoJugador("defaultProfile.jpg");
			jugador.setLiga("");
			jugador.setGoles(0);
			jugador.setTarjetasAmarillas(0);
			jugador.setTarjetasRojas(0);
			jugador.setSanciones(new ArrayList<Sancion>());
			
			jugadorRepository.save(jugador);
			
			listaJugadores.add(jugador);
			
		}
		
		equipo.setPlantillaEquipo(listaJugadores);
		equipoRepository.save(equipo);
		
		usuario.setEquipoId(equipo.getId());
		temporalRepository.save(usuario);

		return new ResponseEntity<Equipo>(HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
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

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/validar/{nombre}/{liga}", method = RequestMethod.GET)
	public ResponseEntity<Equipo> disponibleNombreEquipoLiga(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "liga") String liga) {
		Equipo equipo = equipoRepository.findByLigaAndNombreAllIgnoreCase(liga, nombre);
		if (equipo != null) {
			return new ResponseEntity<Equipo>(HttpStatus.CONFLICT);
		}

		return new ResponseEntity<Equipo>(HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<Equipo> verEquipoId(@PathVariable String id) {
		Equipo equipo = equipoRepository.findById(id);
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
	@RequestMapping(value = "/imagen/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Equipo>cambiarImagenEquipo(@PathVariable String id, @RequestParam("File")MultipartFile file){
		Equipo equipo = equipoRepository.findById(id);
		if(equipo == null){
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		
		if(imageService.getImg().cambiarFoto(equipo.getNombre() + equipo.getLiga(), file)){
			equipo.setImagenEquipo(imageService.getImg().getFileName());
			
			equipoRepository.save(equipo);
		} else {
			return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
		
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> modificarPerfilEquipo(@PathVariable String id, @RequestBody Equipo auxEquipo) {
		Equipo equipo = equipoRepository.findById(id);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		// los puntos se recalculan automaticamente por el número de partidos
		// ganados y empatados.
		equipo.setNombre(auxEquipo.getNombre());
		equipo.setCiudad(auxEquipo.getCiudad());
		equipo.setPosicion(auxEquipo.getPosicion());
		equipo.setGoles(auxEquipo.getGoles());
		equipo.setGolesEncajados(auxEquipo.getGolesEncajados());
		equipo.setPartidosGanados(auxEquipo.getPartidosGanados());
		equipo.setPartidosPerdidos(auxEquipo.getPartidosPerdidos());
		equipo.setPartidosEmpatados(auxEquipo.getPartidosEmpatados());

		equipoRepository.save(equipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{idEquipo}/jugador/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> añadirJugadorEquipo(@PathVariable(value = "id") String id,
			@PathVariable(value = "idEquipo") String idEquipo) {
		Equipo equipo = equipoRepository.findById(idEquipo);
		Jugador jugador = jugadorRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		if (!equipo.getLiga().equals("") && equipo.isAceptado()) {
			jugador.setLiga(equipo.getLiga());
		}
		if (!jugador.getEquipo().equals("")) {
			if (!equipo.getPlantillaEquipo().contains(jugador)) {
				Equipo aux = equipoRepository.findById(jugador.getEquipo());
				/*
				 * if (!aux.getLiga().equals(equipo.getLiga()) &&
				 * (!aux.getLiga().equals("")) && (aux.isAceptado())) { Liga
				 * ligaAux =
				 * ligaRepository.findByNombreIgnoreCase(aux.getLiga());
				 * ligaAux.getGoleadores().remove(jugador);
				 * ligaRepository.save(ligaAux); }
				 */
				aux.getPlantillaEquipo().remove(jugador);
				equipoRepository.save(aux);
			} else {
				return new ResponseEntity<Equipo>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		jugador.setEquipo(equipo.getId());
		equipo.getPlantillaEquipo().add(jugador);

		jugadorRepository.save(jugador);
		equipoRepository.save(equipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{idEquipo}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Equipo> eliminarJugadorEquipo(@PathVariable String id, @PathVariable String idEquipo) {
		Equipo equipo = equipoRepository.findById(idEquipo);
		Jugador jugador = jugadorRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		if (equipo.getPlantillaEquipo().contains(jugador)) {

			equipo.getPlantillaEquipo().remove(jugador);
			jugador.setEquipo("");
			jugador.setLiga("");

			jugadorRepository.save(jugador);
			equipoRepository.save(equipo);

			Liga liga = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
			if (liga != null && liga.getGoleadores().contains(jugador)) {// Elimina
																			// a
																			// el
																			// jugador
																			// si
																			// se
																			// encuentra
																			// entre
																			// los
																			// goleadores
				List<Jugador> jugadores = jugadorRepository.findByLigaIgnoreCase(liga.getNombre());
				liga.crearGoleadores(jugadores);
				ligaRepository.save(liga);
			}

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

		if (!equipo.getLiga().equals("") && equipo.isAceptado()) {
			Liga liga = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
			liga.getGoleadores().removeAll(equipo.getPlantillaEquipo());
			liga.getClasificacion().remove(equipo);
			ligaRepository.save(liga);
		}
		
		//Testing
		
		UsuarioTemporal usuarioTemporal = temporalRepository.findByEquipoId(id);
		if(usuarioTemporal != null){
			System.out.println("Borrado de jugadores del equipo de usuario temporal");
			usuarioTemporal.setEquipoId("");
			temporalRepository.save(usuarioTemporal);
			
			for(Jugador j : equipo.getPlantillaEquipo()){
				jugadorRepository.delete(j);
			}
		} else {
			if(equipo.getPlantillaEquipo() != null){
				for (Jugador j : equipo.getPlantillaEquipo()) {
					j.setEquipo("");
					jugadorRepository.save(j);
				}
			}
		}
	
		equipoRepository.delete(equipo);
		
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}
}
