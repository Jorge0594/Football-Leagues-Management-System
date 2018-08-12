package API.Jugador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonView;

import API.Sancion.Sancion;
import API.Vistas.VistaGrupo;

@Document(collection = "Jugador")
public class Jugador  {

	public interface EquipoAtt {
	}

	public interface PerfilAtt {
	}

	public interface ClaveAtt {
	}

	@JsonView(EquipoAtt.class)
	@Id
	private String id;

	@JsonView(EquipoAtt.class)
	private String nombre;

	@JsonView(EquipoAtt.class)
	private String apellidos;
	
	@JsonView(PerfilAtt.class)
	private boolean aceptado;
	
	@JsonView(PerfilAtt.class)
	private String fechaNacimiento;

	@JsonView(PerfilAtt.class)
	private String dni;

	@JsonView(PerfilAtt.class)
	private String nombreUsuario = "";

	@JsonView(ClaveAtt.class)
	private String clave;

	@JsonView(PerfilAtt.class)
	private String email;

	@JsonView(EquipoAtt.class)
	private String fotoJugador;

	@JsonView(EquipoAtt.class)
	private String equipo;
	
	@JsonView(PerfilAtt.class)
	private VistaGrupo grupo;
	
	@JsonView(PerfilAtt.class)
	private String liga;

	@JsonView(EquipoAtt.class)
	private String posicion;

	@JsonView(PerfilAtt.class)
	private String estado;
	
	@JsonView(PerfilAtt.class)
	private String lugarNacimiento;

	@JsonView(EquipoAtt.class)
	private String nacionalidad;

	@JsonView(EquipoAtt.class)
	private int dorsal;

	@JsonView(EquipoAtt.class)
	private int goles;

	@JsonView(PerfilAtt.class)
	private int tarjetasAmarillas;

	@JsonView(PerfilAtt.class)
	private int tarjetasRojas;

	@JsonView(EquipoAtt.class)
	private boolean delegado;
	
	@JsonView(PerfilAtt.class)
	@DBRef
	private List<Sancion> sanciones = new ArrayList<>();

	public Jugador() {
	};


	public Jugador(String id, String nombre, String apellidos, boolean aceptado, String fechaNacimiento,
			String dni, String nombreUsuario, String clave, String email, String fotoJugador, String equipo,
			String posicion, String estado, String lugarNacimiento, String nacionalidad, int dorsal, int goles,
			int tarjetasAmarillas, int tarjetasRojas, boolean delegado, List<Sancion> sanciones, VistaGrupo grupo, String liga) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.aceptado = aceptado;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.email = email;
		this.fotoJugador = "defaultProfile.jpg";
		this.equipo = equipo;
		this.posicion = posicion;
		this.estado = estado;
		this.lugarNacimiento = lugarNacimiento;
		this.nacionalidad = nacionalidad;
		this.dorsal = dorsal;
		this.goles = goles;
		this.tarjetasAmarillas = tarjetasAmarillas;
		this.tarjetasRojas = tarjetasRojas;
		this.delegado = delegado;
		this.sanciones = sanciones;
		this.liga = liga;
		this.grupo = grupo;
	}
	
	public Jugador(String nombre, String apellidos, String fechaNacimiento, String dni, String email, String fotoJugador, String posicion, String lugarNacimiento, String nacionalidad, int dorsal, boolean delegado) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.email = email;
		this.fotoJugador = fotoJugador;
		this.posicion = posicion;
		this.lugarNacimiento = lugarNacimiento;
		this.nacionalidad = nacionalidad;
		this.dorsal = dorsal;
		this.delegado = delegado;
		this.aceptado = false;
		this.fotoJugador = "defaultProfile.jpg";
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

	public void setClaveEncriptada(String clave) {
		this.clave = new BCryptPasswordEncoder().encode(clave);
	}
	
	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getDorsal() {
		return dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	public int getGoles() {
		return goles;
	}

	public void setGoles(int goles) {
		this.goles = goles;
	}

	public int getTarjetasAmarillas() {
		return tarjetasAmarillas;
	}

	public void setTarjetasAmarillas(int tarjetasAmarillas) {
		this.tarjetasAmarillas = tarjetasAmarillas;
	}

	public int getTarjetasRojas() {
		return tarjetasRojas;
	}

	public VistaGrupo getGrupo() {
		return grupo;
	}


	public void setGrupo(VistaGrupo grupo) {
		this.grupo = grupo;
	}


	public void setTarjetasRojas(int tarjetasRojas) {
		this.tarjetasRojas = tarjetasRojas;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public boolean isDelegado() {
		return delegado;
	}

	public void setDelegado(boolean delegado) {
		this.delegado = delegado;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getFotoJugador() {
		return fotoJugador;
	}

	public void setFotoJugador(String fotoJugador) {
		this.fotoJugador = fotoJugador;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}


	public List<Sancion> getSanciones() {
		return sanciones;
	}


	public void setSanciones(List<Sancion> sanciones) {
		this.sanciones = sanciones;
	}

	public String getLiga() {
		return liga;
	}


	public void setLiga(String liga) {
		this.liga = liga;
	}
	
	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", aceptado=" + aceptado + ", fechaNacimiento=" + fechaNacimiento + ", dni=" + dni + ", nombreUsuario=" + nombreUsuario + ", clave=" + clave + ", email=" + email + ", fotoJugador="
				+ fotoJugador + ", equipo=" + equipo + ", grupo=" + grupo + ", liga=" + liga + ", posicion=" + posicion + ", estado=" + estado + ", lugarNacimiento=" + lugarNacimiento + ", nacionalidad=" + nacionalidad + ", dorsal=" + dorsal + ", goles=" + goles + ", tarjetasAmarillas="
				+ tarjetasAmarillas + ", tarjetasRojas=" + tarjetasRojas + ", delegado=" + delegado + ", sanciones=" + sanciones + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}

}
