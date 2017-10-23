package API.Arbitro;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArbitroRepository extends MongoRepository<Arbitro,String>{
	Arbitro findById(String id);
	Arbitro findByDni(String dni);
	Arbitro findByNombreUsuario(String nombreUsuario);
	List<Arbitro> findByComite(String comite);
	List<Arbitro> findByNombre(String nombre);
	
}
