package API.Jugador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
import API.Equipo.EquipoController.PerfilView;
import API.Grupo.Grupo;
import API.Grupo.GrupoRepository;
import API.Images.ImageService;
import API.Mails.MailService;
import API.MongoBulk.MongoBulk;
import API.Partido.PartidoRepository;
import API.Sancion.Sancion;
import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;
import API.Utilidades.UsuarioUtils;
import API.Vistas.VistaGrupo;

@RestController
@CrossOrigin
@RequestMapping("/jugadores")
public class JugadorController {

	public interface ProfileView extends Jugador.PerfilAtt, Jugador.EquipoAtt, Sancion.SancionAtt, Sancion.JugadorAtt, VistaGrupo.VistaGrupoAtt {
	}

	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private GrupoRepository grupoRepository;
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
	@Autowired
	private UsuarioUtils utils;
	@Autowired
	private MongoBulk mongoBullk;

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
		if (jugadorRepository.findByDniIgnoreCase(jugador.getDni()) != null || jugadorRepository.findByEmailIgnoreCase(jugador.getEmail()) != null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
		}

		String clave = utils.generarClave();
		jugador.setClaveEncriptada(clave);
		jugador.setNombreUsuario(utils.generarNombreUsuario(jugador.getNombre(), jugador.getApellidos()));
		jugador.setId(null);
		jugador.setGoles(0);
		jugador.setTarjetasAmarillas(0);
		jugador.setTarjetasRojas(0);
		jugador.setFotoJugador("defaultProfile.jpg");
		jugador.setSanciones(new ArrayList<Sancion>());
		if (jugador.isAceptado()) {
			Usuario usuario = new Usuario(jugador.getNombreUsuario(), jugador.getClave(), "ROLE_JUGADOR");
			usuarioRepository.save(usuario);
		}

		if (jugador.getEquipo() != null && !jugador.getEquipo().equals("")) {

			Equipo equipo = equipoRepository.findById(jugador.getEquipo());

			if (equipo == null) {
				return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
			}

			if (!equipo.getGrupo().equals("") && equipo.isAceptado()) {
				jugador.setGrupo(equipo.getGrupo());
			}

			equipo.getPlantillaEquipo().add(jugador);
			
			jugadorRepository.save(jugador);
			equipoRepository.save(equipo);
			
		} else {
			jugadorRepository.save(jugador);
		}
	
