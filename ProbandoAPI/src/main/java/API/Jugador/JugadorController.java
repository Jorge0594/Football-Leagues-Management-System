package API.Jugador;

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

import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Liga.Liga;
import API.Liga.LigaRepository;

@RestController
@CrossOrigin
@RequestMapping("/jugadores")
public class JugadorController {

	public interface ProfileView extends Jugador.PerfilAtt, Jugador.EquipoAtt {}

	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	EquipoRepository equipoRepository;
	@Autowired
	LigaRepository ligaRepository;

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
		if(jugadorRepository.findByDniIgnoreCase(jugador.getDni())!=null){
			return new ResponseEntity<Jugador>(HttpStatus.CONFLICT);
		}
		jugador.setFotoJugador("defaultImage.png");
		jugador.setEquipo("");
		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadores() {
		return new ResponseEntity<List<Jugador>>(jugadorRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugador(@PathVariable(value = "nombre") String nombre) {
		List<Jugador> jugadores = jugadorRepository.findByNombreIgnoreCase(nombre);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorApellidos(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos) {
		Jugador jugador = jugadorRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/equipo/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadoresEquipo(@PathVariable String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEquipoIgnoreCase(equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadorEstado(@PathVariable String estado) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoIgnoreCase(estado);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadorEstadoEquipo(@PathVariable(value = "estado") String estado,
			@PathVariable(value = "equipo") String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoAndEquipoAllIgnoreCase(estado, equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/capitan/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verCapitanEquipo(@PathVariable String equipo) {
		Jugador capitan = jugadorRepository.findByCapitanAndEquipoIgnoreCase(true, equipo);
		if (capitan == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(capitan, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/dni/{DNI}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorDNI(@PathVariable(value = "DNI") String DNI) {
		Jugador jugador = jugadorRepository.findByDniIgnoreCase(DNI);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> actualizaJugador(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos, @RequestBody Jugador entrada) {
		Jugador jugador = jugadorRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_MODIFIED);
		}
		jugador.setNombre(entrada.getNombre());
		jugador.setApellidos(entrada.getApellidos());
		jugador.setGoles(entrada.getGoles());
		// jugador.setDorsal(entrada.getDorsal());
		jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
		jugador.setTarjetasRojas(entrada.getTarjetasRojas());
		jugador.setNacionalidad(entrada.getNacionalidad());
		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}/credenciales", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> actualizarUsuarioClave(@PathVariable String id, @RequestBody Jugador auxJugador) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_MODIFIED);
		}
		jugador.setNombreUsuario(auxJugador.getNombreUsuario());
		jugador.setClave(auxJugador.getClave());
		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Jugador> eliminarJugador(@PathVariable String id) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(jugador.getEquipo());
		if(equipo == null){
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		Liga liga = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
		liga.getGoleadores().remove(jugador);
		equipo.getPlantillaEquipo().remove(jugador);
		
		ligaRepository.save(liga);
		equipoRepository.save(equipo);
		jugadorRepository.delete(jugador);
		
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

}
