package API.Player;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection="Jugadores")
public class Player {
	
	public interface TeamAtt{}
	public interface ProfileAtt {}
	public interface PasswordAtt{}
	
	@JsonView(TeamAtt.class)
	@Id
	private String id;
	
	@JsonView(TeamAtt.class)
	private String nombre;
	
	@JsonView(TeamAtt.class)
	private String apellidos;
	
	@JsonView(ProfileAtt.class)
	private String DNI;
	
	@JsonView(ProfileAtt.class)
	private String nombreUsuario;
	
	@JsonView(PasswordAtt.class)
	private String contraseña;
	
	@JsonView(TeamAtt.class)
	private String fotoJugador;
	
	@JsonView(TeamAtt.class)
	private String equipo;
	
	@JsonView(TeamAtt.class)
	private String posicion;
	
	@JsonView(ProfileAtt.class)
	private String fechaSancion;
	
	@JsonView(ProfileAtt.class)
	private String estado;
	
	@JsonView(ProfileAtt.class)
	private String nacionalidad;
	
	@JsonView(ProfileAtt.class)
	private int faltas;
	
	@JsonView(TeamAtt.class)
	private int dorsal;
	
	@JsonView(ProfileAtt.class)
	private int goles;
	
	@JsonView(ProfileAtt.class)
	private int tarjetasAmarillas;
	
	@JsonView(ProfileAtt.class)
	private int tarjetasRojas;
	
	@JsonView(TeamAtt.class)
	private boolean capitan;
	
	
	public Player() {};
	
	
	public Player(String nombre, String apellidos, String equipo, String posicion, String fechaSancion, String estado,
			int faltas, int dorsal, int goles, int tarjetasAmarillas, int tarjetasRojas, String nacionalidad,
			boolean capitan) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.equipo = equipo;
		this.posicion = posicion;
		this.fechaSancion = fechaSancion;
		this.estado = estado;
		this.faltas = faltas;
		this.dorsal = dorsal;
		this.goles = goles;
		this.tarjetasAmarillas = tarjetasAmarillas;
		this.tarjetasRojas = tarjetasRojas;
		this.nacionalidad = nacionalidad;
		this.capitan = capitan;
		this.fotoJugador = "defaultImage.png";
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


	public String getContraseña() {
		return contraseña;
	}


	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
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


	public int getFaltas() {
		return faltas;
	}


	public void setFaltas(int faltas) {
		this.faltas = faltas;
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


	public String getDNI() {
		return DNI;
	}


	public void setDNI(String dNI) {
		DNI = dNI;
	}
	
	

	public String getFotoJugador() {
		return fotoJugador;
	}


	public void setFotoJugador(String fotoJugador) {
		this.fotoJugador = fotoJugador;
	}


	@Override
	public String toString() {
		return "Player [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", DNI=" + DNI
				+ ", nombreUsuario=" + nombreUsuario + ", contraseña=" + contraseña + ", fotoJugador=" + fotoJugador
				+ ", equipo=" + equipo + ", posicion=" + posicion + ", fechaSancion=" + fechaSancion + ", estado="
				+ estado + ", nacionalidad=" + nacionalidad + ", faltas=" + faltas + ", dorsal=" + dorsal + ", goles="
				+ goles + ", tarjetasAmarillas=" + tarjetasAmarillas + ", tarjetasRojas=" + tarjetasRojas + ", capitan="
				+ capitan + "]";
	}



}
