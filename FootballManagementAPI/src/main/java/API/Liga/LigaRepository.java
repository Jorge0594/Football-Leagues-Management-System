package API.Liga;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends MongoRepository<Liga, String> {
	
	Liga findByNombreIgnoreCase(String nombre);
	
	Liga findById(String id);
	
	@Query(value = "{}", fields = "{'nombre':1}")
	List<Liga> findCustomNombresLiga();
}
