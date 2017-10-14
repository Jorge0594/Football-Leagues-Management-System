package API.Player;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Jugadores")
public class Player {
	@Id
	private String id;
	private String nombre;
	private String apellidos;
	private double dorsal;
	private double goles;
	private double tarjetasAmarillas;
	private double tarjetasRojas;
	private boolean nacionalidadEspañola;
	private Player mejorAmigo;
	
	public Player() {};
	
	
	public Player(String nombre, String apellidos, double dorsal, double goles, double tarjetasAmarillas,
			double tarjetasRojas, boolean nacionalidadEspañola) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dorsal = dorsal;
		this.goles = goles;
		this.tarjetasAmarillas = tarjetasAmarillas;
		this.tarjetasRojas = tarjetasRojas;
		this.nacionalidadEspañola = nacionalidadEspañola;
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
	public double getDorsal() {
		return dorsal;
	}
	public void setDorsal(double dorsal) {
		this.dorsal = dorsal;
	}
	public double getGoles() {
		return goles;
	}
	public void setGoles(double goles) {
		this.goles = goles;
	}
	public double getTarjetasAmarillas() {
		return tarjetasAmarillas;
	}
	public void setTarjetasAmarillas(double tarjetasAmarillas) {
		this.tarjetasAmarillas = tarjetasAmarillas;
	}
	public double getTarjetasRojas() {
		return tarjetasRojas;
	}
	public void setTarjetasRojas(double tarjetasRojas) {
		this.tarjetasRojas = tarjetasRojas;
	}
	public boolean isNacionalidadEspañola() {
		return nacionalidadEspañola;
	}
	public void setNacionalidadEspañola(boolean nacionalidadEspañola) {
		this.nacionalidadEspañola = nacionalidadEspañola;
	}

	public Player getMejorAmigo() {
		return mejorAmigo;
	}


	public void setMejorAmigo(Player mejorAmigo) {
		this.mejorAmigo = mejorAmigo;
	}


	@Override
	public String toString() {
		return "Player [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dorsal=" + dorsal
				+ ", goles=" + goles + ", tarjetasAmarillas=" + tarjetasAmarillas + ", tarjetasRojas=" + tarjetasRojas
				+ ", nacionalidadEspañola=" + nacionalidadEspañola + "]";
	}
	
	
	
	

}
