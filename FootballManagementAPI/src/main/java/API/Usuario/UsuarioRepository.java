package API.Usuario;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	Usuario findByNombreUsuarioIgnoreCase(String nombreUsuario);

	Usuario findById(String id);
}
