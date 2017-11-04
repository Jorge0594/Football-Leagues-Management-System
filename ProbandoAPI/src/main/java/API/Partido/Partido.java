package API.Partido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import API.Acta.Acta;
import API.Equipo.Equipo;
import API.Estadio.Estadio;
import API.Incidencia.Incidencia;

@Document(collection = "Partidos")
public class Partido implements Comparable<Partido> {
	@Id
	private String id;
	private String liga;
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private Integer golesLocal;
	private Integer golesVisitante;
	private String idArbitro;
	private String fechaPartido;
	private String horaPartido;
	private Estadio estadio;
	private String estado;
	private String jornada;
	private String equipacionLocal;
	private String equipacionVisitante;
	private List<Incidencia> incidencias;
	private Acta acta;

	public Partido() {
	}

	public Partido(String liga, Equipo equipoLocal, Equipo equipoVisitante, int golesLocal, int golesVisitante,
			String idArbitro, String fechaPartido, String horaPartido, Estadio estadio, String estado, String jornada,
			String equipacionLocal, String equipacionVisitante) throws ParseException {
		super();
		this.liga = liga;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.golesLocal = golesLocal;
		this.golesVisitante = golesVisitante;
		this.idArbitro = idArbitro;
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

	public int getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(Integer golesLocal) {
		this.golesLocal = golesLocal;
	}

	public int getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(Integer golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public String getIdArbitro() {
		return idArbitro;
	}

	public void setIdArbitro(String idArbitro) {
		this.idArbitro = idArbitro;
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

	public Acta getActa() {
		return acta;
	}

	public void setActa(Acta acta) {
		this.acta = acta;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public int compareTo(Partido partido2) {
		 SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return formateador.parse(this.getFechaPartido()).compareTo(formateador.parse(partido2.getFechaPartido()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}

}
