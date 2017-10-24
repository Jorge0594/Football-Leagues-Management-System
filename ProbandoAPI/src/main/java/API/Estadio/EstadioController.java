package API.Estadio;

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

@RestController
@CrossOrigin
@RequestMapping(value="/estadios")
public class EstadioController {
	
	@Autowired
	private EstadioRepository estadioRepository;
	
	@RequestMapping (method = RequestMethod.GET)
	public  ResponseEntity<List<Estadio>> verEstadios (){
		return new ResponseEntity<List<Estadio>>(estadioRepository.findAll(),HttpStatus.OK);
		
	}

	@RequestMapping (value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Estadio> verEstadio(@PathVariable String id){
		Estadio entrada = estadioRepository.findById(id);
		if(entrada==null) {
			return new ResponseEntity<Estadio>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Estadio>(entrada,HttpStatus.OK);
		}
	}
	
	@RequestMapping (value="/{nombre}", method= RequestMethod.GET)
	public ResponseEntity<Estadio> verEstadioNombre(String nombre){
		Estadio entrada = estadioRepository.findByNombre(nombre);
		if (entrada==null) {
			return new ResponseEntity<Estadio>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Estadio>(entrada, HttpStatus.OK);
	}
	
	@RequestMapping (method = RequestMethod.POST)
	public ResponseEntity<Estadio> crearEstadios(@RequestBody Estadio estadio){
		estadioRepository.save(estadio);
		return new ResponseEntity<Estadio>(estadio,HttpStatus.CREATED);
	}
}
