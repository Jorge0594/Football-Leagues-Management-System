package API.Temporada;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import API.Grupo.*;
import API.VistaGrupo.VistaGrupo;


@RestController
@CrossOrigin
@RequestMapping("/temporadas")
public class TemporadaController {

	public interface TemporadaAtt {
	}


	@Autowired
	TemporadaRepository temporadaRepository;
	@Autowired
	GrupoRepository grupoRepository;

	@JsonView(TemporadaAtt.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Temporada>> verTemporadas() {
		return new ResponseEntity<List<Temporada>>(temporadaRepository.findAll(), HttpStatus.OK);

	}
	

	@JsonView(TemporadaAtt.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Temporada> crearTemporada(@RequestBody Temporada entrada) {
		if(temporadaRepository.findByNombre(entrada.getNombre()) != null) {
			return new ResponseEntity<Temporada>(HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			entrada.setId(null);
			entrada.setGrupos(new ArrayList<VistaGrupo>());
			temporadaRepository.save(entrada);
		}
		return new ResponseEntity<Temporada>(entrada, HttpStatus.OK);
	}
	}