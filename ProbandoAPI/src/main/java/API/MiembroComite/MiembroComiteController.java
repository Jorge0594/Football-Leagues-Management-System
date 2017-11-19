package API.MiembroComite;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.ArbitroController.ArbitroView;
import API.Usuario.*;


@RestController
@CrossOrigin
@RequestMapping("/miembrosComite")
public class MiembroComiteController {
	
	public interface MiembroComiteView extends MiembroComite.PerfilMCAtt{}
	
	@Autowired
	private MiembroComiteRepository miembroComiteRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioComponent usuarioComponent;
	
	//GET
	@JsonView(MiembroComiteView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MiembroComite>> verTodosMiembrosComite() {
		return new ResponseEntity<List<MiembroComite>>(miembroComiteRepository.findAll(), HttpStatus.OK);
	}
	
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MiembroComite> verMiembroComite(@PathVariable String id) {
		MiembroComite miembro = miembroComiteRepository.findById(id);
		if (miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MiembroComite>(miembro, HttpStatus.OK);
	}
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value = "/{usuario}", method = RequestMethod.GET)
	public ResponseEntity<MiembroComite> verMiembroComiteUsuario(@PathVariable String usuario){
		MiembroComite miembro = miembroComiteRepository.findByUsuarioIgnoreCase(usuario);
		if(miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MiembroComite>(miembro,HttpStatus.OK);
	}
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public ResponseEntity<MiembroComite> verMiembroComiteEmail(@PathVariable String email){
		MiembroComite miembro = miembroComiteRepository.findByEmailIgnoreCase(email);
		if(miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MiembroComite>(miembro,HttpStatus.OK);
	}
	
	//Buscar todos los miembros de un comite en concreto si al final creamos la colección comite
	
	//PUT
	
	//Modificar información del miembro del comite
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value = "modificarDatos/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MiembroComite> modificarMiembroComiteInfo(@PathVariable String id, @RequestBody MiembroComite miembroModificado) {
		MiembroComite miembro = miembroComiteRepository.findById(id);
		if (miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NO_CONTENT);
		}
		Usuario usuarioConectado= usuarioRepository.findById(usuarioComponent.getLoggedUser().getId());
		if(usuarioConectado.getNombreUsuario().equals(miembro.getUsuario()) || usuarioConectado.getRol().equals("ROLE_ADMIN")) {
			miembro.setNombre(miembroModificado.getNombre());
			miembro.setApellidos(miembroModificado.getApellidos());
			miembro.setEmail(miembroModificado.getEmail());
			miembroComiteRepository.save(miembro);
		return new ResponseEntity<MiembroComite>(miembro, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<MiembroComite>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	//Modificar datos de acceso del miembro del comite
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value = "modificarUsuarioAcceso/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MiembroComite> modificarMiembroComiteAcceso(@PathVariable String id, @RequestBody MiembroComite miembroModificado) {
		Usuario usuarioConectado=usuarioRepository.findById(usuarioComponent.getLoggedUser().getId());
		MiembroComite miembro = miembroComiteRepository.findById(id);
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(miembro.getUsuario());
		if (usuarioConectado.getId() == usuario.getId() || usuarioConectado.getRol().equals("ROLE_ADMIN")){
			if (miembro == null || usuario == null) {
				return new ResponseEntity<MiembroComite>(HttpStatus.NO_CONTENT);
			}
			else {
				List<Usuario> usuarios = usuarioRepository.findAll();
				List<String> nombresUsuarios = new ArrayList<>();
				for(Usuario us : usuarios){
					nombresUsuarios.add(us.getNombreUsuario());
				}
				if(!nombresUsuarios.contains(miembro.getUsuario())){
					miembro.setUsuario(miembroModificado.getUsuario());
					usuario.setNombreUsuario(miembro.getUsuario());
				}
				else {
					return new ResponseEntity<MiembroComite>(miembro,HttpStatus.NOT_ACCEPTABLE);
				}
				if (!miembroModificado.getClave().equals(miembro.getClave())) {
					miembro.setClaveEncriptada(miembroModificado.getClave());
				}
				usuario.setClave(miembro.getClave());
				miembroComiteRepository.save(miembro);
				usuarioRepository.save(usuario);
				return new ResponseEntity<MiembroComite>(miembro, HttpStatus.OK);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	//POST
	@JsonView(MiembroComiteView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<MiembroComite> crearMiembroComite(@RequestBody MiembroComite miembro) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<String> nombresUsuarios = new ArrayList<>();
		for(Usuario us : usuarios) {
			nombresUsuarios.add(us.getNombreUsuario());
		}
		if(!nombresUsuarios.contains(miembro.getUsuario())) {
			miembro.setClaveEncriptada(miembro.getClave());
			miembroComiteRepository.save(miembro);
			Usuario usuarioNuevo = new Usuario(miembro.getUsuario(), miembro.getClave(), "ROLE_MIEMBROCOMITE");
			usuarioRepository.save(usuarioNuevo);
			return new ResponseEntity<MiembroComite>(miembro, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value="/crearAdmin", method = RequestMethod.POST)
	public ResponseEntity<MiembroComite> crearMiembroComiteAdmin(@RequestBody MiembroComite miembro) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<String> nombresUsuarios = new ArrayList<>();
		for(Usuario us : usuarios) {
			nombresUsuarios.add(us.getNombreUsuario());
		}
		if(!nombresUsuarios.contains(miembro.getUsuario())) {
			miembro.setClaveEncriptada(miembro.getClave());
			miembroComiteRepository.save(miembro);
			Usuario usuarioNuevo = new Usuario(miembro.getUsuario(), miembro.getClave(), "ROLE_ADMIN");
			usuarioRepository.save(usuarioNuevo);
			return new ResponseEntity<MiembroComite>(miembro, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	//DELETE
	@JsonView(MiembroComiteView.class)
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<MiembroComite> eliminarMiembroComite(@PathVariable String id){
		
		MiembroComite miembro= miembroComiteRepository.findById(id);
		if(miembro== null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NOT_FOUND);
		}
		
		Usuario usuarioMiembro = usuarioRepository.findByNombreUsuarioIgnoreCase(miembro.getUsuario());
		if(usuarioMiembro == null) {
			return new ResponseEntity<MiembroComite>(miembro,HttpStatus.NOT_FOUND);
		}
		
		Usuario usuarioConectado=usuarioRepository.findById(usuarioComponent.getLoggedUser().getId());
		if (usuarioMiembro.getId().equals(usuarioConectado.getId()) || usuarioConectado.getRol().equals("ROLE_ADMIN")){
				miembroComiteRepository.delete(miembro);
				usuarioRepository.delete(usuarioMiembro);
				return new ResponseEntity<MiembroComite>(HttpStatus.OK);
		}else {
			return new ResponseEntity<MiembroComite>(HttpStatus.UNAUTHORIZED);
		}	
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}
