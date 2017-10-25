package API.Incidencia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import API.Jugador.JugadorRepository;

@RestController
@CrossOrigin
@RequestMapping("/incidencias")
public class IncidenciaController {
	

		@Autowired
		IncidenciaRepository incidenciasRepository;
		@Autowired
		JugadorRepository jugadoresRepository;

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

		@RequestMapping(value = "incidencias/partido/{partido}")
		public ResponseEntity<Incidencia> verIncidenciasPartido(@PathVariable String idPartido) {
			Incidencia incidencia = incidenciasRepository.findByIdPartidoIgnoreCase(idPartido);
			if (incidencia == null) {
				return new ResponseEntity<Incidencia>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Incidencia>(incidencia, HttpStatus.OK);
		}

		
		
		/*@RequestMapping(value="/incidencias/jugador/{idJugador}", method= RequestMethod.GET)
		public List<Incidencia> incidenciasJugador(@PathVariable String idJugador){
			Jugador jugador= jugadoresRepository.findById(idJugador);

		}*/

}
