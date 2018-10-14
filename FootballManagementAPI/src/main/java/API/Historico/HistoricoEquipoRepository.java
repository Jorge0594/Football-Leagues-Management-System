package API.Historico;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface HistoricoEquipoRepository extends MongoRepository<HistoricoEquipo, String> {
	
	List<HistoricoEquipo> findByEquipoId(ObjectId id);
	
	HistoricoEquipo findById(String id);
	
	@Query(value = "{'equipo.id':?0}", fields = "{'equipo.posicion':1, 'equipo.puntos':1, 'equipo.partidosEmpatados':1, 'equipo.partidosGanados':1, 'equipo.partidosPerdidos':1, 'equipo.partidosJugados':1, 'equipo.goles':1, 'equipo.golesEncajados':1, 'temporada.nombre':1}")
	List<HistoricoEquipo> findCustomHistorico(ObjectId id);
}
