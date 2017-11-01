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
		this.clave = new BCryptPasswordEncoder().encode(clave);
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
		this.clave = new BCryptPasswordEncoder().encode(clave);
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
}
