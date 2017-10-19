package API.Acta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Acta")
public class Acta {
	@Id
	private String id;
	private String idPartido;
	private String fecha;
	private String hora;
	private String equipoLocal;
	private String equipoVisitante;
	private String arbitro;
	private String[] convocadosLocal;
	private String[] convocadosVisitante;
	private int golesLocal;
	private int golesVisitante;
	// private Incidencia[] incidencias;
	private String observaciones;

	public Acta() {}

	public Acta(String id, String idPartido, String fecha, String hora, String equipoLocal, String equipoVisitante,
			String arbitro, String[] convocadosLocal, String[] convocadosVisitante, int golesLocal, int golesVisitante,
			String observaciones) {
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
		this.observaciones = observaciones;
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

	public String getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(String equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public String getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(String equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public String getArbitro() {
		return arbitro;
	}

	public void setArbitro(String arbitro) {
		this.arbitro = arbitro;
	}

	public String[] getConvocadosLocal() {
		return convocadosLocal;
	}

	public void setConvocadosLocal(String[] convocadosLocal) {
		this.convocadosLocal = convocadosLocal;
	}

	public String[] getConvocadosVisitante() {
		return convocadosVisitante;
	}

	public void setConvocadosVisitante(String[] convocadosVisitante) {
		this.convocadosVisitante = convocadosVisitante;
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
