package API.Grupo;

import java.util.ArrayList;

import java.util.List;

import API.Equipo.*;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection = "Grupo")
public class Grupo {

	public interface GrupoAtt {}

	@Id
	@JsonView(GrupoAtt.class)
	private String id;
	@JsonView(GrupoAtt.class)
	private String temporada;
	@JsonView(GrupoAtt.class)
	private String nombre;
	@JsonView(GrupoAtt.class)
	private int jornadaActual;
	@DBRef
	@JsonView(GrupoAtt.class)
	private List<Equipo> clasificacion = new ArrayList<>();

	public Grupo() {}
	
	public Grupo(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Grupo(String id, String temporada, String nombre, int jornadaActual, List<Equipo> clasificacion) {
		this(nombre);
		
		this.id = id;
		this.temporada = temporada;
		this.jornadaActual = jornadaActual;
		this.clasificacion = clasificacion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getJornadaActual() {
		return jornadaActual;
	}

	public void setJornadaActual(int jornadaActual) {
		this.jornadaActual = jornadaActual;
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

	public void aumentaJornadaActual() {
		this.jornadaActual++;
	}
	
	
	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	@Override
	public String toString() {
		return "Liga [id=" + id + ", nombre=" + nombre + ", clasificacion=" + clasificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clasificacion == null) ? 0 : clasificacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + jornadaActual;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((temporada == null) ? 0 : temporada.hashCode());
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
		Grupo other = (Grupo) obj;
		if (clasificacion == null) {
			if (other.clasificacion != null)
				return false;
		} else if (!clasificacion.equals(other.clasificacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jornadaActual != other.jornadaActual)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (temporada == null) {
			if (other.temporada != null)
				return false;
		} else if (!temporada.equals(other.temporada))
			return false;
		return true;
	}

}
