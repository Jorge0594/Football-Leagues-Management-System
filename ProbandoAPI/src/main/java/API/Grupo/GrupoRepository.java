package API.Grupo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GrupoRepository extends MongoRepository<Grupo, String> {
	Grupo findByNombreIgnoreCase(String nombre);
	
	Grupo findById(String id);
	
	@Query(value = "{}", fields = "{'nombre':1}")
	List<Grupo> findCustomNombresGrupo();
	
	@Query(value = "{'id':?0}", fields = "{'goleadores': 1}")
	Grupo findCustomLigaGoleadores(String nombre);
	
	@Query(value = "{'id':?0}", fields = "{'temporada': 1, 'nombre':1}")
	Grupo findCustomTemporada(String id);
}
