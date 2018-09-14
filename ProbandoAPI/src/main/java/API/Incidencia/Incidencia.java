package API.Incidencia;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
		String minuto1;
		String minuto2;
		
		if(Integer.parseInt(this.minuto ) < 10){
			minuto1 = "00:0" + this.minuto + ":00";
		} else {
			minuto1 = "00:" + this.minuto + ":00";
		}
		
		if(Integer.parseInt(o.minuto) < 10){
			minuto2 = "00:0" + o.minuto + ":00";
		} else {
			minuto2 = "00:" + o.minuto + ":00";
		}
		
		LocalTime hora1 = LocalTime.parse(minuto1);
		LocalTime hora2 = LocalTime.parse(minuto2);
		
		if(hora1.isAfter(hora2)){
			return -1;
		}
		
		return 1;
	
	}
	
	
}
