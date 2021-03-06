package API.Arbitro;

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
import API.Acta.ActaRepository;
import API.Grupo.Grupo;
import API.Grupo.GrupoRepository;
import API.Jugador.JugadorController.ProfileView;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Sancion.Sancion;
import API.Sancion.SancionRepository;
import API.Temporada.Temporada;
import API.Temporada.TemporadaRepository;
import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;
import API.Images.ImageService;
import API.Utilidades.UsuarioUtils;
import API.Mails.MailService;

@RestController
@CrossOrigin
@RequestMapping("/arbitros")
public class ArbitroController {

	public interface ArbitroView extends Arbitro.ActaAtt, Arbitro.PerfilAtt, Partido.InfoAtt {
	}

	@Autowired
	ArbitroRepository arbitroRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	TemporadaRepository temporadaRepository;
	@Autowired
	UsuarioComponent usuarioComponent;
	@Autowired
	ActaRepository actaRepository;
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	PartidoRepository partidoRepository;
	@Autowired
	SancionRepository sancionRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private UsuarioUtils utils;
	@Autowired
	private MailService mailService;
	
	@JsonView(ArbitroView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Arbitro> creaArbitro(@RequestBody Arbitro arbitro) {
		if (arbitroRepository.findByDniIgnoreCase(arbitro.getDni()) != null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_ACCEPTABLE);
		}
		String clave = utils.generarClave();
		arbitro.setClaveEncriptada(clave);
		arbitro.setNombreUsuario(utils.generarNombreUsuario(arbitro.getNombre(), arbitro.getApellidos()));
		arbitro.setId(null);
		arbitro.setPartidosArbitrados(new ArrayList<Partido>());

		Usuario usuario = new Usuario(arbitro.getNombreUsuario(), arbitro.getClave(), "ROLE_ARBITRO");
		usuarioRepository.save(usuario);
		arbitroRepository.save(arbitro);
		
		String texto = arbitro.getNombre() + ";" + arbitro.getNombreUsuario() + ";" + clave;
		/*mailService.getMail().mandarEmail(arbitro.getEmail(),"Nombre de usuario y contraseña",texto, "jugador");*/
		
		return new ResponseEntity<Arbitro>(arbitro, HttpStatus.CREATED);
	}
	
	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/temporada/{idTemporada}", method = RequestMethod.POST)
	public ResponseEntity<Arbitro> creaArbitroTemporada(@RequestBody Arbitro arbitro, @PathVariable String idTemporada) {
		if (arbitroRepository.findByDniIgnoreCase(arbitro.getDni()) != null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_ACCEPTABLE);
		}
		Temporada temporada = temporadaRepository.findById(idTemporada);
		if (temporada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		String clave = utils.generarClave();
		arbitro.setClaveEncriptada(clave);
		arbitro.setNombreUsuario(utils.generarNombreUsuario(arbitro.getNombre(), arbitro.getApellidos()));
		arbitro.setId(null);
		arbitro.setPartidosArbitrados(new ArrayList<Partido>());

		Usuario usuario = new Usuario(arbitro.getNombreUsuario(), arbitro.getClave(), "ROLE_ARBITRO");
		usuarioRepository.save(usuario);		
		arbitroRepository.save(arbitro);
		temporada.getArbitros().add(arbitro);
		//temporadaRepository.save(temporada);
		
		String texto = arbitro.getNombre() + ";" + arbitro.getNombreUsuario() + ";" + clave;
		/*mailService.getMail().mandarEmail(arbitro.getEmail(),"Nombre de usuario y contraseña",texto, "jugador");*/
		
		return new ResponseEntity<Arbitro>(arbitro, HttpStatus.CREATED);
	}

	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Arbitro> modificaArbitro(@PathVariable String id, @RequestBody Arbitro arbitroModificado) {
		Arbitro entrada = arbitroRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Usuario modificado = usuarioRepository.findByNombreUsuarioIgnoreCase(entrada.getNombreUsuario());
			// Si el usuario conectado es un árbitro
			if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
				// Si el árbitro está realizando una modificación a su propio
				// usuario.
				if (entrada.getNombreUsuario().equals(usuarioComponent.getLoggedUser().getNombreUsuario())) {
					entrada.setNombre(arbitroModificado.getNombre());
					entrada.setNombreUsuario(arbitroModificado.getNombreUsuario());
					// Si se ha realizado un cambio en la contraseña se encripta
					// y se guarda, si es la misma no se toca.
					if (!arbitroModificado.getClave().equals(entrada.getClave())) {
						entrada.setClaveEncriptada(arbitroModificado.getClave());
					}
					entrada.setFechaNacimiento(arbitroModificado.getFechaNacimiento());
					entrada.setEdad(arbitroModificado.getEdad());
					entrada.setLugarNacimiento(arbitroModificado.getLugarNacimiento());
					entrada.setEmail(arbitroModificado.getEmail());
					entrada.setTlf(arbitroModificado.getTlf());
					entrada.setFotoArbitro(arbitroModificado.getFotoArbitro());
					arbitroRepository.save(entrada);
					// Se realizan los cambios en el listado de Usuarios de la
					// API.
					modificado.setClave(entrada.getClave());
					modificado.setNombreUsuario(entrada.getNombreUsuario());
					usuarioRepository.save(modificado);
					return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			}
			// Si el usuario conectado es un miembro del comité o un
			// administrador
			if ((usuarioComponent.getLoggedUser().getRol().equals("ROLE_MIEMBROCOMITE"))
					|| (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ADMIN"))) {
				entrada.setNombre(arbitroModificado.getNombre());
				entrada.setNombreUsuario(arbitroModificado.getNombreUsuario());
				// Si se ha realizado un cambio en la contraseña se encripta y
				// se guarda, si es la misma no se toca.
				if (!arbitroModificado.getClave().equals(entrada.getClave())) {
					entrada.setClaveEncriptada(arbitroModificado.getClave());
				}
				entrada.setFechaNacimiento(arbitroModificado.getFechaNacimiento());
				entrada.setEdad(arbitroModificado.getEdad());
				entrada.setLugarNacimiento(arbitroModificado.getLugarNacimiento());
				entrada.setEmail(arbitroModificado.getEmail());
				entrada.setTlf(arbitroModificado.getTlf());
				entrada.setCategoria(arbitroModificado.getCategoria());
				entrada.setEstado(arbitroModificado.getEstado());
				entrada.setPartidosArbitrados(arbitroModificado.getPartidosArbitrados());
				entrada.setDni(arbitroModificado.getDni());
				entrada.setInternacional(arbitroModificado.isInternacional());
				entrada.setComite(arbitroModificado.getComite());
				entrada.setFotoArbitro(arbitroModificado.getFotoArbitro());
				arbitroRepository.save(entrada);
				// Se realizan los cambios en el listado de Usuarios de la API.
				modificado.setClave(entrada.getClave());
				modificado.setNombreUsuario(entrada.getNombreUsuario());
				usuarioRepository.save(modificado);
				return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
			}
			// No debería entrar aquí nunca, por los permisos de Roles.
			else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	}
	
	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/clave/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Arbitro>cambiarClave(@PathVariable String id, @RequestBody String clave){
		Arbitro arbitro = arbitroRepository.findById(id);
		
		if(arbitro == null){
			return new ResponseEntity<Arbitro>(HttpStatus.NO_CONTENT);
		}
		
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(arbitro.getNombreUsuario());
		
		if(usuario == null){
			return new ResponseEntity<Arbitro>(HttpStatus.NO_CONTENT);
		}
		
		arbitro.setClaveEncriptada(clave);
		usuario.setClave(arbitro.getClave());
		
		arbitroRepository.save(arbitro);
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<Arbitro>(arbitro, HttpStatus.OK);
	}

	@JsonView(ArbitroView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitros() {
		return new ResponseEntity<List<Arbitro>>(arbitroRepository.findAll(), HttpStatus.OK);

	}

	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroId(@PathVariable String id) {
		Arbitro entrada = arbitroRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}

	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/dni/{dni}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroDni(@PathVariable String dni) {
		Arbitro entrada = arbitroRepository.findByDniIgnoreCase(dni);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}

	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/nombreUsuario/{nombreUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroNombreUsuario(@PathVariable String nombreUsuario) {
		Arbitro entrada = arbitroRepository.findByNombreUsuario(nombreUsuario);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}

	@JsonView(ArbitroView.class)
	@RequestMapping(value = "/comite/{comite}", method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitrosComite(@PathVariable String comite) {
		List<Arbitro> entrada = arbitroRepository.findByComite(comite);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Arbitro>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Arbitro>>(entrada, HttpStatus.OK);
	}

	/*@JsonView(ArbitroView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Arbitro> eliminaArbitro(@PathVariable String id) {
		Arbitro arbitro = arbitroRepository.findById(id);
		if (arbitro == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		} else {
			Usuario usuarioDelArbitro = usuarioRepository.findByNombreUsuarioIgnoreCase(arbitro.getNombreUsuario());
			List<Partido> partidosDelArbitro = partidoRepository.findByIdArbitro(arbitro.getId());
			List<Sancion> sancionesDelArbitro = sancionRepository.findByArbitroSdrId(arbitro.getId());

			if (!ligasDelArbitro.isEmpty()) {
				// Elimina al árbitro de todas sus ligas.
				for (Grupo grupo : ligasDelArbitro) {
					if (grupo.getArbitros().contains(arbitro)) {
						grupo.getArbitros().remove(arbitro);
						grupoRepository.save(grupo);
					}
				}
			}
			if (!partidosDelArbitro.isEmpty()) {
				// Elimina al árbitro de todos sus partidos.
				for (Partido partido : partidosDelArbitro) {
					partido.setIdArbitro("");
					partidoRepository.save(partido);
				}
			}
			if (!sancionesDelArbitro.isEmpty()) {
				// Elimina al árbitro de todas sus sanciones.
				for (Sancion sancion : sancionesDelArbitro) {
					sancion.setArbitroSdrId("");
					sancionRepository.save(sancion);
				}
			}
			// Elimina el usuario del árbitro.
			if (usuarioDelArbitro != null) {
				usuarioRepository.delete(usuarioDelArbitro);
			}
			arbitroRepository.delete(arbitro);
			return new ResponseEntity<Arbitro>(arbitro, HttpStatus.OK);
		}
	}
	DE MOMENTO NO SE ELIMINA, VER COMO SE GESTIONA CON LAS TEMPORADAS (De todas maneras no se eliminan)
	*/
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "dni/{dni}/foto", method = RequestMethod.PUT)
	public ResponseEntity<Arbitro> modificarImagenPerfilDni(@PathVariable String dni,
			@RequestParam("File") MultipartFile file) {
		Arbitro arbitro = arbitroRepository.findByDniIgnoreCase(dni);
		if (arbitro == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NO_CONTENT);
		}
		if (usuarioComponent.getLoggedUser().getNombreUsuario().equals(arbitro.getNombreUsuario())
				|| usuarioComponent.getLoggedUser().getRol().equals("ROLE_MIEMBROCOMITE") ||  usuarioComponent.getLoggedUser().getRol().equals("ROLE_ADMIN")) {
			boolean cambioFoto = imageService.getImg().cambiarFoto(arbitro.getDni(), file);
			if (cambioFoto) {
				arbitro.setFotoArbitro(imageService.getImg().getNombreFichero());
				arbitroRepository.save(arbitro);
				return new ResponseEntity<Arbitro>(arbitro, HttpStatus.OK);
			} else {
				return new ResponseEntity<Arbitro>(HttpStatus.NOT_ACCEPTABLE);
			}

		} else {
			return new ResponseEntity<Arbitro>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/clave/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> claveOlvidada(@PathVariable String email) {
		Arbitro arbitro = arbitroRepository.findByEmailIgnoreCase(email);
		if (arbitro == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(arbitro.getNombreUsuario());
			if (usuario == null) {
				return new ResponseEntity<Arbitro>(HttpStatus.NOT_ACCEPTABLE);
			}
			String clave = utils.generarClave();
			arbitro.setClaveEncriptada(clave);
			usuario.setClave(arbitro.getClave());
			usuarioRepository.save(usuario);
			arbitroRepository.save(arbitro);
			String credenciales = arbitro.getNombreUsuario() + ";" + clave; 
			//Deshabilitado de momento
			mailService.getMail().mandarEmail(arbitro.getEmail(), "Nueva contraseña", credenciales, "claveusuario");
			return new ResponseEntity<Arbitro>(arbitro, HttpStatus.OK);
		}
	}
	


}
