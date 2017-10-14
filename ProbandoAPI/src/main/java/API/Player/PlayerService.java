package API.Player;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jugadores")
public class PlayerService {

	@Autowired
	PlayerRepository repositorio;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Player>> verJugadores() {
		return new ResponseEntity<List<Player>>(repositorio.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Player>> verJugador(@PathVariable(value = "nombre") String nombre) {
		List<Player> jugadores = repositorio.findByNombreIgnoreCase(nombre);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Player>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Player>>(jugadores, HttpStatus.OK);
	}

	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.GET)
	public ResponseEntity<Player> verJugadorApellidos(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos) {
		Player jugador = repositorio.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Player>(jugador, HttpStatus.OK);
	}


	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.PUT)
	public ResponseEntity<Player> actualizaJugador(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos, @RequestBody Player entrada) {
		Player jugador = repositorio.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
		}
		jugador.setNombre(entrada.getNombre());
		jugador.setApellidos(entrada.getApellidos());
		jugador.setGoles(entrada.getGoles());
		jugador.setDorsal(entrada.getDorsal());
		jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
		jugador.setTarjetasRojas(entrada.getTarjetasRojas());
		jugador.setNacionalidad(entrada.getNacionalidad());
		repositorio.save(jugador);
		return new ResponseEntity<Player>(jugador, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value="/{nombre}/{apellidos}", method= RequestMethod.DELETE)
	public ResponseEntity<Player> eliminarJugador (@PathVariable (value="nombre") String nombre,@PathVariable(value="apellidos") String apellidos){
	Player jugador= repositorio.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
	if (jugador==null) {
		return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
	}
	repositorio.delete(jugador);
	return new ResponseEntity<Player>(jugador,HttpStatus.ACCEPTED);
	}

}
