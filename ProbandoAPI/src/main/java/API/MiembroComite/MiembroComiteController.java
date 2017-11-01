package API.MiembroComite;

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

@RestController
@CrossOrigin
@RequestMapping("/miembrosComite")
public class MiembroComiteController {
	
	@Autowired
	private MiembroComiteRepositorio miembroComiteRepository;
	
	//GET
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MiembroComite>> verTodosMiembrosComite() {
		return new ResponseEntity<List<MiembroComite>>(miembroComiteRepository.findAll(), HttpStatus.OK);

	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MiembroComite> verMiembroComite(@PathVariable String id) {
		MiembroComite miembro = miembroComiteRepository.findById(id);
		if (miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MiembroComite>(miembro, HttpStatus.OK);
	}
	@RequestMapping(value = "/{usuario}", method = RequestMethod.GET)
	public ResponseEntity<MiembroComite> verMiembroComiteUsuario(@PathVariable String usuario){
		MiembroComite miembro = miembroComiteRepository.findByUsuarioIgnoreCase(usuario);
		if(miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MiembroComite>(miembro,HttpStatus.OK);
	}
	
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
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MiembroComite> modificarMiembroComiteInfo(@PathVariable String id, @RequestBody MiembroComite miembroModificado) {
		MiembroComite miembro = miembroComiteRepository.findById(id);
		if (miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NO_CONTENT);
		}
		miembro.setNombre(miembroModificado.getNombre());
		miembro.setApellidos(miembroModificado.getApellidos());
		miembro.setEmail(miembroModificado.getEmail());
		miembroComiteRepository.save(miembro);
		return new ResponseEntity<MiembroComite>(miembro, HttpStatus.OK);
	}
	
	//Modificar usuario y contraseña
	@RequestMapping(value = "datosAcceso/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MiembroComite> modificarMiembroComiteAcceso(@PathVariable String id, @RequestBody MiembroComite miembroModificado) {
		MiembroComite miembro = miembroComiteRepository.findById(id);
		if (miembro == null) {
			return new ResponseEntity<MiembroComite>(HttpStatus.NO_CONTENT);
		}
		miembro.setUsuario(miembroModificado.getUsuario());
		miembro.setClave(miembroModificado.getClave());
		miembroComiteRepository.save(miembro);
		return new ResponseEntity<MiembroComite>(miembro, HttpStatus.OK);
	}
	
	//POST
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<MiembroComite> crearMiembroComite(@RequestBody MiembroComite miembro) {
		miembroComiteRepository.save(miembro);
		return new ResponseEntity<MiembroComite>(miembro, HttpStatus.CREATED);

	}
	
}
