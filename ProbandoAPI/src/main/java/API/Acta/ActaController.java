package API.Acta;

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
@RequestMapping("/actas")
public class ActaController {

	@Autowired
	ActaRepository actaRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActas() {
		return new ResponseEntity<List<Acta>>(actaRepository.findAll(), HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Acta> verActaId(@PathVariable String id) {
		Acta entrada = actaRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/partido/{idPartido}")
	public ResponseEntity<Acta> verActaIdPartido(@PathVariable String idPartido) {
		Acta entrada = actaRepository.findByIdPartidoIgnoreCase(idPartido);
		if (entrada == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/arbitro/{arbitro}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaArbitro(@PathVariable String arbitro) {
		List<Acta> entrada = actaRepository.findByArbitro(arbitro);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fecha/{fecha}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaFecha(@PathVariable String fecha) {
		List<Acta> entrada = actaRepository.findByFecha(fecha);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/local/{equipoLocal}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoLocal(@PathVariable String equipoLocal) {
		List<Acta> entrada = actaRepository.findByEquipoLocalIgnoreCase(equipoLocal);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/visitante/{equipoVisitante}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoVisitante(@PathVariable String equipoVisitante) {
		List<Acta> entrada = actaRepository.findByEquipoVisitanteIgnoreCase(equipoVisitante);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Acta> crearActa(@RequestBody Acta entrada) {
		actaRepository.save(entrada);
		return new ResponseEntity<Acta>(entrada, HttpStatus.CREATED);

	}
}
