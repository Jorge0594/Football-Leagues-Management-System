package API.Peticion;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface PeticionRepository extends MongoRepository<Peticion, String>{

}
