package API.Jugador;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonView;

import API.Sancion.Sancion;

@Document(collection = "Jugador")
public class Jugador implements Comparable<Jugador> {

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
	
	@JsonView(EquipoAtt.class)
	private int edad;
	
	@JsonView(PerfilAtt.class)
	private boolean aceptado;
	
	@JsonView(PerfilAtt.class)
	private String fechaNacimiento;

	@JsonView(PerfilAtt.class)
	private String dni;

	@JsonView(PerfilAtt.class)
	private String nombreUsuario;

	@JsonView(ClaveAtt.class)
	private String clave;

	@JsonView(PerfilAtt.class)
	private String email;

	@JsonView(EquipoAtt.class)
	private String fotoJugador;

	@JsonView(EquipoAtt.class)
	private String equipo;

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
	private boolean capitan;
	
	@JsonView(PerfilAtt.class)
	@DBRef
	private List<Sancion> sanciones;

	public Jugador() {
	};


	public Jugador(String id, String nombre, String apellidos, int edad, boolean aceptado, String fechaNacimiento,
			String dni, String nombreUsuario, String clave, String email, String fotoJugador, String equipo,
			String posicion, String estado, String lugarNacimiento, String nacionalidad, int dorsal, int goles,
			int tarjetasAmarillas, int tarjetasRojas, boolean capitan, List<Sancion> sanciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.aceptado = aceptado;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.email = email;
		this.fotoJugador = fotoJugador;
		this.equipo = equipo;
		this.posicion = posicion;
		this.estado = estado;
		this.lugarNacimiento = lugarNacimiento;
		this.nacionalidad = nacionalidad;
		this.dorsal = dorsal;
		this.goles = goles;
		this.tarjetasAmarillas = tarjetasAmarillas;
		this.tarjetasRojas = tarjetasRojas;
		this.capitan = capitan;
		this.sanciones = sanciones;
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

	public void setTarjetasRojas(int tarjetasRojas) {
		this.tarjetasRojas = tarjetasRojas;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public boolean isCapitan() {
		return capitan;
	}

	public void setCapitan(boolean capitan) {
		this.capitan = capitan;
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
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


	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", edad=" + edad
				+ ", aceptado=" + aceptado + ", fechaNacimiento=" + fechaNacimiento + ", dni=" + dni
				+ ", nombreUsuario=" + nombreUsuario + ", clave=" + clave + ", email=" + email + ", fotoJugador="
				+ fotoJugador + ", equipo=" + equipo + ", posicion=" + posicion + ", estado=" + estado
				+ ", lugarNacimiento=" + lugarNacimiento + ", nacionalidad=" + nacionalidad + ", dorsal=" + dorsal
				+ ", goles=" + goles + ", tarjetasAmarillas=" + tarjetasAmarillas + ", tarjetasRojas=" + tarjetasRojas
				+ ", capitan=" + capitan + ", sanciones=" + sanciones + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aceptado ? 1231 : 1237);
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + (capitan ? 1231 : 1237);
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + dorsal;
		result = prime * result + edad;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((fotoJugador == null) ? 0 : fotoJugador.hashCode());
		result = prime * result + goles;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lugarNacimiento == null) ? 0 : lugarNacimiento.hashCode());
		result = prime * result + ((nacionalidad == null) ? 0 : nacionalidad.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		result = prime * result + ((posicion == null) ? 0 : posicion.hashCode());
		result = prime * result + ((sanciones == null) ? 0 : sanciones.hashCode());
		result = prime * result + tarjetasAmarillas;
		result = prime * result + tarjetasRojas;
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
		Jugador other = (Jugador) obj;
		if (aceptado != other.aceptado)
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (capitan != other.capitan)
			return false;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (dorsal != other.dorsal)
			return false;
		if (edad != other.edad)
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
		if (fotoJugador == null) {
			if (other.fotoJugador != null)
				return false;
		} else if (!fotoJugador.equals(other.fotoJugador))
			return false;
		if (goles != other.goles)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lugarNacimiento == null) {
			if (other.lugarNacimiento != null)
				return false;
		} else if (!lugarNacimiento.equals(other.lugarNacimiento))
			return false;
		if (nacionalidad == null) {
			if (other.nacionalidad != null)
				return false;
		} else if (!nacionalidad.equals(other.nacionalidad))
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
		if (posicion == null) {
			if (other.posicion != null)
				return false;
		} else if (!posicion.equals(other.posicion))
			return false;
		if (sanciones == null) {
			if (other.sanciones != null)
				return false;
		} else if (!sanciones.equals(other.sanciones))
			return false;
		if (tarjetasAmarillas != other.tarjetasAmarillas)
			return false;
		if (tarjetasRojas != other.tarjetasRojas)
			return false;
		return true;
	}


	@Override
	public int compareTo(Jugador o) {
		if (this.goles > o.goles) {
			return -1;
		} else if (this.goles < o.goles) {
			return 1;
		}
		return 0;
	}

}
