package API.Equipo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
public interface EquipoRepository extends MongoRepository<Equipo, String> {
	List<Equipo>findByLigaIgnoreCase(String liga);
	Equipo findByPosicion(int posicion);
	Equipo findByNombreIgnoreCase(String nombre);
	Equipo findById (String id);
	
}
