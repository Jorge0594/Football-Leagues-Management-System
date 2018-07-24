package API.Temporada;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends MongoRepository<Temporada, String> {
	
	Temporada findByNombre(String nombre);
}
