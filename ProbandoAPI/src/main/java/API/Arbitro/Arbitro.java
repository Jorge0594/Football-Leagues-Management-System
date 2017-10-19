package API.Arbitro;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Arbitros")
public class Arbitro {
	@Id
	private String id;
	private String nombre;
	private String nombreUsuario;
	private String clave;
	private String fechaNacimiento;
	private int edad;
	private String lugarNacimient;
	private String comite;
	private String categoria;
	private String email;
	private String tlf;
	private boolean internacional;
	private String estado;

	public Arbitro() {
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

	public String getLugarNacimient() {
		return lugarNacimient;
	}

	public void setLugarNacimient(String lugarNacimient) {
		this.lugarNacimient = lugarNacimient;
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

	@Override
	public String toString() {
		return "Refree [id=" + id + ", nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", clave=" + clave
				+ ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", lugarNacimient=" + lugarNacimient
				+ ", comite=" + comite + ", categoria=" + categoria + ", email=" + email + ", tlf=" + tlf
				+ ", internacional=" + internacional + ", estado=" + estado + "]";
	}
}
