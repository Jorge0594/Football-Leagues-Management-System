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

import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping("/jugadores")
public class PlayerController {
	
	public interface ProfileView extends Player.ProfileAtt,Player.TeamAtt{}
	
	@Autowired
	PlayerRepository playerRepository;
	
	
	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Player>crearJugador(@RequestBody Player jugador){
		jugador.setFotoJugador("defaultImage.png");
		playerRepository.save(jugador);
		return new ResponseEntity<Player>(jugador,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Player>> verJugadores() {
		return new ResponseEntity<List<Player>>(playerRepository.findAll(), HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Player>> verJugador(@PathVariable(value = "nombre") String nombre) {
		List<Player> jugadores = playerRepository.findByNombreIgnoreCase(nombre);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Player>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Player>>(jugadores, HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.GET)
	public ResponseEntity<Player> verJugadorApellidos(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos) {
		Player jugador = playerRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Player>(jugador, HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/equipo/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Player>> verJugadoresEquipo(@PathVariable String equipo){
		List<Player>jugadores = playerRepository.findByEquipoIgnoreCase(equipo);
		if(jugadores.isEmpty()){
			return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Player>>(jugadores,HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<List<Player>>verJugadorEstado (@PathVariable String estado){
		List <Player>jugadores = playerRepository.findByEstadoIgnoreCase(estado);
		if(jugadores.isEmpty()){
			return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Player>> (jugadores,HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Player>> verJugadorEstadoEquipo (@PathVariable(value = "estado")String estado,@PathVariable(value = "equipo")String equipo){
		List <Player>jugadores = playerRepository.findByEstadoAndEquipoAllIgnoreCase(estado,equipo);
		if(jugadores.isEmpty()){
			return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Player>> (jugadores,HttpStatus.OK);
	}
	
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/capitan/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<Player>verCapitanEquipo(@PathVariable String equipo){
		Player capitan = playerRepository.findByCapitanAndEquipoIgnoreCase(true, equipo);
		if(capitan == null){
			return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Player>(capitan, HttpStatus.OK);
	}
	
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/dni/{DNI}", method = RequestMethod.GET)
	public ResponseEntity<Player> verJugadorDNI(@PathVariable (value = "DNI") String DNI){
		Player jugador = playerRepository.findByDniIgnoreCase(DNI);
		if(jugador == null){
			return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Player>(jugador, HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.PUT)
	public ResponseEntity<Player> actualizaJugador(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos, @RequestBody Player entrada) {
		Player jugador = playerRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_MODIFIED);
		}
		jugador.setNombre(entrada.getNombre());
		jugador.setApellidos(entrada.getApellidos());
		jugador.setGoles(entrada.getGoles());
		jugador.setDorsal(entrada.getDorsal());
		jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
		jugador.setTarjetasRojas(entrada.getTarjetasRojas());
		jugador.setNacionalidad(entrada.getNacionalidad());
		playerRepository.save(jugador);
		return new ResponseEntity<Player>(jugador, HttpStatus.ACCEPTED);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}/credenciales", method = RequestMethod.PUT)
	public ResponseEntity<Player> actualizarUsuarioContraseña (@PathVariable String id, @RequestBody Player auxJugador){
		Player jugador = playerRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_MODIFIED);
		}
		jugador.setNombreUsuario(auxJugador.getNombreUsuario());
		jugador.setContraseña(auxJugador.getContraseña());
		playerRepository.save(jugador);
		return new ResponseEntity<Player> (jugador,HttpStatus.OK);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Player> eliminarJugador (@PathVariable String id){
	Player jugador= playerRepository.findById(id);
	if (jugador==null) {
		return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
	}
	playerRepository.delete(jugador);
	return new ResponseEntity<Player>(jugador,HttpStatus.ACCEPTED);
	}

}
