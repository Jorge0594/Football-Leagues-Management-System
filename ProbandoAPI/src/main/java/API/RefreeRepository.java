package API;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreeRepository extends MongoRepository<Refree,String>{
	List<Refree> findByNombre(String name);

}
