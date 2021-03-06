package API.Solicitud;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import API.Mails.MailService;
import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;
import API.UsuarioTemporal.UsuarioTemporal;
import API.UsuarioTemporal.UsuarioTemporalRepository;

@RestController
@CrossOrigin
@RequestMapping("/solicitudes")
public class SolicitudController {
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	@Autowired
	private MailService mailService;
	@Autowired
	private UsuarioTemporalRepository temporalRepository;
	
	@RequestMapping(method =RequestMethod.POST)
	public ResponseEntity<Solicitud>crearSolicitud(@RequestBody Solicitud solicitud){
		
		//No se permitirá mandar mas de dos solicitudes desde una misma ip ni tampoco a ningun usuario del sistema crear solicitudes
		if( solicitudRepository.findByIp(solicitud.getIp()) != null || temporalRepository.findByIp(solicitud.getIp())!= null
				|| usuarioComponent.getLoggedUser() != null || solicitud.getIp() == null){
			return new ResponseEntity<Solicitud>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		if(solicitudRepository.findByEmail(solicitud.getEmail())!= null || temporalRepository.findByEmail(solicitud.getEmail()) != null){
			return new ResponseEntity<Solicitud>(HttpStatus.CONFLICT);
		}
		
		solicitud.setId(null);
		solicitudRepository.save(solicitud);
		
		return new ResponseEntity<Solicitud>(solicitud, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Solicitud>>verSolicitudes(){
		return new ResponseEntity<List<Solicitud>>(solicitudRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "email/{email}", method = RequestMethod.GET)
	public ResponseEntity<Solicitud> existeEmail(@PathVariable String email){
		Solicitud solicitud = solicitudRepository.findByEmail(email);
		
		if(solicitud == null){
			return new ResponseEntity<Solicitud>(HttpStatus.OK);
		}
		return new ResponseEntity<Solicitud>(HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/aceptar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UsuarioTemporal>aceptarSolicitud(@PathVariable String id){
		Solicitud solicitud = solicitudRepository.findById(id);
		if(solicitud == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NO_CONTENT);
		}
		
		//Crear un usuario temporal
		String nombreUsuario = generarNombreUsuario(solicitud.getNombreSolicitante(), solicitud.getApellidosSolicitante());
		String clave = generarClave();
		String crendenciales = nombreUsuario + ";" + clave;
		
		UsuarioTemporal usuarioTemporal = new UsuarioTemporal(solicitud.getIp(), nombreUsuario, clave, solicitud.getNombreSolicitante(),
				solicitud.getApellidosSolicitante(), solicitud.getEmail(), solicitud.getCampus(), solicitud.getLiga());
		//Crear un usuario asociado a este usuario temporal
		Usuario usuario = new Usuario(nombreUsuario, usuarioTemporal.getClave(), "ROLE_TEMPORAL");
		
		mailService.getMail().mandarEmail(usuarioTemporal.getEmail(), "Usuario de acceso a altas de equipo", crendenciales, "temporal");
		
		solicitudRepository.delete(solicitud);
		temporalRepository.save(usuarioTemporal);
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<UsuarioTemporal>(usuarioTemporal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rechazar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Solicitud>rechazarSolicitud(@PathVariable String id){
		Solicitud solicitud = solicitudRepository.findById(id);
		if(solicitud == null){
			return new ResponseEntity<Solicitud>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		solicitudRepository.delete(solicitud);
		
		return new ResponseEntity<Solicitud>(solicitud, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Solicitud> eleminarSolicitud(@PathVariable String id){
		Solicitud solicitud = solicitudRepository.findById(id);
		
		if(solicitud == null){
			return new ResponseEntity<Solicitud>(HttpStatus.NO_CONTENT);
		}
		
		solicitudRepository.delete(solicitud);
		
		return new ResponseEntity<Solicitud>(solicitud, HttpStatus.OK);
	}
	
	private String generarNombreUsuario(String nombre, String apellidos) {

		String apellido[] = apellidos.split(" ");

		String usuario = nombre + apellido[0];

		while (usuarioRepository.findByNombreUsuarioIgnoreCase(usuario) != null) {
			Random rnd = new Random();
			int num = rnd.nextInt(1000);
			if (usuarioRepository.findByNombreUsuarioIgnoreCase((usuario += num)) == null) {
				usuario += num;
			}
		}
		return usuario;
	}

	private static String generarClave() {// Genera una clave con caracteres
											// ASCII aleatorios
		String clave = "";
		Random rnd = new Random();
		for (int i = 0; i < 5; i++) {
			clave = clave + ((char) (rnd.nextInt(27) + 63));// Caracteres del
															// '?' a la 'Z'
			clave = clave + ((char) (rnd.nextInt(25) + 97));// Carateres de la
															// 'a' a la 'z'
		}
		return clave;
	}

	
	
}
