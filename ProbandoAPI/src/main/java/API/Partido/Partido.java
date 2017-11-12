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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acta == null) ? 0 : acta.hashCode());
		result = prime * result + ((equipacionLocal == null) ? 0 : equipacionLocal.hashCode());
		result = prime * result + ((equipacionVisitante == null) ? 0 : equipacionVisitante.hashCode());
		result = prime * result + ((equipoLocal == null) ? 0 : equipoLocal.hashCode());
		result = prime * result + ((equipoVisitante == null) ? 0 : equipoVisitante.hashCode());
		result = prime * result + ((estadio == null) ? 0 : estadio.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaPartido == null) ? 0 : fechaPartido.hashCode());
		result = prime * result + ((golesLocal == null) ? 0 : golesLocal.hashCode());
		result = prime * result + ((golesVisitante == null) ? 0 : golesVisitante.hashCode());
		result = prime * result + ((horaPartido == null) ? 0 : horaPartido.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idArbitro == null) ? 0 : idArbitro.hashCode());
		result = prime * result + ((incidencias == null) ? 0 : incidencias.hashCode());
		result = prime * result + ((jornada == null) ? 0 : jornada.hashCode());
		result = prime * result + ((liga == null) ? 0 : liga.hashCode());
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
		if (acta == null) {
			if (other.acta != null)
				return false;
		} else if (!acta.equals(other.acta))
			return false;
		if (equipacionLocal == null) {
			if (other.equipacionLocal != null)
				return false;
		} else if (!equipacionLocal.equals(other.equipacionLocal))
			return false;
		if (equipacionVisitante == null) {
			if (other.equipacionVisitante != null)
				return false;
		} else if (!equipacionVisitante.equals(other.equipacionVisitante))
			return false;
		if (equipoLocal == null) {
			if (other.equipoLocal != null)
				return false;
		} else if (!equipoLocal.equals(other.equipoLocal))
			return false;
		if (equipoVisitante == null) {
			if (other.equipoVisitante != null)
				return false;
		} else if (!equipoVisitante.equals(other.equipoVisitante))
			return false;
		if (estadio == null) {
			if (other.estadio != null)
				return false;
		} else if (!estadio.equals(other.estadio))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaPartido == null) {
			if (other.fechaPartido != null)
				return false;
		} else if (!fechaPartido.equals(other.fechaPartido))
			return false;
		if (golesLocal == null) {
			if (other.golesLocal != null)
				return false;
		} else if (!golesLocal.equals(other.golesLocal))
			return false;
		if (golesVisitante == null) {
			if (other.golesVisitante != null)
				return false;
		} else if (!golesVisitante.equals(other.golesVisitante))
			return false;
		if (horaPartido == null) {
			if (other.horaPartido != null)
				return false;
		} else if (!horaPartido.equals(other.horaPartido))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idArbitro == null) {
			if (other.idArbitro != null)
				return false;
		} else if (!idArbitro.equals(other.idArbitro))
			return false;
		if (incidencias == null) {
			if (other.incidencias != null)
				return false;
		} else if (!incidencias.equals(other.incidencias))
			return false;
		if (jornada == null) {
			if (other.jornada != null)
				return false;
		} else if (!jornada.equals(other.jornada))
			return false;
		if (liga == null) {
			if (other.liga != null)
				return false;
		} else if (!liga.equals(other.liga))
			return false;
		return true;
	}


	

}
