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
@RequestMapping("histEquipos")
public class HistoricoEquipoController {
	
	@Autowired
	private HistoricoEquipoRepository historicoEquipoRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HistoricoEquipo>crearHistorico(@RequestBody HistoricoEquipo historico){
		
		historico.setId(null);
		
		historicoEquipoRepository.save(historico);
		
		return new ResponseEntity<HistoricoEquipo>(historico, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<HistoricoEquipo>>obtenerHistoricos(){		
		return new ResponseEntity<List<HistoricoEquipo>>(historicoEquipoRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<HistoricoEquipo>obtenerHistorico(@PathVariable String id){		
		return new ResponseEntity<HistoricoEquipo>(historicoEquipoRepository.findById(id), HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/equipo/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HistoricoEquipo>>obtenerHistoricosEquipo(@PathVariable String id){		
		return new ResponseEntity<List<HistoricoEquipo>>(historicoEquipoRepository.findByEquipoId(new ObjectId(id)), HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HistoricoEquipo>eliminarHistorico(@PathVariable String id){
		HistoricoEquipo historico = historicoEquipoRepository.findById(id);
		
		if(historico == null){
			 return new ResponseEntity<HistoricoEquipo>(HttpStatus.NOT_FOUND);
		}
		
		historicoEquipoRepository.delete(historico);
		
		return new ResponseEntity<HistoricoEquipo>(historico, HttpStatus.OK);
	}	

}
