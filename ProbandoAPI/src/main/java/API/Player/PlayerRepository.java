package API.Player;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PlayerRepository extends MongoRepository<Player, String> {
	List<Player> findByNombreIgnoreCase(String name);
	Player findByNombreAndApellidosAllIgnoreCase(String name, String surname);
}
