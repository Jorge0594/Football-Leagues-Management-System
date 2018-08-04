package API.Temporada;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends MongoRepository<Temporada, String> {
	
	List<Temporada> findByNombre(String nombre);
	
	Temporada findById(String id);
	
	Temporada findByLigaAndNombreAllIgnoreCase(String liga, String nombre);
}
