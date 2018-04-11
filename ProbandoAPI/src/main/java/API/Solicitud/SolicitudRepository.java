package API.Solicitud;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolicitudRepository extends MongoRepository<Solicitud, String> {
	
	Solicitud findById(String id);
	
	List<Solicitud> findByIp(String ip);
}
