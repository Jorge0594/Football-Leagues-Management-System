package API.Historico;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoEquipoRepository extends MongoRepository<HistoricoEquipo, String> {
	
	List<HistoricoEquipo> findByEquipoId(ObjectId id);
	
	HistoricoEquipo findById(String id);
}
