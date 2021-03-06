package API.UsuarioTemporal;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Mails.MailService;
import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/temporales")

public class UsuarioTemporalController {
	
	@Autowired
	private UsuarioTemporalRepository temporalRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<UsuarioTemporal>crearUsuarioTemporal(@RequestBody UsuarioTemporal usuarioTemporal){
		if(temporalRepository.findByNombreUsuarioIgnoreCase(usuarioTemporal.getNombreUsuario())!= null ||
				usuarioRepository.findByNombreUsuarioIgnoreCase(usuarioTemporal.getNombreUsuario())!= null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NOT_ACCEPTABLE);
		}
		//A efectos de testing no se genera automaticamente la clave ni el usuario
		String clave = usuarioTemporal.getClave();
		
		usuarioTemporal.setId(null);
		usuarioTemporal.setEquipoId("");
		usuarioTemporal.setClaveEncriptada(usuarioTemporal.getClave());
		
		String crendenciales = usuarioTemporal.getNombreUsuario() + ";" + clave;
		
		Usuario usuario = new Usuario(usuarioTemporal.getNombre(), usuarioTemporal.getClave(), "ROLE_TEMPORAL");
		mailService.getMail().mandarEmail(usuarioTemporal.getEmail(), "Usuario de acceso a altas de equipo", crendenciales, "temporal");
		
		temporalRepository.save(usuarioTemporal);
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<UsuarioTemporal>(usuarioTemporal, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioTemporal>>verUsuarios(){
		return new ResponseEntity<List<UsuarioTemporal>>(temporalRepository.findAll(), HttpStatus.OK);
				
	}
	
	@RequestMapping(value = "/usuario")
	public ResponseEntity<UsuarioTemporal>verPerfilUsuariotemporal(){
		return new ResponseEntity<UsuarioTemporal>(temporalRepository.findByNombreUsuarioIgnoreCase(usuarioComponent.getLoggedUser().getNombreUsuario()),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UsuarioTemporal> usuarioId(@PathVariable String id){
		UsuarioTemporal usuario = temporalRepository.findById(id);
		if(usuario == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UsuarioTemporal>(usuario, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/equipo" , method = RequestMethod.GET)
	public ResponseEntity<Equipo>equipoUsuario(@PathVariable String id){
		UsuarioTemporal usuario = temporalRepository.findById(id);
		if(usuario == null){
			return new ResponseEntity<Equipo>(HttpStatus.NO_CONTENT);
		}
		
		Equipo equipo = equipoRepository.findById(usuario.getEquipoId());
		
		if(equipo == null){
			return new ResponseEntity<Equipo>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "liga/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UsuarioTemporal> asignarLiga(@PathVariable String id, @RequestBody String liga){
		UsuarioTemporal temporal = temporalRepository.findById(id);
		
		if(temporal == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NO_CONTENT);
		}
		
		temporal.setLiga(liga);
		
		temporalRepository.save(temporal);
		
		return new ResponseEntity<UsuarioTemporal>(temporal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "usuario/{id}", method = RequestMethod.PUT)
	private ResponseEntity<UsuarioTemporal>cambiarUsuarioClave(@PathVariable String id, @RequestBody UsuarioTemporal entrada){
		UsuarioTemporal temporal = temporalRepository.findById(id);
		
		if(temporal == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NO_CONTENT);
		}
		
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(temporal.getNombreUsuario());
		
		if(usuario == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		temporal.setNombreUsuario(entrada.getNombreUsuario());
		temporal.setClaveEncriptada(entrada.getClave());
		
		usuario.setNombreUsuario(temporal.getNombreUsuario());
		usuario.setClave(temporal.getClave());
		
		temporalRepository.save(temporal);
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<UsuarioTemporal>(temporal, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UsuarioTemporal>eliminarUsuario(@PathVariable String id){
		UsuarioTemporal usuarioTemporal = temporalRepository.findById(id);
		if(usuarioTemporal == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NO_CONTENT);
		}
		
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(usuarioTemporal.getNombreUsuario());
		
		if(usuario == null){
			return new ResponseEntity<UsuarioTemporal>(HttpStatus.NO_CONTENT);
		}
		
		usuarioRepository.delete(usuario);
		temporalRepository.delete(usuarioTemporal);
		
		return new ResponseEntity<UsuarioTemporal>(usuarioTemporal, HttpStatus.OK);
	}
	

}
