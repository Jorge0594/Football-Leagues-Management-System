package API.Arbitro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arbitros")
public class ArbitroController {
	
	@Autowired
	ArbitroRepository arbitroRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitros(){
		return new ResponseEntity<List<Arbitro>>(arbitroRepository.findAll(), HttpStatus.OK);
		
	}

}
