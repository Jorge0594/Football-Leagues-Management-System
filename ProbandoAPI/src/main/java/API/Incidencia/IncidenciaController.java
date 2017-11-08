package API.Incidencia;
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

import API.Jugador.JugadorRepository;
import API.MiembroComite.MiembroComite;
import API.Usuario.Usuario;


@RestController
@CrossOrigin
@RequestMapping("/incidencias")
public class IncidenciaController {
	

		@Autowired
		IncidenciaRepository incidenciasRepository;
		@Autowired
		JugadorRepository jugadoresRepository;

		//GET
		
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<List<Incidencia>> verIncidencias() {
			return new ResponseEntity<List<Incidencia>>(incidenciasRepository.findAll(), HttpStatus.OK);

		}

		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity<Incidencia> verIncidencia(@PathVariable String id) {
			Incidencia incidencia = incidenciasRepository.findById(id);
			if (incidencia == null) {
				return new ResponseEntity<Incidencia>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Incidencia>(incidencia, HttpStatus.OK);
		}

		@RequestMapping(value = "incidencias/partido/{idPartido}", method = RequestMethod.GET)
		public ResponseEntity<List<Incidencia>> verIncidenciasPartido(@PathVariable String idPartido) {	
			List<Incidencia> incidencias = incidenciasRepository.findByIdPartidoIgnoreCase(idPartido);
			if (incidencias.isEmpty()) {
				return new ResponseEntity<List<Incidencia>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Incidencia>>(incidencias, HttpStatus.OK);
		}

		@RequestMapping(value = "incidencias/jugador/{idJugador}", method = RequestMethod.GET)
		public ResponseEntity<List<Incidencia>> verIncidenciasJugador(@PathVariable String idJugador) {
			List<Incidencia> incidencias = incidenciasRepository.findByIdJugadorIgnoreCase(idJugador);
			if (incidencias.isEmpty()) {
				return new ResponseEntity<List<Incidencia>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Incidencia>>(incidencias, HttpStatus.OK);
		}
		
		@RequestMapping(value = "incidencias/tipo/{tipoIncidencia}", method = RequestMethod.GET)
		public ResponseEntity<List<Incidencia>> verIncidenciasTipo(@PathVariable String tipoIncidencia) {
			List<Incidencia> incidencias = incidenciasRepository.findByTipoIgnoreCase(tipoIncidencia);
			if (incidencias.isEmpty()) {
				return new ResponseEntity<List<Incidencia>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Incidencia>>(incidencias, HttpStatus.OK);
		}
		
		
		//PUT
		
		//POST
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Incidencia> crearIncidencia(@RequestBody Incidencia incidencia) {
			if( incidencia.getTipo().equalsIgnoreCase("ROJA") || incidencia.getTipo().equalsIgnoreCase("AMARILLA") || incidencia.getTipo().equalsIgnoreCase("GOL")) {
				incidenciasRepository.save(incidencia);
				return new ResponseEntity<Incidencia>(incidencia, HttpStatus.CREATED);
			}
			return new ResponseEntity<Incidencia>(HttpStatus.NOT_ACCEPTABLE);

		}
		
		//DELETE
		@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
		public ResponseEntity<Incidencia> eliminarIncidencia(@PathVariable String id){
			Incidencia incidencia= incidenciasRepository.findById(id);
			if (incidencia != null) {                                                                                                                                                     
					incidenciasRepository.delete(incidencia);
					return new ResponseEntity<>(null, HttpStatus.OK);
				
			}
				else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}                                            
}
