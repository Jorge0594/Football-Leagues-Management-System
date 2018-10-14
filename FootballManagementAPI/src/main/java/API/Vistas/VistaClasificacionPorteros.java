package API.Vistas;

import com.fasterxml.jackson.annotation.JsonView;

public class VistaClasificacionPorteros implements Comparable<VistaClasificacionPorteros>{
	
	public interface VistaPorterosAtt {}
	
	@JsonView(VistaPorterosAtt.class)
	private String id;
	@JsonView(VistaPorterosAtt.class)
	private String equipo;
	@JsonView(VistaPorterosAtt.class)
	private String nombre;
	@JsonView(VistaPorterosAtt.class)
	private String fotoJugador;
	@JsonView(VistaPorterosAtt.class)
	private int golesEncajados;
	
	public VistaClasificacionPorteros(){}

	public VistaClasificacionPorteros(String id, String equipo, String nombre, String fotoJugador, int golesEncajados) {
		super();
		this.id = id;
		this.equipo = equipo;
		this.nombre = nombre;
		this.golesEncajados = golesEncajados;
		this.fotoJugador = fotoJugador;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getGolesEncajados() {
		return golesEncajados;
	}

	public void setGolesEncajados(int golesEncajados) {
		this.golesEncajados = golesEncajados;
	}
	
	public String getFotoJugador() {
		return fotoJugador;
	}

	public void setFotoJugador(String fotoJugador) {
		this.fotoJugador = fotoJugador;
	}

	@Override
	public String toString() {
		return "VistaClasificacionPorteros [id=" + id + ", equipo=" + equipo + ", nombre=" + nombre + ", golesEncajados=" + golesEncajados + ", fotoJugador=" + fotoJugador + "]";
	}

	@Override
	public int compareTo(VistaClasificacionPorteros o) {
		if(this.getGolesEncajados() > o.getGolesEncajados()){
			return 1;
		} else if (this.getGolesEncajados() < o.getGolesEncajados()){
			return -1;
		}
		return 0;
	}

}
