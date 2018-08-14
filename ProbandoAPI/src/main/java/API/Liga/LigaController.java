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

import API.MiembroComite.MiembroComite;
import API.MiembroComite.MiembroComiteRepository;
import API.Temporada.Temporada;
import API.Vistas.VistaGrupo;

@RestController
@CrossOrigin
@RequestMapping("/ligas")
public class LigaController {

	public interface LigaAtt extends Liga.LigaAtt, Temporada.TemporadaAtt {
	}

	@Autowired
	private LigaRepository ligaRepository;
	
	private MiembroComiteRepository miembroComiteRepository;

	@JsonView(LigaAtt.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Liga>> verLigas() {
		return new ResponseEntity<List<Liga>>(ligaRepository.findAll(), HttpStatus.OK);

	}
	@JsonView(LigaAtt.class)
	@RequestMapping(value="asignada/{idMiembroComite}", method = RequestMethod.GET)
	public ResponseEntity<Liga> verLigaComite(@PathVariable String idMiembroComite) {
		MiembroComite miembro = miembroComiteRepository.findById(idMiembroComite);
		if(miembro != null) {
			Liga liga = ligaRepository.findById(miembro.getIdLiga());
			if(liga != null) {
				return new ResponseEntity<Liga>(HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<Liga>(liga, HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Liga>(HttpStatus.NOT_FOUND);
		}
	}
	@JsonView(LigaAtt.class)
	@RequestMapping(value = "{nombre}/grupos", method = RequestMethod.GET)
	public ResponseEntity<List<VistaGrupo>> verGruposLiga(@PathVariable String nombre) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		
		if(liga == null){
			 return new ResponseEntity<List<VistaGrupo>>(HttpStatus.NOT_FOUND);
		}
		//Suponiendo que la variable temporada actual sea el indice en elq eu se encuentra dicha jornada en la lista
		Temporada temporada = liga.getTemporadas().get(liga.getTemporadaActual());
		
		return new ResponseEntity<List<VistaGrupo>>(temporada.getGrupos(), HttpStatus.OK);

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
