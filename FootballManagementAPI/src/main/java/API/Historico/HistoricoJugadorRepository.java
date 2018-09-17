package API.Historico;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoJugadorRepository extends MongoRepository<HistoricoJugador, String>{
	
	List<HistoricoJugador>findByJugadorId(ObjectId id);
	
	HistoricoJugador findById(String id);

}
