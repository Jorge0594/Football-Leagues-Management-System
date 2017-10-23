package API.Incidencia;

import java.util.List;

public interface IncidenciaRepository {

	List<Incidencia> findAll();

	Incidencia findById(String id);

	Incidencia findByIdPartidoIgnoreCase(String idPartido);

}
