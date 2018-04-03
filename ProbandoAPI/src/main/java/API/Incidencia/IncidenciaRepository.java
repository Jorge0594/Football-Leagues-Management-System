package API.Incidencia;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncidenciaRepository extends MongoRepository<Incidencia, String> {

	List<Incidencia> findAll();

	Incidencia findById(String id);

	List<Incidencia> findByIdPartido(String idPartido);

	List<Incidencia> findByIdJugador(String idJugador);

	List<Incidencia> findByTipo(String tipoIncidencia);

}
