package API.Incidencia;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Incidencias")		
public class Incidencia {
	
	
	@Id
	private String id;
	private String tipo;
	private String idJugador;
	private String idPartido;
	private String minuto;
	
				
	public Incidencia() {}


	public Incidencia(String id, String tipo, String idJugador, String idPartido, String minuto) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.idJugador = idJugador;
		this.idPartido = idPartido;
		this.minuto = minuto;
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


	public String getIdJugador() {
		return idJugador;
	}


	public void setIdJugador(String idJugador) {
		this.idJugador = idJugador;
	}


	public String getIdPartido() {
		return idPartido;
	}


	public void setIdPartido(String idPartido) {
		this.idPartido = idPartido;
	}


	public String getMinuto() {
		return minuto;
	}


	public void setMinuto(String minuto) {
		this.minuto = minuto;
	}
	
	
}
