package API.Liga;

import java.util.ArrayList;
import java.util.List;

import API.Jugador.*;
import API.Equipo.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection = "Liga")
public class Liga {

	public interface LigaAtt {
	}

	@Id
	@JsonView(LigaAtt.class)
	private String id;
	@JsonView(LigaAtt.class)
	private String nombre;
	/*
	 * @JsonView(LigaAtt.class)
	 * 
	 * @DBRef private List<Jornadas>jornadas = new ArrayList<>();
	 */
	@JsonView(LigaAtt.class)
	@DBRef
	private List<Equipo> clasificacion = new ArrayList<>();
	@JsonView(LigaAtt.class)
	@DBRef
	private List<Jugador> goleadores = new ArrayList<>();

	public Liga() {
	}

	public Liga(String nombre) {
		super();
		this.nombre = nombre;
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

	public List<Equipo> getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(List<Equipo> clasificacion) {
		this.clasificacion = clasificacion;
	}

	public List<Jugador> getGoleadores() {
		return goleadores;
	}

	public void setGoleadores(List<Jugador> goleadores) {
		this.goleadores = goleadores;
	}

	@Override
	public String toString() {
		return "Liga [id=" + id + ", nombre=" + nombre + ", clasificacion=" + clasificacion + ", goleadores="
				+ goleadores + "]";
	}

	
	

}
