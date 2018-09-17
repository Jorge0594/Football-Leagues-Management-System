package API.Vistas;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

@Component
public class VistaJugador {
	
	public interface VistaJugadorAtt {}
	
	@JsonView(VistaJugadorAtt.class)
	private String nombre;
	@JsonView(VistaJugadorAtt.class)
	private int dorsal;
	
	public VistaJugador(){}

	public VistaJugador(String nombre, int dorsal) {
		super();
		this.nombre = nombre;
		this.dorsal = dorsal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDorsal() {
		return dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	@Override
	public String toString() {
		return "VistaJugador [nombre=" + nombre + ", dorsal=" + dorsal + "]";
	}
	
}
