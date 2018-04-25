package API.Liga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import API.Jugador.*;
import API.Equipo.*;
import API.Arbitro.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection = "Liga")
public class Liga {

	public interface LigaAtt {}

	@Id
	@JsonView(LigaAtt.class)
	private String id;
	@JsonView(LigaAtt.class)
	private String nombre;
	@DBRef
	@JsonView(LigaAtt.class)
	private List<Equipo> clasificacion = new ArrayList<>();
	@JsonView(LigaAtt.class)
	@DBRef
	private List<Arbitro> arbitros = new ArrayList<>();
	@JsonView(LigaAtt.class)
	@DBRef
	private List<Jugador> goleadores = new ArrayList<>(5);

	
	
	
	public Liga(String id, String nombre, List<Equipo> clasificacion, List<Arbitro> arbitros) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.clasificacion = clasificacion;
		this.arbitros = arbitros;
	}

	public Liga() {
	}
	
	public Liga(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public void crearGoleadores(List<Jugador>jugadoresLiga){
		Collections.sort(jugadoresLiga);
		this.goleadores.clear();
		this.goleadores.addAll(jugadoresLiga.subList(0, 5));
	}
	
	public void reordenarGoleadores(Jugador jugador){
		if(jugador.compareTo(this.goleadores.get(4)) == -1){
			this.goleadores.remove(4);
			this.goleadores.add(jugador);
			Collections.sort(this.goleadores);
		}
	}
	
	public void reordenarGoleadoresLista(List<Jugador>jugadores){
		for(Jugador jugador: jugadores){
			reordenarGoleadores(jugador);
		}
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

	
	public List<Arbitro> getArbitros() {
		return arbitros;
	}

	public void setArbitros(List<Arbitro> arbitros) {
		this.arbitros = arbitros;
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
		return true;
	}

}
