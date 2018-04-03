package API.UsuarioTemporal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "UsuarioTemporal")
public class UsuarioTemporal {
	
	private String id;
	private String ip;
	private String nombreUsuario;
	private String clave;
	private String nombre;
	private String apellidos;
	private String email;
	private String campus;
	private String[] equipo = new String[2]; //0:id 1:nombre
	private String primerAcceso = "";
	private String fechaCaducidad = "";
	
	public UsuarioTemporal(){
		
	}

	public UsuarioTemporal(String id, String ip, String nombreUsuario, String clave, String nombre, String apellidos,
			String email, String campus) {
		super();
		this.id = id;
		this.ip = ip;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.campus = campus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
		this.clave = clave;
	}
	
	public void setClaveEncriptada(String clave){
		this.clave = new BCryptPasswordEncoder().encode(clave);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String[] getEquipo() {
		return this.equipo;
	}

	public void setEquipo(String[] equipo) {
		this.equipo = equipo;
	}

	public String getPrimerAcceso() {
		return primerAcceso;
	}

	public void setPrimerAcceso(String primerAcceso) {
		this.primerAcceso = primerAcceso;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	@Override
	public String toString() {
		return "UsuarioTemporal [id=" + id + ", ip=" + ip + ", nombreUsuario=" + nombreUsuario + ", clave=" + clave
				+ ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email + ", campus=" + campus
				+ ", equipo=" + equipo + ", primerAcceso=" + primerAcceso + ", fechaCaducidad=" + fechaCaducidad + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((campus == null) ? 0 : campus.hashCode());
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioTemporal other = (UsuarioTemporal) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (campus == null) {
			if (other.campus != null)
				return false;
		} else if (!campus.equals(other.campus))
			return false;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (equipo == null) {
			if (other.equipo != null)
				return false;
		} else if (!equipo.equals(other.equipo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreUsuario == null) {
			if (other.nombreUsuario != null)
				return false;
		} else if (!nombreUsuario.equals(other.nombreUsuario))
			return false;
		return true;
	}
	

	
	
	
	
	
	

}
