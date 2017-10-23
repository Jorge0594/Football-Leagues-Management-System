package API.Arbitro;

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
@RequestMapping("/arbitros")
public class ArbitroController {

	@Autowired
	ArbitroRepository arbitroRepository;
	
	@RequestMapping (method = RequestMethod.POST)
	public ResponseEntity<Arbitro> creaArbitro (@RequestBody Arbitro arbitro){
		arbitroRepository.save(arbitro);
		return new ResponseEntity<Arbitro>(arbitro,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitros() {
		return new ResponseEntity<List<Arbitro>>(arbitroRepository.findAll(), HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroId(@PathVariable String id) {
		Arbitro entrada = arbitroRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}
	
	@RequestMapping (value="/dni/{dni}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroDni(@PathVariable String dni){
		Arbitro entrada = arbitroRepository.findByDni(dni);
		if (entrada==null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada,HttpStatus.OK);
	}
	
	@RequestMapping (value="/nombreUsuario/{nombreUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroNombreUsuario(@PathVariable String nombreUsuario){
		Arbitro entrada = arbitroRepository.findByNombreUsuario(nombreUsuario);
		if (entrada==null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada,HttpStatus.OK);
	}
	
	@RequestMapping (value="/comite/{comite}", method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitrosComite(@PathVariable String comite){
		List<Arbitro> entrada = arbitroRepository.findByComite(comite);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Arbitro>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Arbitro>>(entrada,HttpStatus.OK);
	}
	

}
