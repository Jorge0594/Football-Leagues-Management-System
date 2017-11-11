package API.Sancion;

import java.util.Date;
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

import API.Acta.Acta;
import API.Arbitro.*;
import API.Jugador.*;

@RestController
@CrossOrigin
@RequestMapping("/sanciones")
public class SancionController {
	
	@Autowired
	SancionRepository sancionRepository;
	@Autowired
	ArbitroRepository arbitroRepository;
	@Autowired
	JugadorRepository jugadorRepository;	
	
	
	//Get
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Sancion>> verSanciones(){
		return new ResponseEntity<List<Sancion>>(sancionRepository.findAll(), HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Sancion> verSancionId(@PathVariable String id){
		Sancion sancion = sancionRepository.findById(id);
		if(sancion == null) {
			
			return new ResponseEntity<Sancion>(HttpStatus.NOT_FOUND);
		}
			return new ResponseEntity<Sancion>(sancion, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{estado}", method = RequestMethod.GET)
	public ResponseEntity<List<Sancion>> verSancionesEstado(@PathVariable String estado){
		List<Sancion> sanciones = sancionRepository.findByEstado(estado);
		if(sanciones.isEmpty()) {
			return new ResponseEntity<List<Sancion>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Sancion>>(sanciones, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{inicioSancion}", method = RequestMethod.GET)
	public ResponseEntity<List<Sancion>> verSancionesInicio(@PathVariable Date inicioSancion){
		List<Sancion> sanciones = sancionRepository.findByInicioSancion(inicioSancion);
		if(sanciones.isEmpty()) {
			return new ResponseEntity<List<Sancion>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Sancion>>(sanciones, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{arbitroId}", method = RequestMethod.GET)
	public ResponseEntity<List<Sancion>> verSancionesPorArbitro(@PathVariable String arbitroId){
		List<Sancion> sanciones = sancionRepository.findByArbitroSdrId(arbitroId);
		if(sanciones.isEmpty()) {
			return new ResponseEntity<List<Sancion>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Sancion>>(sanciones, HttpStatus.OK);
	}
	
	
	//PUT
	@RequestMapping(value = "/aprobarSancion/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Sancion> modificarEstadoSancion(@PathVariable String id){
		Sancion sancion = sancionRepository.findById(id);
		if(sancion == null) {
			return new ResponseEntity<Sancion>(HttpStatus.NOT_FOUND);
		}
		sancion.setEstado("Activa");
		return new ResponseEntity<Sancion>(sancion, HttpStatus.OK);
	}
	
	//Cuando se apruebe un acta se comprueba si hay jugadores de los equipos que han jugado con sanciones y se aumentan los dias cumplidos. 
	@RequestMapping(value = "/partidoCumplido/{idJugador}", method = RequestMethod.PUT)
	public ResponseEntity<Sancion> cumplirPartidoSancion(@PathVariable String idJugador){
		Sancion sancion = sancionRepository.findByJugadorId(idJugador);
		if(sancion == null) {
			return new ResponseEntity<Sancion>(HttpStatus.NOT_FOUND);
		}
		sancion.setPartidosCumplidos(sancion.getPartidosCumplidos() +1);
		if(sancion.getPartidosCumplidos()== sancion.getPartidosSancionados()) {
			sancion.setEstado("Cumplida");
		}
		sancionRepository.save(sancion);
		return new ResponseEntity<Sancion>(sancion, HttpStatus.OK);
	}
	
	
	//POST
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Sancion> crearSancion(@RequestBody Sancion sancion) {
		sancion.setEstado("Pendiente");
		sancion.setPartidosCumplidos(0);
		sancionRepository.save(sancion);
		return new ResponseEntity<Sancion>(sancion, HttpStatus.CREATED);

	}
	
	
}
