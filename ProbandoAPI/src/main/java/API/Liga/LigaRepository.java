package API.Liga;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends MongoRepository<Liga, String> {
	
	Liga findByNombre(String nombre);
}
