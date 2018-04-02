package API.Partido;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartidoRepository extends MongoRepository<Partido, String> {
	Partido findById(String id);

	List<Partido> findByLigaIgnoreCase(String liga);

	List<Partido> findByJornada(int jornada);

	List<Partido> findByJornadaAndLigaIgnoreCase(int jornada, String liga);

	List<Partido> findByEquipoLocalId(ObjectId equipoLocalId);

	List<Partido> findByEquipoVisitanteId(ObjectId equipoVisitanteId);

	List<Partido> findByEquipoVisitanteIdOrEquipoLocalId(String equipoLocal, String equipoVisitante);

	List<Partido> findByIdArbitro(String idArbitro);

	List<Partido> findByIdArbitroAndEquipoLocalId(String id, String equipoLocalId);

	List<Partido> findByIdArbitroAndEquipoVisitanteId(String id, String equipoVisitanteId);

	List<Partido> findByIdArbitroAndEquipoLocalIdOrEquipoVisitanteId(String id, String equipoLocalId,
			String equipoVisitanteId);

}
