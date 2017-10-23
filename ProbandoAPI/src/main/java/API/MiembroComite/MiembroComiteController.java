package API.MiembroComite;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/miembrosComite")
public class MiembroComiteController {
	
	@Autowired
	private MiembroComiteRepositorio miembroComiteRepository;

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
	
	//Buscar todos los miembros de un comite en concreto si al final creamos la colección comite

	
	
}
