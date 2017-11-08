package API.Acta;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import API.Arbitro.Arbitro;
import API.Equipo.Equipo;
import API.Incidencia.Incidencia;
import API.Jugador.Jugador;

@Document(collection = "Acta")
public class Acta {
	@Id
	private String id;
	private String idPartido;
	private String fecha;
	private String hora;
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private Arbitro arbitro;
	private List<Jugador> convocadosLocal;
	private List<Jugador> convocadosVisitante;
	private int golesLocal;
	private int golesVisitante;
	private List<Incidencia> incidencias;
	private String observaciones;

	public Acta() {
	}

	public Acta(String id, String idPartido, String fecha, String hora, Equipo equipoLocal, Equipo equipoVisitante,
			Arbitro arbitro, List<Jugador> convocadosLocal, List<Jugador> convocadosVisitante, int golesLocal,
			int golesVisitante, List<Incidencia> incidencias, String observaciones) {
		super();
		this.id = id;
		this.idPartido = idPartido;
		this.fecha = fecha;
		this.hora = hora;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.arbitro = arbitro;
		this.convocadosLocal = convocadosLocal;
		this.convocadosVisitante = convocadosVisitante;
		this.golesLocal = golesLocal;
		this.golesVisitante = golesVisitante;
		this.incidencias = incidencias;
		this.observaciones = observaciones;
	}

	public void setConvocadosLocal(List<Jugador> convocadosLocal) {
		this.convocadosLocal = convocadosLocal;
	}

	public void setConvocadosVisitante(List<Jugador> convocadosVisitante) {
		this.convocadosVisitante = convocadosVisitante;
	}

	public List<Jugador> getConvocadosLocal() {
		return convocadosLocal;
	}

	public List<Jugador> getConvocadosVisitante() {
		return convocadosVisitante;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(String idPartido) {
		this.idPartido = idPartido;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(Equipo equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public Arbitro getArbitro() {
		return arbitro;
	}

	public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}

	public int getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(int golesLocal) {
		this.golesLocal = golesLocal;
	}

	public int getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(int golesVisitante) {
		this.golesVisitante = golesVisitante;
	}
	

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
