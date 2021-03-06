package API.Acta;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro;
import API.Equipo.Equipo;
import API.Incidencia.Incidencia;
import API.Jugador.Jugador;
import API.Vistas.VistaGrupo;

@Document(collection = "Acta")
public class Acta {

	public interface ActaAtt {
	}

	@Id
	@JsonView(ActaAtt.class)
	private String id;

	@JsonView(ActaAtt.class)
	private String idPartido;
	
	@JsonView(ActaAtt.class)
	private int jornada;

	@JsonView(ActaAtt.class)
	private boolean aceptada;

	@JsonView(ActaAtt.class)
	private String fecha;

	@JsonView(ActaAtt.class)
	private String hora;
	
	@JsonView(ActaAtt.class)
	private VistaGrupo grupo;

	@JsonView(ActaAtt.class)
	private String idEquipoLocal;

	@JsonView(ActaAtt.class)
	private String nombreEquipoLocal;

	@JsonView(ActaAtt.class)
	private String escudoEquipoLocal;

	@JsonView(ActaAtt.class)
	private String idEquipoVisitante;

	@JsonView(ActaAtt.class)
	private String nombreEquipoVisitante;

	@JsonView(ActaAtt.class)
	private String escudoEquipoVisitante;

	@JsonView(ActaAtt.class)
	private String idArbitro;

	@JsonView(ActaAtt.class)
	private String idCapitanLocal;
	
	@JsonView(ActaAtt.class)
	private String idCapitanVisitante;
	
	@JsonView(ActaAtt.class)
	private List<String> idsPorterosLocal;
	
	@JsonView(ActaAtt.class)
	private List<String> idsPorterosVisitante;

	@JsonView(ActaAtt.class)
	private String nombreArbitro;

	@JsonView(ActaAtt.class)
	private List<Jugador> convocadosLocal;

	@JsonView(ActaAtt.class)
	private List<Jugador> convocadosVisitante;

	@JsonView(ActaAtt.class)
	private int golesLocal;

	@JsonView(ActaAtt.class)
	private int golesVisitante;

	@JsonView(ActaAtt.class)
	private List<Incidencia> incidencias;

	@JsonView(ActaAtt.class)
	private String observaciones;

	public Acta() {
	}



