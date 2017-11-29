package API.Peticion;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Peticion")
public class Peticion {

	@Id
	private String id;
	private String tipo;
	private String estado;
	private String observaciones;
	
	public Peticion() {}
	
	public Peticion(String id, String tipo, String estado, String observaciones) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.estado = estado;
		this.observaciones = observaciones;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
	
	
	
}
