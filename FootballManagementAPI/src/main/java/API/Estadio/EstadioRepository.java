package API.Estadio;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstadioRepository extends MongoRepository<Estadio,String>{
	public Estadio findById(String id);
	public Estadio findByNombre(String nombre);
	
}
