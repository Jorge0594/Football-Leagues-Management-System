package API.Partido;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartidoRepository extends MongoRepository<Partido, String> {
	Partido findById(String id);

	List<Partido> findByLigaIgnoreCase(String liga);

	List<Partido> findByJornada(int jornada);

	List<Partido> findByJornadaAndLigaIgnoreCase(int jornada, String liga);

	List<Partido> findByEquipoLocalId(String equipoLocalId);

	List<Partido> findByEquipoVisitanteId(String equipoVisitanteId);

	List<Partido> findByEquipoVisitanteIdOrEquipoLocalId(String equipoLocalId, String equipoVisitanteId);

	List<Partido> findByIdArbitro(String idArbitro);

	List<Partido> findByIdArbitroAndEquipoLocalId(String id, String equipoLocalId);

	List<Partido> findByIdArbitroAndEquipoVisitanteId(String id, String equipoVisitanteId);

	List<Partido> findByIdArbitroAndEquipoLocalIdOrEquipoVisitanteId(String id, String equipoLocalId, String equipoVisitanteId);
	
	List<Partido> findByIdArbitroAndEstadoIgnoreCase(String idArbitro, String estado);
	
}