		String texto = jugador.getNombre() + ";" + jugador.getNombreUsuario() + ";" + clave;
		/*
		 * mailService.getMail().mandarEmail(jugador.getEmail()
		 * ,"Nombre de usuario y contraseña",texto, "jugador");
		 */
		return new ResponseEntity<Jugador>(jugador, HttpStatus.CREATED);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadores() {
		return new ResponseEntity<List<Jugador>>(jugadorRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/grupo/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadoresPorGrupo(@PathVariable(value = "idGrupo") String idGrupo) {
		List<Jugador> jugadores = jugadorRepository.findByGrupoIdGrupo(idGrupo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
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
	@RequestMapping(value = "/usuario", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verPerfilJugadorUsuario() {
		return new ResponseEntity<Jugador>(jugadorRepository.findByNombreUsuarioIgnoreCase(usuarioComponent.getLoggedUser().getNombreUsuario()), HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorApellidos(@PathVariable(value = "nombre") String nombre, @PathVariable(value = "apellidos") String apellidos) {
		Jugador jugador = jugadorRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/validar/email/{email:.+}/{id}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> emailDisponible(@PathVariable String email, @PathVariable String id) {
		Jugador jugador = jugadorRepository.findByEmailIgnoreCase(email);
		if (jugador != null && !jugador.getId().equals(id)) {
			return new ResponseEntity<Jugador>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Jugador>(HttpStatus.OK);
	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "validar/dni/{dni}/{id}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> dniDisponible(@PathVariable String dni, @PathVariable String id) {
		Jugador jugador = jugadorRepository.findByDniIgnoreCase(dni);
		if (jugador != null && !jugador.getId().equals(id)) {
			return new ResponseEntity<Jugador>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Jugador>(HttpStatus.OK);
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
	public ResponseEntity<List<Jugador>> verJugadorEstadoEquipo(@PathVariable(value = "estado") String estado, @PathVariable(value = "equipo") String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoAndEquipoAllIgnoreCase(estado, equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/capitan/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verCapitanEquipo(@PathVariable String equipo) {
		Jugador capitan = jugadorRepository.findByDelegadoAndEquipo(true, equipo);
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
	@RequestMapping(value = "clave/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> claveOlvidada(@PathVariable String email) {
		Jugador jugador = jugadorRepository.findByEmailIgnoreCase(email);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());
			if (usuario == null) {
				return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
			}
			String clave = utils.generarClave();
			jugador.setClaveEncriptada(clave);
			usuario.setClave(jugador.getClave());
			usuarioRepository.save(usuario);
			jugadorRepository.save(jugador);
			String credenciales = jugador.getNombreUsuario() + ";" + clave;
			mailService.getMail().mandarEmail(jugador.getEmail(), "Nueva contraseña", credenciales, "claveusuario");
			return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
		}
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorId(@PathVariable(value = "id") String id) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> actualizaJugadorId(@PathVariable(value = "id") String id, @RequestBody Jugador entrada) {
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
			Arbitro arbitro = arbitroRepository.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
			if (!partidoRepository.findByIdArbitroAndEquipoLocalIdOrEquipoVisitanteId(arbitro.getId(), jugador.getEquipo(), jugador.getEquipo()).isEmpty()) {
				jugador.setGoles(entrada.getGoles());
				jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
				jugador.setTarjetasRojas(entrada.getTarjetasRojas());
				break;
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
			}
		case "ROLE_ADMIN":
			jugador.setNombre(entrada.getNombre());
			jugador.setApellidos(entrada.getApellidos());
			jugador.setDelegado(entrada.isDelegado());
			jugador.setEmail(entrada.getEmail());
			jugador.setDni(entrada.getDni());
			jugador.setDorsal(entrada.getDorsal());
			jugador.setGoles(entrada.getGoles());
			jugador.setSanciones(entrada.getSanciones());
			jugador.setNacionalidad(entrada.getNacionalidad());
			jugador.setEstado(entrada.getEstado());
			jugador.setPosicion(entrada.getPosicion());
			jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
			jugador.setTarjetasRojas(entrada.getTarjetasRojas());
			jugador.setFechaNacimiento(entrada.getFechaNacimiento());
			jugador.setLugarNacimiento(entrada.getLugarNacimiento());
			if (!jugador.getEquipo().equals(entrada.getEquipo())) {
				Equipo antiguo = equipoRepository.findById(jugador.getEquipo());
				antiguo.getPlantillaEquipo().remove(jugador);
				equipoRepository.save(antiguo);
				Equipo nuevo = equipoRepository.findById(entrada.getEquipo());
				nuevo.getPlantillaEquipo().add(jugador);
				equipoRepository.save(nuevo);
			}
		case "ROLE_MIEMBROCOMITE":
			jugador.setNombre(entrada.getNombre());
			jugador.setApellidos(entrada.getApellidos());
			jugador.setDelegado(entrada.isDelegado());
			jugador.setEmail(entrada.getEmail());
			jugador.setDni(entrada.getDni());
			jugador.setNombreUsuario(entrada.getNombreUsuario());
			jugador.setDorsal(entrada.getDorsal());
			jugador.setGoles(entrada.getGoles());
			jugador.setSanciones(entrada.getSanciones());
			jugador.setNacionalidad(entrada.getNacionalidad());
			jugador.setEstado(entrada.getEstado());
			jugador.setPosicion(entrada.getPosicion());
			jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
			jugador.setTarjetasRojas(entrada.getTarjetasRojas());
			jugador.setFechaNacimiento(entrada.getFechaNacimiento());
			jugador.setLugarNacimiento(entrada.getLugarNacimiento());
			if (!jugador.getEquipo().equals(entrada.getEquipo())) {
				Equipo antiguo = equipoRepository.findById(jugador.getEquipo());
				antiguo.getPlantillaEquipo().remove(jugador);
				equipoRepository.save(antiguo);
				Equipo nuevo = equipoRepository.findById(entrada.getEquipo());
				nuevo.getPlantillaEquipo().add(jugador);
				equipoRepository.save(nuevo);
			}
			break;
		default:
			return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
		}

		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}/email", method = RequestMethod.PUT)
	public synchronized ResponseEntity<Jugador> cambiarEmail(@PathVariable String id, @RequestBody String email) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		jugador.setEmail(email);
		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/grupo", method = RequestMethod.PUT)
	public synchronized ResponseEntity<List<Jugador>> cambiarGrupo() {
		Update update = new Update();
		update.set("liga", "URJC");
		update.set("grupo", "GRUPO A");
		Query query = new Query();
		
		mongoBullk.modificarBloque(query, update, "Jugador");
		
		return new ResponseEntity<List<Jugador>>(HttpStatus.OK);
		
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}/foto", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> modificarImagenPerfil(@PathVariable String id, @RequestParam("File") MultipartFile file) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
		}
		
		synchronized (imageService.getImg().getLock()) {
			boolean cambioFoto = imageService.getImg().cambiarFoto(jugador.getDni(), file);
			if (cambioFoto) {
				jugador.setFotoJugador(imageService.getImg().getNombreFichero());
				jugadorRepository.save(jugador);
				return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
			}
		}

	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "dni/{dni}/foto", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> modificarImagenPerfilDni(@PathVariable String dni, @RequestParam("File") MultipartFile file) {
		Jugador jugador = jugadorRepository.findByDniIgnoreCase(dni);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
		}
		
		synchronized (imageService.getImg().getLock()) {
			boolean cambioFoto = imageService.getImg().cambiarFoto(jugador.getDni(), file);
			if (cambioFoto) {
				jugador.setFotoJugador(imageService.getImg().getNombreFichero());
				jugadorRepository.save(jugador);
				return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		

	}

	@JsonView(PerfilView.class)
	@RequestMapping(value = "/clave/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> cambiarClave(@PathVariable String id, @RequestBody String clave) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.CONFLICT);
		}
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());

		jugador.setClaveEncriptada(clave);
		usuario.setClave(jugador.getClave());

		usuarioRepository.save(usuario);
		jugadorRepository.save(jugador);

		return new ResponseEntity<Jugador>(HttpStatus.OK);
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
			Grupo grupo = grupoRepository.findById(equipo.getGrupo().getIdGrupo());
			if (grupo != null) {
				equipo.getPlantillaEquipo().remove(jugador);
				grupoRepository.save(grupo);
				equipoRepository.save(equipo);
			}

		}

		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());

		if (usuario != null) {
			usuarioRepository.delete(usuario);
		}
		
		if(!jugador.getFotoJugador().equals("defaultProfile.jpg")){
			imageService.getImg().eleminarFoto(jugador.getFotoJugador());	
		}
		
		jugadorRepository.delete(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

}
