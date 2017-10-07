package API;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PlayerRepository extends MongoRepository<Player, String> {
	List<Player> findByNombre(String name);
	Player findByNombreAndApellidos(String name, String surname);
}
