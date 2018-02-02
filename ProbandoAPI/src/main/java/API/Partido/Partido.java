package API.Partido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import API.Acta.Acta;
import API.Equipo.Equipo;
import API.Estadio.Estadio;
import API.Incidencia.Incidencia;
import API.Jugador.Jugador;

@Document(collection = "Partidos")
public class Partido implements Comparable<Partido> {

	public interface InfoAtt {
	}

	public interface RestAtt {
	}

	@JsonView(InfoAtt.class)
	@Id
	private String id;
	
	@JsonView(InfoAtt.class)
	private String liga;
	
	@DBRef
	@JsonView(InfoAtt.class)
	private Equipo equipoLocal;
	
	@DBRef
	@JsonView(InfoAtt.class)
	private Equipo equipoVisitante;
	
	@JsonView(InfoAtt.class)
	private Integer golesLocal;
	
	@JsonView(InfoAtt.class)
	private Integer golesVisitante;
	
	@JsonView(InfoAtt.class)
	private String idArbitro;
	
	@JsonView(InfoAtt.class)
	private String fechaPartido;
	
	@JsonView(InfoAtt.class)
	private String horaPartido;
	
	@DBRef
	@JsonView(InfoAtt.class)
	private Estadio estadio;
	
	@JsonView(InfoAtt.class)
	private String estado;
	
	@JsonView(InfoAtt.class)
	private int jornada;
	
	@JsonView(InfoAtt.class)
	private String equipacionLocal;
	
	@JsonView(InfoAtt.class)
	private String equipacionVisitante;
	
	@DBRef
	@JsonView(RestAtt.class)
	private List<Jugador> convocadosLocal;
	
	@DBRef
	@JsonView(RestAtt.class)
	private List<Jugador> convocadosVisitante;
	
	@DBRef
	@JsonView(RestAtt.class)
	private List<Incidencia> incidencias;
	
	@JsonView(RestAtt.class)
	private String idActa;

	public Partido() {
	}

	public Partido(String liga, Equipo equipoLocal, Equipo equipoVisitante, int golesLocal, int golesVisitante,
			String idArbitro, String fechaPartido, String horaPartido, Estadio estadio, String estado, int jornada,
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

	public void setLiga(String ligaId) {
		this.liga = ligaId;
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

	public int getJornada() {
		return jornada;
	}

	public void setJornada(int jornada) {
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

	
	public List<Jugador> getConvocadosLocal() {
		return convocadosLocal;
	}

	public void setConvocadosLocal(List<Jugador> convocadosLocal) {
		this.convocadosLocal = convocadosLocal;
	}

	public List<Jugador> getConvocadosVisitante() {
		return convocadosVisitante;
	}

	public void setConvocadosVisitante(List<Jugador> convocadosVisitante) {
		this.convocadosVisitante = convocadosVisitante;
	}

	public Estadio getEstadio() {
		return estadio;
	}

	public void setEstadio(Estadio estadio) {
		this.estadio = estadio;
	}

	public String getIdActa() {
		return idActa;
	}

	public void setIdActa(String acta) {
		this.idActa = acta;
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
			e.printStackTrace();
		}
		return -2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Partido other = (Partido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}