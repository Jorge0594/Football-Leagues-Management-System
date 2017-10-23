package API.Liga;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface LigaRepository extends MongoRepository<Liga, String> {
	Liga findByNombre(String nombre);

}
