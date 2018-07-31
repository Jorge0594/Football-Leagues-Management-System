package API.Equipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

import API.Grupo.Grupo;
import API.Grupo.GrupoRepository;
import API.Images.ImageService;
import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;
import API.MongoBulk.MongoBulk;
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

	public interface PerfilView extends Equipo.RankAtt, Equipo.PerfilAtt, Jugador.EquipoAtt, Jugador.PerfilAtt, Sancion.SancionAtt, Sancion.JugadorAtt {
	}

	public interface JugadorView extends Jugador.PerfilAtt, Jugador.EquipoAtt {
	}

	private Object lock = new Object();

	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	@Autowired
	private UsuarioTemporalRepository temporalRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private MongoBulk mongoBulk;

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

		Grupo grupoEquipo = grupoRepository.findByNombreIgnoreCase(equipo.getGrupo());

		equipo.setPosicion(grupoEquipo.getClasificacion().size() + 1);
		equipoRepository.save(equipo);

		grupoEquipo.getClasificacion().add(equipo);
		grupoRepository.save(grupoEquipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.CREATED);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/temporal", method = RequestMethod.POST)
	public ResponseEntity<Equipo> crearEquipoTemporal(@RequestBody Equipo equipo) {
		UsuarioTemporal usuario = temporalRepository.findByNombreUsuarioIgnoreCase(usuarioComponent.getLoggedUser().getNombreUsuario());
		if (usuario == null) {
			return new ResponseEntity<Equipo>(HttpStatus.UNAUTHORIZED);
		}
		equipo.setId(null);
		equipo.setGrupo(usuario.getGrupo());
		equipo.setAceptado(false);
		equipo.setImagenEquipo("shield.png");

		equipoRepository.save(equipo);

		List<Jugador> listaJugadores = new ArrayList<>();

		for (Jugador jugador : equipo.getPlantillaEquipo()) {

			jugador.setEquipo(equipo.getId());
			jugador.setAceptado(false);
			jugador.setId(null);
			jugador.setFotoJugador("defaultProfile.jpg");
			jugador.setGrupo("");
			jugador.setGoles(0);
			jugador.setTarjetasAmarillas(0);
			jugador.setTarjetasRojas(0);
			jugador.setEstado("");
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
	@RequestMapping(value = "/grupo/{grupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verEquiposGrupo(@PathVariable String grupo) {
		List<Equipo> equipos = equipoRepository.findByGrupoIgnoreCase(grupo);
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
	@RequestMapping(value = "/validar/{nombre}/{grupo}", method = RequestMethod.GET)
	public ResponseEntity<Equipo> disponibleNombreEquipoGrupo(@PathVariable(value = "nombre") String nombre, @PathVariable(value = "grupo") String grupo) {
		Equipo equipo = equipoRepository.findByGrupoAndNombreAllIgnoreCase(grupo, nombre);
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

	@SuppressWarnings("unchecked")
	@JsonView(PerfilView.class)
	@RequestMapping(value = "/{id}/temporal", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> modificarEquipoTemporal(@PathVariable String id, @RequestBody String requestBody) {
		Equipo equipo = equipoRepository.findById(id);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
		}
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonRequest = (JSONObject) parser.parse(requestBody);
			JSONArray nuevosJugadores = (JSONArray) jsonRequest.get("newPlayers");
			JSONArray jugadoresModificados = (JSONArray) jsonRequest.get("modifyPlayers");
			List<String> jugadoresEliminados = (List<String>) jsonRequest.get("removedPlayers");

			actualizarJugadores(jugadoresModificados, equipo);
			if (nuevosJugadores.size() > 0)
				crearJugadores(nuevosJugadores, equipo);
			if (jugadoresEliminados.size() > 0)
				eliminarJugadores(jugadoresEliminados, equipo);

			equipoRepository.save(equipo);
		} catch (ParseException e) {
			return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Equipo>(HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/imagen/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Equipo> cambiarImagenEquipo(@PathVariable String id, @RequestParam("File") MultipartFile file) {
		Equipo equipo = equipoRepository.findById(id);
		if (equipo == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}

		synchronized (lock) {
			if (imageService.getImg().cambiarFoto(equipo.getNombre() + equipo.getGrupo(), file)) {
				equipo.setImagenEquipo(imageService.getImg().getNombreFichero());

				equipoRepository.save(equipo);
			} else {
				return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
			}
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
	public ResponseEntity<Equipo> añadirJugadorEquipo(@PathVariable(value = "id") String id, @PathVariable(value = "idEquipo") String idEquipo) {
		Equipo equipo = equipoRepository.findById(idEquipo);
		Jugador jugador = jugadorRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		if (!equipo.getGrupo().equals("") && equipo.isAceptado()) {
			jugador.setGrupo(equipo.getGrupo());
		}
		if (!jugador.getEquipo().equals("")) {
			if (!equipo.getPlantillaEquipo().contains(jugador)) {
				Equipo aux = equipoRepository.findById(jugador.getEquipo());
				/*
				 * if (!aux.getGrupo().equals(equipo.getGrupo()) &&
				 * (!aux.getGrupo().equals("")) && (aux.isAceptado())) { Grupo
				 * grupoAux =
				 * grupoRepository.findByNombreIgnoreCase(aux.getGrupo());
				 * grupoAux.getGoleadores().remove(jugador);
				 * grupoRepository.save(grupoAux); }
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
			jugador.setGrupo("");

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

		if (!equipo.getGrupo().equals("") && equipo.isAceptado()) {
			Grupo grupo = grupoRepository.findByNombreIgnoreCase(equipo.getGrupo());
			grupo.getClasificacion().remove(equipo);
			grupoRepository.save(grupo);
		}

		// Testing

		UsuarioTemporal usuarioTemporal = temporalRepository.findByEquipoId(id);
		if (usuarioTemporal != null) {
			usuarioTemporal.setEquipoId("");
			temporalRepository.save(usuarioTemporal);

			Query query = new Query(Criteria.where("equipo").is(equipo.getId()));
			try {
				mongoBulk.eliminarBloque(query, "Jugador");
			} catch (Exception e) {
				return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
			}
		} else {
			if (equipo.getPlantillaEquipo() != null) {
				for (Jugador j : equipo.getPlantillaEquipo()) {
					j.setEquipo("");
					jugadorRepository.save(j);
				}
			}
		}

		equipoRepository.delete(equipo);

		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}

	private void actualizarJugadores(JSONArray jugadores, Equipo equipo) {
		for (int i = 0; i < jugadores.size(); i++) {
			JSONObject jsonJugador = (JSONObject) jugadores.get(i);
			Optional<Jugador> jugador = equipo.getPlantillaEquipo().stream().filter(j -> j.getId().equals((String) jsonJugador.get("id"))).findAny();
			if (jugador.isPresent()) {
				actualizarDatosJugador(jsonJugador, jugador.get());
			}
		}
	}

	private void eliminarJugadores(List<String> listaIds, Equipo equipo) {
		List<Jugador> plantilla = equipo.getPlantillaEquipo();

		listaIds.forEach(id -> {
			Optional<Jugador> jugador = plantilla.stream().filter(j -> j.getId().equals(id)).findAny();
			if (jugador.isPresent()) {
				plantilla.remove(jugador.get());
				jugadorRepository.delete(jugador.get());
			}
		});

		equipo.setPlantillaEquipo(plantilla);

	}

	private void crearJugadores(JSONArray jugadores, Equipo equipo) {
		Jugador jugador;
		JSONObject jsonJugador;
		List<Jugador> listaJugadores = new ArrayList<>();
		for (int i = 0; i < jugadores.size(); i++) {
			jsonJugador = (JSONObject) jugadores.get(i);
			jugador = new Jugador((String) jsonJugador.get("nombre"), (String) jsonJugador.get("apellidos"), (String) jsonJugador.get("fechaNacimiento"), (String) jsonJugador.get("dni"), (String) jsonJugador.get("email"), (String) jsonJugador.get("fotoJugador"), (String) jsonJugador.get("posicion"),
					(String) jsonJugador.get("lugarNacimiento"), (String) jsonJugador.get("nacionalidad"), (int) (long) jsonJugador.get("dorsal"), (boolean) jsonJugador.get("capitan"));

			jugador.setEquipo(equipo.getId());
			jugadorRepository.save(jugador);

			listaJugadores.add(jugador);
		}

		equipo.getPlantillaEquipo().addAll(listaJugadores);
	}

	private void actualizarDatosJugador(JSONObject jsonJugador, Jugador jugador) {
		jugador.setNombre((String) jsonJugador.get("nombre"));
		jugador.setApellidos((String) jsonJugador.get("apellidos"));
		jugador.setEmail((String) jsonJugador.get("email"));
		jugador.setFechaNacimiento((String) jsonJugador.get("fechaNacimiento"));
		jugador.setDni((String) jsonJugador.get("dni"));
		jugador.setPosicion((String) jsonJugador.get("posicion"));
		jugador.setLugarNacimiento((String) jsonJugador.get("lugarNacimiento"));
		jugador.setNacionalidad((String) jsonJugador.get("nacionalidad"));
		jugador.setDorsal((int) (long) jsonJugador.get("dorsal"));
		jugador.setFotoJugador((String) jsonJugador.get("fotoJugador"));
		jugador.setCapitan((boolean) jsonJugador.get("capitan"));
		jugadorRepository.save(jugador);
	}
}