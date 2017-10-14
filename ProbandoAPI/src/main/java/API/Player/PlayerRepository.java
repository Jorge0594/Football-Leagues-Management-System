package API.Player;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PlayerRepository extends MongoRepository<Player, String> {
	List<Player> findByNombreIgnoreCase(String name);
	List<Player>findByEquipoIgnoreCase(String equipo);
	List<Player>findByEstadoIgnoreCase(String estado);
	List<Player>findByEstadoAndEquipoAllIgnoreCase(String estado, String equipo);
	Player findByNombreAndApellidosAllIgnoreCase(String name, String surname);
	Player findById(String id);
	Player findByDniIgnoreCase(String dni);
	Player findByCapitanAndEquipoIgnoreCase(boolean capitan, String equipo);
	
}
