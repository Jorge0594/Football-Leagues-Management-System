package API.Solicitud;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolicitudRepository extends MongoRepository<Solicitud, String> {
	
	Solicitud findById(String id);
	
	Solicitud findByEmail(String email);
	
	Solicitud findByIp(String ip);
}
