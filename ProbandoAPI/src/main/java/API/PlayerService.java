package API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerService {

	@Autowired
	PlayerRepository repositorio;

	@RequestMapping(method = RequestMethod.GET)
	public List<Player> verJugadores() {
		for (Player jugador : repositorio.findAll()) {
			System.out.println(jugador);
		}
		return repositorio.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Player insertarJugador(@RequestBody Player entrada) {
		return repositorio.save(entrada);

	}

}
