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

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin
@RequestMapping(value = "/estadios")
public class EstadioController {

	public interface EstadioView extends Estadio.BasicoAtt, Estadio.DatosAtt {
	}

	@Autowired
	private EstadioRepository estadioRepository;

	@JsonView(EstadioView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Estadio>> verEstadios() {
		return new ResponseEntity<List<Estadio>>(estadioRepository.findAll(), HttpStatus.OK);

	}

	@JsonView(EstadioView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Estadio> verEstadio(@PathVariable String id) {
		Estadio entrada = estadioRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Estadio>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Estadio>(entrada, HttpStatus.OK);
		}
	}

	@JsonView(EstadioView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<Estadio> verEstadioNombre(String nombre) {
		Estadio entrada = estadioRepository.findByNombre(nombre);
		if (entrada == null) {
			return new ResponseEntity<Estadio>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Estadio>(entrada, HttpStatus.OK);
	}

	@JsonView(EstadioView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Estadio> modificarEstadio(@PathVariable String id, @RequestBody Estadio estadio) {
		Estadio entrada = estadioRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Estadio>(HttpStatus.NOT_FOUND);
		} else {
			estadio.setId(entrada.getId());
			estadioRepository.save(estadio);
			return new ResponseEntity<Estadio>(estadio, HttpStatus.OK);
		}
	}

	@JsonView(EstadioView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Estadio> crearEstadios(@RequestBody Estadio estadio) {
		estadio.setId(null);
		estadioRepository.save(estadio);
		return new ResponseEntity<Estadio>(estadio, HttpStatus.CREATED);
	}

}
