package API.Partido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import API.Estadio.Estadio;
import API.Incidencia.Incidencia;
import API.Vistas.VistaGrupo;
import API.Vistas.VistaTemporada;

@Document(collection = "Partido")
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
	
	@JsonView(InfoAtt.class)
	private VistaGrupo grupo;
	
	@JsonView(InfoAtt.class)
	private VistaTemporada temporada;
	
	@JsonView(InfoAtt.class)
	private String equipoLocalId;
	
	@JsonView(InfoAtt.class)
	private String equipoLocalNombre;
	
	@JsonView(InfoAtt.class)
	private String equipoVisitanteId;
	
	@JsonView(InfoAtt.class)
	private String equipoVisitanteNombre;
	
	@JsonView(InfoAtt.class)
	private String equipoLocalEscudo;
	
	@JsonView(InfoAtt.class)
	private String equipoVisitanteEscudo;
	
	@JsonView(InfoAtt.class)
	private int golesLocal;
	
	@JsonView(InfoAtt.class)
	private int golesVisitante;
	
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
	private List<Incidencia> incidencias;
	
	@JsonView(RestAtt.class)
	private String idActa;

	public Partido() {
	}
	
	public Partido(String id, String liga, String equipoLocalId, String equipoLocalNombre, String equipoVisitanteId,
			String equipoVisitanteNombre,String equipoLocalEscudo, String equipoVisitanteEscudo, int golesLocal, int golesVisitante, String idArbitro,
			String fechaPartido, String horaPartido, Estadio estadio, String estado, int jornada,
			String equipacionLocal, String equipacionVisitante, List<Incidencia> incidencias, String idActa) {
		this.id = id;
		this.liga = liga;
		this.equipoLocalId = equipoLocalId;
		this.equipoLocalNombre = equipoLocalNombre;
		this.equipoVisitanteId = equipoVisitanteId;
		this.equipoVisitanteNombre = equipoVisitanteNombre;
		this.equipoLocalEscudo = equipoLocalEscudo;
		this.equipoVisitanteEscudo = equipoVisitanteEscudo;
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
		this.incidencias = incidencias;
		this.idActa = idActa;
	}

	public Partido(String liga, VistaGrupo grupo, VistaTemporada temporada, String equipoLocalId, String equipoLocalNombre, String equipoVisitanteId, String equipoVisitanteNombre, String equipoLocalEscudo, String equipoVisitanteEscudo, String fechaPartido, int jornada) {
		super();
		this.liga = liga;
		this.grupo = grupo;
		this.temporada = temporada;
		this.equipoLocalId = equipoLocalId;
		this.equipoLocalNombre = equipoLocalNombre;
		this.equipoVisitanteId = equipoVisitanteId;
		this.equipoVisitanteNombre = equipoVisitanteNombre;
		this.equipoLocalEscudo = equipoLocalEscudo;
		this.equipoVisitanteEscudo = equipoVisitanteEscudo;
		this.fechaPartido = fechaPartido;
		this.jornada = jornada;
		this.estado = "Pendiente";
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
	 
	
	public String getEquipoLocalEscudo() {
		return equipoLocalEscudo;
	}

	public void setEquipoLocalEscudo(String equipoLocalEscudo) {
		this.equipoLocalEscudo = equipoLocalEscudo;
	}

	public String getEquipoVisitanteEscudo() {
		return equipoVisitanteEscudo;
	}

	public void setEquipoVisitanteEscudo(String equipoVisitanteEscudo) {
		this.equipoVisitanteEscudo = equipoVisitanteEscudo;
	}

	public void setGolesLocal(int golesLocal) {
		this.golesLocal = golesLocal;
	}

	public void setGolesVisitante(int golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public String getEquipoLocalId() {
		return equipoLocalId;
	}

	public void setEquipoLocalId(String equipoLocalId) {
		this.equipoLocalId = equipoLocalId;
	}

	public String getEquipoLocalNombre() {
		return equipoLocalNombre;
	}

	public void setEquipoLocalNombre(String equipoLocalNombre) {
		this.equipoLocalNombre = equipoLocalNombre;
	}

	public String getEquipoVisitanteId() {
		return equipoVisitanteId;
	}

	public void setEquipoVisitanteId(String equipoVisitanteId) {
		this.equipoVisitanteId = equipoVisitanteId;
	}

	public String getEquipoVisitanteNombre() {
		return equipoVisitanteNombre;
	}

	public void setEquipoVisitanteNombre(String equipoVisitanteNombre) {
		this.equipoVisitanteNombre = equipoVisitanteNombre;
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
	public String toString() {
		return "Partido [id=" + id + ", liga=" + liga + ", equipoLocalId=" + equipoLocalId + ", equipoLocalNombre="
				+ equipoLocalNombre + ", equipoVisitanteId=" + equipoVisitanteId + ", equipoVisitanteNombre="
				+ equipoVisitanteNombre + ", golesLocal=" + golesLocal + ", golesVisitante=" + golesVisitante
				+ ", idArbitro=" + idArbitro + ", fechaPartido=" + fechaPartido + ", horaPartido=" + horaPartido
				+ ", estadio=" + estadio + ", estado=" + estado + ", jornada=" + jornada + ", equipacionLocal="
				+ equipacionLocal + ", equipacionVisitante=" + equipacionVisitante + ", incidencias=" + incidencias
				+ ", idActa=" + idActa + "]";
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