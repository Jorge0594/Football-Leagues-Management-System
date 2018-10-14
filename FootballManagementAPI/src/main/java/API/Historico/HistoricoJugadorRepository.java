package API.Historico;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface HistoricoJugadorRepository extends MongoRepository<HistoricoJugador, String>{
	
	List<HistoricoJugador>findByJugadorId(ObjectId id);
	
	HistoricoJugador findById(String id);
	
	@Query(value = "{'jugador.id':?0}", fields = "{'jugador.goles':1, 'jugador.tarjetasAmarillas':1, 'jugador.tarjetasRojas':1, 'temporada.nombre':1}")
	List<HistoricoJugador>findCustomHisotoricoJugador(ObjectId id);

}
