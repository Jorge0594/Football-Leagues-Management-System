package API.Team;

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

import API.Player.Player;
import API.Player.PlayerRepository;

@RestController
@RequestMapping(value = "/equipos")

public class TeamController {

	public interface RankView extends Team.RankAtt {}
	public interface ProfileView extends Team.RankAtt, Team.ProfileAtt, Player.TeamAtt {}
	public interface PlayerView extends Player.ProfileAtt, Player.TeamAtt{}

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private PlayerRepository playerRepository;

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Team> crearEquipo(@RequestBody Team equipo) {
		equipo.setImagenEquipo("imageTeamDafault.png");
		teamRepository.save(equipo);
		return new ResponseEntity<Team>(equipo, HttpStatus.CREATED);
	}

	@JsonView(RankView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Team>> verEquipos() {
		return new ResponseEntity<List<Team>>(teamRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(RankView.class)
	@RequestMapping(value = "/liga/{liga}", method = RequestMethod.GET)
	public ResponseEntity<List<Team>> verEquiposLiga(@PathVariable String liga) {
		List<Team> equipos = teamRepository.findByLigaIgnoreCase(liga);
		if (equipos.isEmpty()) {
			return new ResponseEntity<List<Team>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Team>>(equipos, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<Team> verEquipoNombre(@PathVariable String nombre) {
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Team>(equipo, HttpStatus.OK);
	}
	
	@JsonView(PlayerView.class)
	@RequestMapping(value = "/{nombre}/plantilla", method = RequestMethod.GET)
	public ResponseEntity<List<Player>>verEquipoPantilla(@PathVariable String nombre){
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		if(equipo == null){
			return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Player>>(equipo.getPlantillaEquipo(),HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.PUT)
	public ResponseEntity<Team> modificarPerfilEquipo(@PathVariable String nombre, @RequestBody Team auxEquipo) {
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		equipo.setNombre(auxEquipo.getNombre());
		equipo.setLiga(auxEquipo.getLiga());
		equipo.setCiudad(auxEquipo.getCiudad());

		for (Player jugador : equipo.getPlantillaEquipo()) {
			jugador.setEquipo(auxEquipo.getNombre());
		}

		teamRepository.save(equipo);

		return new ResponseEntity<Team>(equipo, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/resultado", method = RequestMethod.PUT)
	public ResponseEntity<Team> modificarEquipoPostPartido(@PathVariable String nombre, @RequestBody Team auxEquipo) {
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		equipo.setGoles(auxEquipo.getGoles());
		equipo.setGolesEncajados(auxEquipo.getGolesEncajados());
		equipo.setPartidosEmpatados(auxEquipo.getPartidosEmpatados());
		equipo.setPartidosGanados(auxEquipo.getPartidosGanados());
		equipo.setPartidosJugados(auxEquipo.getPartidosJugados());
		equipo.setPartidosPerdidos(auxEquipo.getPartidosPerdidos());
		equipo.setPuntos(auxEquipo.getPuntos());

		teamRepository.save(equipo);

		return new ResponseEntity<Team>(equipo, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/posicion", method = RequestMethod.PUT)
	public ResponseEntity<Team> modificarEquipoPosicion(@PathVariable String nombre, @RequestBody Team auxEquipo) {
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		if (equipo == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		equipo.setPosicion(auxEquipo.getPosicion());

		teamRepository.save(equipo);

		return new ResponseEntity<Team>(equipo, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Team> añadirJugadorEquipo(@PathVariable String id, @PathVariable String nombre) {
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		Player jugador = playerRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		jugador.setEquipo(equipo.getNombre());
		equipo.getPlantillaEquipo().add(jugador);

		playerRepository.save(jugador);
		teamRepository.save(equipo);

		return new ResponseEntity<Team>(equipo, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Team> eliminarJugadorEquipo(@PathVariable String id, @PathVariable String nombre) {
		Team equipo = teamRepository.findByNombreIgnoreCase(nombre);
		Player jugador = playerRepository.findById(id);
		if (equipo == null || jugador == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		if (equipo.getPlantillaEquipo().contains(jugador)) {
			jugador.setEquipo("");
			equipo.getPlantillaEquipo().remove(jugador);

			playerRepository.save(jugador);
			teamRepository.save(equipo);

			return new ResponseEntity<Team>(equipo, HttpStatus.OK);
		}
		return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
	}
	
	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Team>eliminarEquipo (@PathVariable String id){
		Team equipo = teamRepository.findById(id);
		if (equipo == null) {
			return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
		}
		for(Player j : equipo.getPlantillaEquipo()){
			j.setEquipo("");
			playerRepository.save(j);
		}
		
		teamRepository.delete(equipo);
		return new ResponseEntity<Team>(equipo,HttpStatus.OK);
	}
}
