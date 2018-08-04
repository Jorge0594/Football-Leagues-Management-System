package API.Liga;

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

import API.Temporada.Temporada;

@RestController
@CrossOrigin
@RequestMapping("/ligas")
public class LigaController {

	public interface LigaAtt extends Liga.LigaAtt, Temporada.TemporadaAtt {
	}

	@Autowired
	private LigaRepository ligaRepository;

	@JsonView(LigaAtt.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Liga>> verLigas() {
		return new ResponseEntity<List<Liga>>(ligaRepository.findAll(), HttpStatus.OK);

	}

	@JsonView(LigaAtt.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Liga> crearLiga(@RequestBody Liga entrada) {

		if (ligaRepository.findByNombreIgnoreCase(entrada.getNombre()) != null) {
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			entrada.setId(null);
			ligaRepository.save(entrada);
		}
		return new ResponseEntity<Liga>(entrada, HttpStatus.OK);
	}

	@JsonView(LigaAtt.class)
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Liga> eliminarLiga(@PathVariable String id) {
		Liga liga = ligaRepository.findById(id);

		if (liga == null) {
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}

		ligaRepository.delete(liga);

		return new ResponseEntity<>(liga, HttpStatus.OK);
	}

}
