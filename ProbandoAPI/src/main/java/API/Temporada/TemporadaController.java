package API.Temporada;

import java.util.ArrayList;
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

import API.Liga.Liga;
import API.Liga.LigaRepository;
import API.Vistas.VistaGrupo;

@RestController
@CrossOrigin
@RequestMapping("/temporadas")
public class TemporadaController {

	public interface TemporadaAtt extends Temporada.TemporadaAtt, VistaGrupo.VistaGrupoAtt{
	}
 
	@Autowired
	private TemporadaRepository temporadaRepository;
	@Autowired
	private LigaRepository ligaRepository;

	@JsonView(TemporadaAtt.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Temporada>> verTemporadas() {
		return new ResponseEntity<List<Temporada>>(temporadaRepository.findAll(), HttpStatus.OK);

	}
	@JsonView(TemporadaAtt.class)
	@RequestMapping(value="/{idTemporada}", method = RequestMethod.GET)
	public ResponseEntity<Temporada> verTemporada(@PathVariable String idTemporada) {
		Temporada temporada = temporadaRepository.findById(idTemporada);
		if(temporada == null) {
			return new ResponseEntity<Temporada>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Temporada>(temporada, HttpStatus.OK);

	}

	@JsonView(TemporadaAtt.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Temporada> crearTemporada(@RequestBody Temporada entrada) {
		if (temporadaRepository.findByNombre(entrada.getNombre()) != null) {
			return new ResponseEntity<Temporada>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			entrada.setId(null);
			entrada.setGrupos(new ArrayList<VistaGrupo>());
			temporadaRepository.save(entrada);
		}
		return new ResponseEntity<Temporada>(entrada, HttpStatus.OK);
	}
	
	@JsonView(TemporadaAtt.class)
	@RequestMapping(value = "/modificarLiga/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Temporada> modificarTemporada(@PathVariable String id, @RequestBody String nombreLiga){
		Temporada temporada = temporadaRepository.findById(id);
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombreLiga);
		
		 if(temporada == null || liga == null){
			 return new ResponseEntity<Temporada>(HttpStatus.NO_CONTENT);
		 }
		 
		 temporada.setLiga(liga.getNombre());
		 liga.getTemporadas().add(temporada);
		 
		 temporadaRepository.save(temporada);
		 ligaRepository.save(liga);
		 
		 return new ResponseEntity<Temporada>(temporada, HttpStatus.OK);
	}
}