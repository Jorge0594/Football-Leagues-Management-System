package API.Historico;

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

@RestController
@CrossOrigin
@RequestMapping("/histJugadores")
public class HistoricoJugadorController {
	
	@Autowired
	private HistoricoJugadorRepository historicoJugadorRepository;
	
	//For testing
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HistoricoJugador> añadirHistorico(@RequestBody HistoricoJugador historico){
		
		historico.setId(null);
		
		historicoJugadorRepository.save(historico);
		
		return new ResponseEntity<HistoricoJugador>(historico, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<HistoricoJugador>> obtenerHistoricos(){
		return new ResponseEntity<List<HistoricoJugador>> (historicoJugadorRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<HistoricoJugador> obtenerHistoricoId(@PathVariable String id){
		return new ResponseEntity<HistoricoJugador> (historicoJugadorRepository.findById(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jugador/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HistoricoJugador>> obtenerHistoricosJugador(@PathVariable String id){
		return new ResponseEntity<List<HistoricoJugador>>(historicoJugadorRepository.findByJugadorId(new ObjectId(id)), HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HistoricoJugador> eliminarHistorico(@PathVariable String id){
		HistoricoJugador historico = historicoJugadorRepository.findById(id);
		
		if(historico == null){
			return new ResponseEntity<HistoricoJugador>(HttpStatus.NOT_FOUND);
		}
		
		historicoJugadorRepository.delete(historico);
		
		return new ResponseEntity<HistoricoJugador>(historico, HttpStatus.OK);
	}

}
