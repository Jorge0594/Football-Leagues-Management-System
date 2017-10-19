package API.Arbitro;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArbitroRepository extends MongoRepository<Arbitro,String>{
	List<Arbitro> findByNombre(String nombre);

}
