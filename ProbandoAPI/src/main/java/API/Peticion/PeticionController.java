package API.Peticion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.Collections;
import java.util.List;

@RequestMapping("/peticiones")
public class PeticionController {

	@Autowired
	private PeticionRepository peticionRepository;
	//GET
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Peticion>> verPeticiones(){
		return new ResponseEntity<List<Peticion>>(peticionRepository.findAll(), HttpStatus.OK);
	}
	
	
	
	
}
