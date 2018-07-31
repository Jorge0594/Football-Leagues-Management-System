package API.VistaGrupo;


import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.itextpdf.text.DocumentException;
import com.tournament.generator.TournamentCalendar;


import API.Grupo.Grupo;
import API.Grupo.GrupoRepository;


@RestController
@CrossOrigin
@RequestMapping("/vistasGrupos")
public class VistaGrupoController {

	public interface VistaGrupoAtt {
	}


	@Autowired
	VistaGrupoRepository vistaGrupoRepository;
	@Autowired
	GrupoRepository grupoRepository;

	@JsonView(VistaGrupoAtt.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VistaGrupo>> verVistasGrupo () {
		return new ResponseEntity<List<VistaGrupo>>(vistaGrupoRepository.findAll(), HttpStatus.OK);

	}
	

	@JsonView(VistaGrupoAtt.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<VistaGrupo> crearVistaGrupo(@RequestBody VistaGrupo entrada) {
		Grupo grupo = grupoRepository.findById(entrada.getIdGrupo());
		if (grupo == null) {
			return new ResponseEntity<VistaGrupo>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			entrada.setNombre(grupo.getNombre());
			vistaGrupoRepository.save(entrada);
		}
		return new ResponseEntity<VistaGrupo>(entrada, HttpStatus.OK);
	}
	}