package API.UsuarioTemporal;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioTemporalRepository extends MongoRepository<UsuarioTemporal, String>{
	
	UsuarioTemporal findById(String id);
	
	UsuarioTemporal findByNombreUsuarioIgnoreCase(String nombreuUsuario);
	
	UsuarioTemporal findByEmail(String email);
	
	UsuarioTemporal findByIp(String ip);
	
	UsuarioTemporal findByEquipoId(String equipoId);
}
