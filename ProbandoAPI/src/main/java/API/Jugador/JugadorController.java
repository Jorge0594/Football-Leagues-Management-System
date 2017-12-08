package API.Jugador;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Images.ImageService;
import API.Liga.Liga;
import API.Liga.LigaRepository;
import API.Mails.MailService;
import API.Partido.PartidoRepository;
import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/jugadores")
public class JugadorController {

	public interface ProfileView extends Jugador.PerfilAtt, Jugador.EquipoAtt {
	}

	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private LigaRepository ligaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ArbitroRepository arbitroRepository;
	@Autowired
	private PartidoRepository partidoRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	@Autowired
	private MailService mailService;
	@Autowired
	private ImageService imageService;

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
		if (jugadorRepository.findByDniIgnoreCase(jugador.getDni()) != null
				|| usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario()) != null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
		}
		String clave = jugador.getClave();
		jugador.setId(null);
		jugador.setFotoJugador("defaultProfile.jpg");
		jugador.setAceptado(false);
		jugador.setEquipo("");
		jugador.setGoles(0);
		jugador.setTarjetasAmarillas(0);
		jugador.setTarjetasRojas(0);
		jugador.setClaveEncriptada(jugador.getClave());

		/*if (jugador.isAceptado()) {
			Usuario usuario = new Usuario(jugador.getNombreUsuario(), jugador.getClave(), "ROLE_JUGADOR");

			usuarioRepository.save(usuario);
		}*/
		Usuario usuario = new Usuario(jugador.getNombreUsuario(), jugador.getClave(), "ROLE_JUGADOR");
		usuarioRepository.save(usuario);
		jugadorRepository.save(jugador);
		String texto = jugador.getNombre()+";"+ jugador.getNombreUsuario()+";"+clave+";" + jugador.getEmail() + ";";
		mailService.getMail().mandarEmail(jugador.getEmail(),"Nombre de usuario y contraseña",texto);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.CREATED);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadores() {
		return new ResponseEntity<List<Jugador>>(jugadorRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugador(@PathVariable(value = "nombre") String nombre) {
		List<Jugador> jugadores = jugadorRepository.findByNombreIgnoreCase(nombre);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/usuario/{nombreUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verPerfilJugadorUsuario(@PathVariable String nombreUsuario) {
		return new ResponseEntity<Jugador>(jugadorRepository.findByNombreUsuarioIgnoreCase(nombreUsuario),
				HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorApellidos(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos) {
		Jugador jugador = jugadorRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/equipo/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadoresEquipo(@PathVariable String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEquipoIgnoreCase(equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadorEstado(@PathVariable String estado) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoIgnoreCase(estado);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadorEstadoEquipo(@PathVariable(value = "estado") String estado,
			@PathVariable(value = "equipo") String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoAndEquipoAllIgnoreCase(estado, equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/capitan/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verCapitanEquipo(@PathVariable String equipo) {
		Jugador capitan = jugadorRepository.findByCapitanAndEquipoIgnoreCase(true, equipo);
		if (capitan == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(capitan, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/dni/{DNI}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorDNI(@PathVariable(value = "DNI") String DNI) {
		Jugador jugador = jugadorRepository.findByDniIgnoreCase(DNI);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> actualizaJugadorId(@PathVariable(value = "id") String id,
			@RequestBody Jugador entrada) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());
		switch (usuarioComponent.getLoggedUser().getRol()) {
		case "ROLE_JUGADOR":

			if (usuarioComponent.getLoggedUser().getNombreUsuario().equals(jugador.getNombreUsuario())) {

				jugador.setNombre(entrada.getNombre());
				jugador.setFotoJugador(entrada.getFotoJugador());
				jugador.setApellidos(entrada.getApellidos());
				jugador.setEmail(entrada.getEmail());

				if (!jugador.getClave().equals(entrada.getClave()) && entrada.getClave() != null) {
					jugador.setClaveEncriptada(entrada.getClave());
					usuario.setClave(jugador.getClave());
					usuarioRepository.save(usuario);
				}
				break;
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
			}
		case "ROLE_ARBITRO":
			/*
			 * boolean jugadorEnPartido = false; for (Partido partidoArbitro :
			 * arbitro.getPartidosArbitrados()) { if
			 * (((partidoArbitro.getEquipoLocal().getPlantillaEquipo().contains(
			 * jugador)) ||
			 * (partidoArbitro.getEquipoVisitante().getPlantillaEquipo().
			 * contains(jugador))) && (jugadorEnPartido == false)) {
			 * jugadorEnPartido = true; } } if (jugadorEnPartido) {
			 * jugador.setGoles(entrada.getGoles());
			 * jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
			 * jugador.setTarjetasRojas(entrada.getTarjetasRojas()); } else {
			 * return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED); }
			 */
			Arbitro arbitro = arbitroRepository
					.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
			if (!partidoRepository.findByIdArbitroAndEquipoLocalIdOrEquipoVisitanteId(arbitro.getId(),
					new ObjectId(jugador.getEquipo()), new ObjectId(jugador.getEquipo())).isEmpty()) {
				jugador.setGoles(entrada.getGoles());
				jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
				jugador.setTarjetasRojas(entrada.getTarjetasRojas());
				break;
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
			}
		case "ROLE_ADMIN":
		case "ROLE_MIEMBROCOMITE":
			jugador.setNombre(entrada.getNombre());
			jugador.setApellidos(entrada.getApellidos());
			jugador.setCapitan(entrada.isCapitan());
			jugador.setEmail(entrada.getEmail());
			jugador.setDni(entrada.getDni());
			jugador.setNombreUsuario(entrada.getNombreUsuario());

			if (!jugador.getClave().equals(entrada.getClave()) && entrada.getClave() != null) {
				jugador.setClaveEncriptada(entrada.getClave());
				usuario.setClave(jugador.getClave());
			}
			jugador.setDorsal(entrada.getDorsal());
			jugador.setGoles(entrada.getGoles());
			jugador.setFechaSancion(entrada.getFechaSancion());
			jugador.setNacionalidad(entrada.getNacionalidad());
			jugador.setEstado(entrada.getEstado());
			jugador.setPosicion(entrada.getPosicion());
			jugador.setGoles(entrada.getGoles());
			jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
			jugador.setTarjetasRojas(entrada.getTarjetasRojas());

			usuario.setNombreUsuario(jugador.getNombreUsuario());
			usuarioRepository.save(usuario);
			break;
		default:
			return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
		}

		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}/foto", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> modificarImagenPerfil(@PathVariable String id,
			@RequestParam("File") MultipartFile file) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		if (!usuarioComponent.getLoggedUser().getNombreUsuario().equals(jugador.getNombreUsuario())) {
			return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
		} else {
			boolean cambioFoto = imageService.getImg().cambiarFoto(jugador.getNombreUsuario(), file);
			if (cambioFoto) {
				jugador.setFotoJugador(imageService.getImg().getFileName());
				jugadorRepository.save(jugador);
				return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Jugador> eliminarJugador(@PathVariable String id) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}

		Equipo equipo = equipoRepository.findById(jugador.getEquipo());
		if (equipo != null) {
			equipo.getPlantillaEquipo().remove(jugador);
			Liga liga = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
			if (liga != null) {
				liga.getGoleadores().remove(jugador);
				equipo.getPlantillaEquipo().remove(jugador);
				ligaRepository.save(liga);
				equipoRepository.save(equipo);
			}

		}
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());

		if (usuario != null) {
			usuarioRepository.delete(usuario);
		}

		jugadorRepository.delete(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

}
