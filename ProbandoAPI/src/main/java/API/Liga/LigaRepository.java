package API.Liga;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface LigaRepository extends MongoRepository<Liga, String> {
	Liga findByNombreIgnoreCase(String nombre);
	
	Liga findById(String id);
	
	List<Liga> findByArbitrosId(String id);
	
	@Query(value = "{}", fields = "{'nombre':1}")
	List<Liga> findCustomNombresLiga();
	
	@Query(value = "{'nombre':?0}", fields = "{'_id':0, 'clasificacion': 1}")
	Liga findCustomLigaClasificacion(String nombre);
	
	@Query(value = "{'nombre':?0}", fields = "{'_id':0, 'goleadores': 1}")
	Liga findCustomLigaGoleadores(String nombre);

}
