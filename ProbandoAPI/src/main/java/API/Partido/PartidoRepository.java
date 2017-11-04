package API.Partido;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartidoRepository extends MongoRepository<Partido, String> {
	public Partido findById(String id);

	public List<Partido> findByLiga(String liga);

	public List<Partido> findByJornada(String jornada);

	public List<Partido> findByEquipoLocalNombreIgnoreCase(String equipoLocalId);

	public List<Partido> findByEquipoVisitanteId(String equipoVisitanteId);

	public List<Partido> findByIdArbitro(String idArbitro);

}
