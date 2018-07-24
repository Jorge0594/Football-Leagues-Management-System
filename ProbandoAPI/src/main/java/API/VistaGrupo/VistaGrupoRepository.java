package API.VistaGrupo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaGrupoRepository extends MongoRepository<VistaGrupo, String> {
	
	VistaGrupo findByIdGrupo(String idGrupo);
}
