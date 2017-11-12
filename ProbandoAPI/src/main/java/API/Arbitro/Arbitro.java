package API.Arbitro;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import API.Partido.Partido;

@Document(collection = "Arbitros")
public class Arbitro {
	@Id
	private String id;
	private String dni;
	private String nombre;
	private String nombreUsuario;
	private String clave;
	private List<Partido> partidosArbitrados;
	private String fechaNacimiento;
	private int edad;
	private String lugarNacimiento;
	private String comite;
	private String categoria;
	private String email;
	private String tlf;
	private boolean internacional;
	private String estado;

	public Arbitro() {
	}

	public Arbitro(String dni, String nombre, String nombreUsuario, String clave, List<Partido> partidosArbitrados,
			String fechaNacimiento, int edad, String lugarNacimiento, String comite, String categoria, String email,
			String tlf, boolean internacional, String estado) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.partidosArbitrados = partidosArbitrados;
		this.fechaNacimiento = fechaNacimiento;
		this.edad = edad;
		this.lugarNacimiento = lugarNacimiento;
		this.comite = comite;
		this.categoria = categoria;
		this.email = email;
		this.tlf = tlf;
		this.internacional = internacional;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimient) {
		this.lugarNacimiento = lugarNacimient;
	}

	public String getComite() {
		return comite;
	}

	public void setComite(String comite) {
		this.comite = comite;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public boolean isInternacional() {
		return internacional;
	}

	public void setInternacional(boolean internacional) {
		this.internacional = internacional;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	public void setClaveEncriptada(String clave) {
		this.clave =new BCryptPasswordEncoder().encode(clave);
	}

	public List<Partido> getPartidosArbitrados() {
		return partidosArbitrados;
	}

	public void setPartidosArbitrados(List<Partido> partidosArbitrados) {
		this.partidosArbitrados = partidosArbitrados;
	}

	@Override
	public String toString() {
		return "Refree [id=" + id + ", nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", clave=" + clave
				+ ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", lugarNacimient=" + lugarNacimiento
				+ ", comite=" + comite + ", categoria=" + categoria + ", email=" + email + ", tlf=" + tlf
				+ ", internacional=" + internacional + ", estado=" + estado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((comite == null) ? 0 : comite.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + edad;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (internacional ? 1231 : 1237);
		result = prime * result + ((lugarNacimiento == null) ? 0 : lugarNacimiento.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		result = prime * result + ((partidosArbitrados == null) ? 0 : partidosArbitrados.hashCode());
		result = prime * result + ((tlf == null) ? 0 : tlf.hashCode());
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
		Arbitro other = (Arbitro) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (comite == null) {
			if (other.comite != null)
				return false;
		} else if (!comite.equals(other.comite))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (edad != other.edad)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internacional != other.internacional)
			return false;
		if (lugarNacimiento == null) {
			if (other.lugarNacimiento != null)
				return false;
		} else if (!lugarNacimiento.equals(other.lugarNacimiento))
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
		if (partidosArbitrados == null) {
			if (other.partidosArbitrados != null)
				return false;
		} else if (!partidosArbitrados.equals(other.partidosArbitrados))
			return false;
		if (tlf == null) {
			if (other.tlf != null)
				return false;
		} else if (!tlf.equals(other.tlf))
			return false;
		return true;
	}
	
	
}
