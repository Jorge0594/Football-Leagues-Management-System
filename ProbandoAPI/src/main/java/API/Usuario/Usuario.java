package API.Usuario;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "Usuario")
public class Usuario {

	@Id
	private String id;
	private String nombreUsuario;
	private String clave;
	private String rol;

	public Usuario() {
	}

	public Usuario(String id, String nombreUsuario, String clave, String rol) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.clave = new BCryptPasswordEncoder().encode(clave);
		this.rol = rol;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = new BCryptPasswordEncoder().encode(clave);
	}

	public String getRol() {
		return rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", clave=" + clave + ", rol=" + rol + "]";
	}

}
