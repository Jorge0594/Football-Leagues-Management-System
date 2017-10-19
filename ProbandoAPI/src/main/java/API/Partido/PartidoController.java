package API.Partido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partidos")
public class PartidoController {
	@Autowired
	private PartidoRepository partidoRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidos() {
		return new ResponseEntity<List<Partido>>(partidoRepository.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Partido> crearPartido(@RequestBody Partido partido) {
		partidoRepository.save(partido);
		return new ResponseEntity<Partido>(partido, HttpStatus.CREATED);

	}
}