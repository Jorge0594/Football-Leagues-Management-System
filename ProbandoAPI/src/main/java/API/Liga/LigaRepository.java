package API.Liga;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LigaRepository extends MongoRepository<Liga, String> {
	Liga findByNombreIgnoreCase(String nombre);
	Liga findById(String id);

}
