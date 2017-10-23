package API.MiembroComite;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface MiembroComiteRepositorio extends MongoRepository<MiembroComite, String>  {
	List<MiembroComite> findAll();
    MiembroComite findById(String id);
}
