package API.MiembroComite;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro.ActaAtt;

@Document(collection="MiembroComite")		
public class MiembroComite {
	
	public interface PerfilMCAtt{}
	public interface AccesoAtt{}
	
	@JsonView(PerfilMCAtt.class)
	@Id
	private String id;
	@JsonView(PerfilMCAtt.class)
	private String nombre;
	@JsonView(PerfilMCAtt.class)
	private String apellidos;
	@JsonView(PerfilMCAtt.class)
	private String usuario;
	@JsonView(AccesoAtt.class)
	private String clave; 
	@JsonView(PerfilMCAtt.class)
	private String comite;
	@JsonView(PerfilMCAtt.class)
	private String email;
	
	public MiembroComite() {}
	
	public MiembroComite(String id, String nombre, String apellidos, String usuario, String clave, String comite, String email) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.clave = clave;
		this.comite = comite;
		this.email = email;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public String getApellidos() {
		return apellidos;
	}
	
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	
	public String getUsuario() {
		return usuario;
	}
	
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	public String getClave() {
		return clave;
	}
	
	
	public void setClaveSinEncriptar(String clave) {
		this.clave = clave;
	}
	
	public void setClaveEncriptada(String clave) {
		this.clave = new BCryptPasswordEncoder().encode(clave);
	}
	
	
	public String getComite() {
		return comite;
	}
	
	
	public void setComite(String comite) {
		this.comite = comite;
	}
	
	
	
	@Override
	public String toString() {
		return "MiembroComite [Id= " + id + ", Nombre= " + nombre + ", Apellidos= " + apellidos +", Usuario= " + usuario + ", Contraseña= " + clave
				+ ", Comite=" + comite + "]";
	}
	

}
