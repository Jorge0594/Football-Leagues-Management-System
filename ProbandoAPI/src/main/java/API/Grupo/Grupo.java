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

	public interface LigaAtt {}

	@Id
	@JsonView(LigaAtt.class)
	private String id;
	@JsonView(LigaAtt.class)
	private String nombre;
	@DBRef
	@JsonView(LigaAtt.class)
	private List<Equipo> clasificacion = new ArrayList<>();


	public Grupo(String id, String nombre, List<Equipo> clasificacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.clasificacion = clasificacion;
	}

	public Grupo() {
	}
	
	public Grupo(String nombre) {
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
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
