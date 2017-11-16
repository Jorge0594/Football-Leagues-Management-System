package API.Partido;

import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
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

import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.Equipo;
import API.Estadio.Estadio;
import API.Jugador.Jugador;

@RestController
@CrossOrigin
@RequestMapping("/partidos")
public class PartidoController {
	
	public interface PartidoView extends Estadio.BasicoAtt, Partido.InfoAtt, Partido.RestAtt, Jugador.EquipoAtt, Equipo.RankAtt{}
	@Autowired
	private PartidoRepository partidoRepository;
	@Autowired
	private ArbitroRepository arbitroRepository;
	
	@JsonView(PartidoView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidos() {
		List<Partido> partidos = partidoRepository.findAll();
		Collections.sort(partidos);
		return new ResponseEntity<List<Partido>>(partidos, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Partido> verPartidoId(@PathVariable String id) {
		Partido entrada = partidoRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/liga/{liga}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosLiga(@PathVariable String liga) {
		List<Partido> entrada = partidoRepository.findByLiga(liga);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/jornada/{jornada}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosJornada(@PathVariable String jornada) {
		List<Partido> entrada = partidoRepository.findByJornada(jornada);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/equipoLocal/{equipoLocalId}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosEquipoLocal(@PathVariable String equipoLocalId) {
		List<Partido> entrada = partidoRepository.findByEquipoLocalId(new ObjectId(equipoLocalId));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/equipoVisitante/{equipoVisitanteId}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosEquipoVisitante(@PathVariable String equipoVisitanteId) {
		List<Partido> entrada = partidoRepository.findByEquipoVisitanteId(new ObjectId(equipoVisitanteId));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value = "/arbitro/{idArbitro}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> verPartidosArbitro(@PathVariable String idArbitro) {
		List<Partido> entrada = partidoRepository.findByIdArbitro(idArbitro);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Partido>>(entrada, HttpStatus.OK);
	}
	@JsonView(PartidoView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Partido> crearPartido(@RequestBody Partido partido) {
		Arbitro arbitroDelPartido = arbitroRepository.findById(partido.getIdArbitro());
		if (arbitroDelPartido == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		} else {
			partido.setId(null);
			partidoRepository.save(partido);
			Partido partidoConId= partidoRepository.findById(partido.getId());
			arbitroDelPartido.getPartidosArbitrados().add(partidoConId);
			arbitroRepository.save(arbitroDelPartido);
			return new ResponseEntity<Partido>(partido, HttpStatus.CREATED);
		}
	}
	@JsonView(PartidoView.class)
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Partido> EliminarPartidoId(@PathVariable String id) {
		Partido entrada = partidoRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Partido>(HttpStatus.NOT_FOUND);
		}
		Arbitro arbitroDelPartido = arbitroRepository.findById(entrada.getIdArbitro());
		//Elimina el partido de los partidos arbitrados del árbitro.
		arbitroDelPartido.getPartidosArbitrados().remove(entrada);
		arbitroRepository.save(arbitroDelPartido);
		partidoRepository.delete(entrada);
		return new ResponseEntity<Partido>(entrada, HttpStatus.OK);
	}
}