package API.Liga;

import java.util.ArrayList;
import java.util.List;

import API.Jugador.*;
import API.Partido.Partido;
import API.Sancion.Sancion;
import API.Equipo.*;
import API.Arbitro.*;

import org.springframework.data.annotation.Id;
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
	private List<Equipo> clasificacion = new ArrayList<>();
	@JsonView(LigaAtt.class)
	private List<Jugador> goleadores = new ArrayList<>();
	@JsonView(LigaAtt.class)
	private List<Arbitro> arbitros = new ArrayList<>();
	@JsonView(LigaAtt.class)
	private List<Sancion> sancion = new ArrayList<>();
	@JsonView(LigaAtt.class)
	private List<Partido> partidos = new ArrayList<>();
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arbitros == null) ? 0 : arbitros.hashCode());
		result = prime * result + ((clasificacion == null) ? 0 : clasificacion.hashCode());
		result = prime * result + ((goleadores == null) ? 0 : goleadores.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((partidos == null) ? 0 : partidos.hashCode());
		result = prime * result + ((sancion == null) ? 0 : sancion.hashCode());
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
		Liga other = (Liga) obj;
		if (arbitros == null) {
			if (other.arbitros != null)
				return false;
		} else if (!arbitros.equals(other.arbitros))
			return false;
		if (clasificacion == null) {
			if (other.clasificacion != null)
				return false;
		} else if (!clasificacion.equals(other.clasificacion))
			return false;
		if (goleadores == null) {
			if (other.goleadores != null)
				return false;
		} else if (!goleadores.equals(other.goleadores))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (partidos == null) {
			if (other.partidos != null)
				return false;
		} else if (!partidos.equals(other.partidos))
			return false;
		if (sancion == null) {
			if (other.sancion != null)
				return false;
		} else if (!sancion.equals(other.sancion))
			return false;
		return true;
	}

	
	

}
