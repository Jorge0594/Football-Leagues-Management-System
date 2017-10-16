package API.Team;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TeamRepository extends MongoRepository<Team, String> {
	List<Team>findByLigaIgnoreCase(String liga);
	Team findByPosicion(int posicion);
	Team findByNombreIgnoreCase(String nombre);
	Team findById (String id);
}
