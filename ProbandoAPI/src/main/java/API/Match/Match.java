package API.Match;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import API.Estadio.Estadio;

@Document(collection = "Partidos")
public class Match {
	@Id
	private String id;
	private String liga;
	private String equipoLocal;
	private String equipoVisitante;
	private int golesLocal;
	private int golesVisitante;
	private String arbitro;
	private String fechaPartido;
	private String horaPartido;
	private Estadio estadio;
	private String estado;
	private String jornada;
	private String equipacionLocal;
	private String equipacionVisitante;
	// private Incidencia[] incidencias;
	// private Acta acta;

	public Match() {
	};


	public Match(String id, String liga, String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante,
			String arbitro, String fechaPartido, String horaPartido, Estadio estadio, String estado, String jornada,
			String equipacionLocal, String equipacionVisitante) {
		super();
		this.id = id;
		this.liga = liga;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.golesLocal = golesLocal;
		this.golesVisitante = golesVisitante;
		this.arbitro = arbitro;
		this.fechaPartido = fechaPartido;
		this.horaPartido = horaPartido;
		this.estadio = estadio;
		this.estado = estado;
		this.jornada = jornada;
		this.equipacionLocal = equipacionLocal;
		this.equipacionVisitante = equipacionVisitante;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
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

	public String getArbitro() {
		return arbitro;
	}

	public void setArbitro(String arbitro) {
		this.arbitro = arbitro;
	}

	public String getFechaPartido() {
		return fechaPartido;
	}

	public void setFechaPartido(String fechaPartido) {
		this.fechaPartido = fechaPartido;
	}

	public String getHoraPartido() {
		return horaPartido;
	}

	public void setHoraPartido(String horaPartido) {
		this.horaPartido = horaPartido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public String getEquipacionLocal() {
		return equipacionLocal;
	}

	public void setEquipacionLocal(String equipacionLocal) {
		this.equipacionLocal = equipacionLocal;
	}

	public String getEquipacionVisitante() {
		return equipacionVisitante;
	}

	public void setEquipacionVisitante(String equipacionVisitante) {
		this.equipacionVisitante = equipacionVisitante;
	}


	public Estadio getEstadio() {
		return estadio;
	}


	public void setEstadio(Estadio estadio) {
		this.estadio = estadio;
	}


}
