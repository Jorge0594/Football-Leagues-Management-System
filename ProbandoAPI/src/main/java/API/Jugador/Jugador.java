package API.Jugador;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection="Jugadores")
public class Jugador {
	
	public interface EquipoAtt{}
	public interface PerfilAtt {}
	public interface ClaveAtt{}
	
	@JsonView(EquipoAtt.class)
	@Id
	private String id;
	
	@JsonView(EquipoAtt.class)
	private String nombre;
	
	@JsonView(EquipoAtt.class)
	private String apellidos;
	
	@JsonView(PerfilAtt.class)
	private String dni;
	
	@JsonView(PerfilAtt.class)
	private String nombreUsuario;
	
	@JsonView(ClaveAtt.class)
	private String clave;
	
	@JsonView(EquipoAtt.class)
	private String fotoJugador;
	
	@JsonView(EquipoAtt.class)
	private String equipo;
	
	@JsonView(EquipoAtt.class)
	private String posicion;
	
	@JsonView(PerfilAtt.class)
	private String fechaSancion;
	
	@JsonView(PerfilAtt.class)
	private String estado;
	
	@JsonView(PerfilAtt.class)
	private String nacionalidad;
	
	@JsonView(EquipoAtt.class)
	private int dorsal;
	
	@JsonView(PerfilAtt.class)
	private int goles;
	
	@JsonView(PerfilAtt.class)
	private int tarjetasAmarillas;
	
	@JsonView(PerfilAtt.class)
	private int tarjetasRojas;
	
	@JsonView(EquipoAtt.class)
	private boolean capitan;
	
	
	public Jugador() {};
	
	
	public Jugador(String nombre, String apellidos, String nombreUsuario, String clave, String equipo, String posicion, String fechaSancion, String estado,
			int dorsal, int goles, int tarjetasAmarillas, int tarjetasRojas, String nacionalidad,
			boolean capitan) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.equipo = equipo;
		this.posicion = posicion;
		this.fechaSancion = fechaSancion;
		this.estado = estado;
		this.dorsal = dorsal;
		this.goles = goles;
		this.tarjetasAmarillas = tarjetasAmarillas;
		this.tarjetasRojas = tarjetasRojas;
		this.nacionalidad = nacionalidad;
		this.capitan = capitan;
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


	public String getFechaSancion() {
		return fechaSancion;
	}


	public void setFechaSancion(String fechaSancion) {
		this.fechaSancion = fechaSancion;
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


	@Override
	public String toString() {
		return "Player [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni
				+ ", nombreUsuario=" + nombreUsuario + ", clave=" + clave + ", fotoJugador=" + fotoJugador
				+ ", equipo=" + equipo + ", posicion=" + posicion + ", fechaSancion=" + fechaSancion + ", estado="
				+ estado + ", nacionalidad=" + nacionalidad + ", dorsal=" + dorsal + ", goles="
				+ goles + ", tarjetasAmarillas=" + tarjetasAmarillas + ", tarjetasRojas=" + tarjetasRojas + ", capitan="
				+ capitan + "]";
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
		if (fechaSancion == null) {
			if (other.fechaSancion != null)
				return false;
		} else if (!fechaSancion.equals(other.fechaSancion))
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
		if (tarjetasAmarillas != other.tarjetasAmarillas)
			return false;
		if (tarjetasRojas != other.tarjetasRojas)
			return false;
		return true;
	}

	

}
