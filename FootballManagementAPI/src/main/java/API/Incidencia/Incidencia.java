package API.Incidencia;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;
import API.Vistas.VistaJugador;

@Document(collection="Incidencia")		
public class Incidencia implements Comparable <Incidencia>{
	
	public interface IncidenciaAtt{}
	
	@Id
	@JsonView(IncidenciaAtt.class)
	private String id;
	
	@JsonView(IncidenciaAtt.class)
	private String tipo;
	
	@JsonView(IncidenciaAtt.class)
	private String idJugador;
	
	@JsonView(IncidenciaAtt.class)
	private VistaJugador jugador;
	
	@JsonView(IncidenciaAtt.class)
	private String idPartido;
	
	@JsonView(IncidenciaAtt.class)
	private String minuto;
	
	@JsonView(IncidenciaAtt.class)
	private String observaciones;
				
	public Incidencia() {}


	public Incidencia(String id, String tipo, String idJugador, VistaJugador jugador, String idPartido, String minuto, String observaciones) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.idJugador = idJugador;
		this.jugador = jugador;
		this.idPartido = idPartido;
		this.minuto = minuto;
		this.observaciones = observaciones;
	}

	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
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


	public String getIdJugador() {
		return idJugador;
	}


	public void setIdJugador(String idJugador) {
		this.idJugador = idJugador;
	}

	public VistaJugador getJugador() {
		return jugador;
	}


	public void setJugador(VistaJugador jugador) {
		this.jugador = jugador;
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


	@Override
	public int compareTo(Incidencia o) {
		int minuto1 = Integer.parseInt(this.getMinuto());
		int minuto2 = Integer.parseInt(o.getMinuto());
		
		if(minuto1 > minuto2){
			return -1;
		} else if (minuto1 < minuto2){
			return 1;
		}
		
		return 0;
	
	}
	
	
}
