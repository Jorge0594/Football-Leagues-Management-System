package API.Liga;

import API.Jugador.*;
import API.Equipo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value="/liga")
public class LigaController {
	
	public interface GoleadoresView extends Jugador.EquipoAtt {}
	public interface ClasificacionView extends Equipo.RankAtt{}
	
	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private LigaRepository ligaRepository;
}
