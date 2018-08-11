package API.Vistas;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

@Component
public class VistaTemporada {
	
	public interface VistaTemporadaAtt{}
	
	@JsonView(VistaTemporadaAtt.class)
	private String idTemporada;
	@JsonView(VistaTemporadaAtt.class)
	private String nombre;
	@JsonView(VistaTemporadaAtt.class)
	private String liga;
	
	public VistaTemporada() {}
	
	public VistaTemporada(String id, String nombre, String liga) {
		super();
		this.idTemporada = id;
		this.nombre = nombre;
		this.liga = liga;
	}

	public String getId() {
		return idTemporada;
	}

	public void setId(String id) {
		this.idTemporada = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	@Override
	public String toString() {
		return "VistaTemporada [id=" + idTemporada + ", nombre=" + nombre + ", liga=" + liga + "]";
	}
	
	
}
