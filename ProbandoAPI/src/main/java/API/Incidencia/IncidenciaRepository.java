package API.Incidencia;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncidenciaRepository extends MongoRepository<Incidencia, String> {

	List<Incidencia> findAll();

	Incidencia findById(String id);

	List<Incidencia> findByIdPartidoIgnoreCase(String idPartido);

	List<Incidencia> findByIdJugadorIgnoreCase(String idJugador);

	List<Incidencia> findByTipoIgnoreCase(String tipoIncidencia);

}
