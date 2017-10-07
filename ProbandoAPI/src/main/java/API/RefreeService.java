package API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refrees")
public class RefreeService {
	
	@Autowired
	RefreeRepository repositorio;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Refree>> verArbitros(){
		return new ResponseEntity<List<Refree>>(repositorio.findAll(), HttpStatus.OK);
		
	}

}