	public Acta(String id, String idPartido, boolean aceptada, String fecha, String hora, VistaGrupo grupo, String idEquipoLocal, String nombreEquipoLocal, String escudoEquipoLocal, String idEquipoVisitante, String nombreEquipoVisitante, String escudoEquipoVisitante, String idArbitro,
			String idCapitanLocal, String idCapitanVisitante, List<String> idsPorterosLocal, List<String> idsPorterosVisitante, String nombreArbitro, List<Jugador> convocadosLocal, List<Jugador> convocadosVisitante, int golesLocal, int golesVisitante, List<Incidencia> incidencias,
			String observaciones, int jornada) {
		super();
		this.id = id;
		this.idPartido = idPartido;
		this.aceptada = aceptada;
		this.fecha = fecha;
		this.hora = hora;
		this.grupo = grupo;
		this.idEquipoLocal = idEquipoLocal;
		this.nombreEquipoLocal = nombreEquipoLocal;
		this.escudoEquipoLocal = escudoEquipoLocal;
		this.idEquipoVisitante = idEquipoVisitante;
		this.nombreEquipoVisitante = nombreEquipoVisitante;
		this.escudoEquipoVisitante = escudoEquipoVisitante;
		this.idArbitro = idArbitro;
		this.idCapitanLocal = idCapitanLocal;
		this.idCapitanVisitante = idCapitanVisitante;
		this.idsPorterosLocal = idsPorterosLocal;
		this.idsPorterosVisitante = idsPorterosVisitante;
		this.nombreArbitro = nombreArbitro;
		this.convocadosLocal = convocadosLocal;
		this.convocadosVisitante = convocadosVisitante;
		this.golesLocal = golesLocal;
		this.golesVisitante = golesVisitante;
		this.incidencias = incidencias;
		this.observaciones = observaciones;
		this.jornada = jornada;
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
	
	public int getJornada() {
		return jornada;
	}

	public void setJornada(int jornada) {
		this.jornada = jornada;
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

	public String getIdEquipoLocal() {
		return idEquipoLocal;
	}

	public void setIdEquipoLocal(String idEquipoLocal) {
		this.idEquipoLocal = idEquipoLocal;
	}

	public String getNombreEquipoLocal() {
		return nombreEquipoLocal;
	}

	public void setNombreEquipoLocal(String nombreEquipoLocal) {
		this.nombreEquipoLocal = nombreEquipoLocal;
	}

	public String getEscudoEquipoLocal() {
		return escudoEquipoLocal;
	}

	public void setEscudoEquipoLocal(String escudoEquipoLocal) {
		this.escudoEquipoLocal = escudoEquipoLocal;
	}

	public String getIdEquipoVisitante() {
		return idEquipoVisitante;
	}

	public void setIdEquipoVisitante(String idEquipoVisitante) {
		this.idEquipoVisitante = idEquipoVisitante;
	}

	public String getNombreEquipoVisitante() {
		return nombreEquipoVisitante;
	}

	public void setNombreEquipoVisitante(String nombreEquipoVisitante) {
		this.nombreEquipoVisitante = nombreEquipoVisitante;
	}

	public String getEscudoEquipoVisitante() {
		return escudoEquipoVisitante;
	}

	public void setEscudoEquipoVisitante(String escudoEquipoVisitante) {
		this.escudoEquipoVisitante = escudoEquipoVisitante;
	}

	public String getIdArbitro() {
		return idArbitro;
	}

	public String getNombreArbitro() {
		return nombreArbitro;
	}

	public void setIdArbitro(String idArbitro) {
		this.idArbitro = idArbitro;
	}
	

	public String getIdCapitanLocal() {
		return idCapitanLocal;
	}

	public void setIdCapitanLocal(String idCapitanLocal) {
		this.idCapitanLocal = idCapitanLocal;
	}

	public String getIdCapitanVisitante() {
		return idCapitanVisitante;
	}

	public void setIdCapitanVisitante(String idCapitanVisitante) {
		this.idCapitanVisitante = idCapitanVisitante;
	}

	public List<String> getIdsPorterosLocal() {
		return idsPorterosLocal;
	}

	public void setIdsPorterosLocal(List<String> idsPorterosLocal) {
		this.idsPorterosLocal = idsPorterosLocal;
	}

	public List<String> getIdsPorterosVisitante() {
		return idsPorterosVisitante;
	}

	public void setIdsPorterosVisitante(List<String> idsPorterosVisitante) {
		this.idsPorterosVisitante = idsPorterosVisitante;
	}

	public void setNombreArbitro(String nombreArbitro) {
		this.nombreArbitro = nombreArbitro;
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

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

	public VistaGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(VistaGrupo grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return "Acta [id=" + id + ", idPartido=" + idPartido + ", aceptada=" + aceptada + ", fecha=" + fecha + ", hora=" + hora + ", grupo=" + grupo + ", idEquipoLocal=" + idEquipoLocal + ", nombreEquipoLocal=" + nombreEquipoLocal + ", escudoEquipoLocal=" + escudoEquipoLocal + ", idEquipoVisitante="
				+ idEquipoVisitante + ", nombreEquipoVisitante=" + nombreEquipoVisitante + ", escudoEquipoVisitante=" + escudoEquipoVisitante + ", idArbitro=" + idArbitro + ", idCapitanLocal=" + idCapitanLocal + ", idCapitanVisitante=" + idCapitanVisitante + ", idsPorterosLocal=" + idsPorterosLocal
				+ ", idsPorterosVisitante=" + idsPorterosVisitante + ", nombreArbitro=" + nombreArbitro + ", convocadosLocal=" + convocadosLocal + ", convocadosVisitante=" + convocadosVisitante + ", golesLocal=" + golesLocal + ", golesVisitante=" + golesVisitante + ", incidencias=" + incidencias
				+ ", observaciones=" + observaciones + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idArbitro == null) ? 0 : idArbitro.hashCode());
		result = prime * result + ((nombreArbitro == null) ? 0 : nombreArbitro.hashCode());
		result = prime * result + ((convocadosLocal == null) ? 0 : convocadosLocal.hashCode());
		result = prime * result + ((convocadosVisitante == null) ? 0 : convocadosVisitante.hashCode());
		result = prime * result + ((idEquipoLocal == null) ? 0 : idEquipoLocal.hashCode());
		result = prime * result + ((idEquipoVisitante == null) ? 0 : idEquipoVisitante.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + golesLocal;
		result = prime * result + golesVisitante;
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idPartido == null) ? 0 : idPartido.hashCode());
		result = prime * result + ((incidencias == null) ? 0 : incidencias.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
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
		Acta other = (Acta) obj;
		if (idArbitro == null) {
			if (other.idArbitro != null)
				return false;
		} else if (!idArbitro.equals(other.idArbitro))
			return false;
		if (convocadosLocal == null) {
			if (other.convocadosLocal != null)
				return false;
		} else if (!convocadosLocal.equals(other.convocadosLocal))
			return false;
		if (convocadosVisitante == null) {
			if (other.convocadosVisitante != null)
				return false;
		} else if (!convocadosVisitante.equals(other.convocadosVisitante))
			return false;
		if (idEquipoLocal == null) {
			if (other.idEquipoLocal != null)
				return false;
		} else if (!idEquipoLocal.equals(other.idEquipoLocal))
			return false;
		if (idEquipoVisitante == null) {
			if (other.idEquipoVisitante != null)
				return false;
		} else if (!idEquipoVisitante.equals(other.idEquipoVisitante))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (golesLocal != other.golesLocal)
			return false;
		if (golesVisitante != other.golesVisitante)
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idPartido == null) {
			if (other.idPartido != null)
				return false;
		} else if (!idPartido.equals(other.idPartido))
			return false;
		if (incidencias == null) {
			if (other.incidencias != null)
				return false;
		} else if (!incidencias.equals(other.incidencias))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		return true;
	}

}
