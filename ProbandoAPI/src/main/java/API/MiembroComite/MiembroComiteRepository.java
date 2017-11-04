package API.MiembroComite;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface MiembroComiteRepository extends MongoRepository<MiembroComite, String>  {
	List<MiembroComite> findAll();
    MiembroComite findById(String id);
	MiembroComite findByUsuarioIgnoreCase(String usuario);
	MiembroComite findByEmailIgnoreCase(String email);
}
