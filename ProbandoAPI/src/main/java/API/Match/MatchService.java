package API.Match;

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
public class MatchService {
	@Autowired
	private MatchRepository repositorio;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Match>> verPartidos() {
		return new ResponseEntity<List<Match>>(repositorio.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Match> crearPartido(@RequestBody Match partido) {
		repositorio.save(partido);
		return new ResponseEntity<Match>(partido, HttpStatus.CREATED);

	}
}