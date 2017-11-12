package API.Partido;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartidoRepository extends MongoRepository<Partido, String> {
	Partido findById(String id);

	List<Partido> findByLiga(String liga);

	List<Partido> findByJornada(String jornada);

	List<Partido> findByEquipoLocalId(ObjectId equipoLocalId);

	List<Partido> findByEquipoVisitanteId(ObjectId equipoVisitanteId);

	List<Partido> findByIdArbitro(String idArbitro);
	
	List<Partido> findByIdArbitroAndEquipoLocalIdAndEquipoVisitanteId(String id, ObjectId equipoLocalId, Object equipoVisitanteId);

}
