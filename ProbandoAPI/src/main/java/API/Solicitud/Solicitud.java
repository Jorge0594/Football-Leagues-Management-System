package API.Solicitud;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Solicitud")
public class Solicitud {
	
	private String id;
	private String nombreSolicitante;
	private String apellidosSolicitante;
	private String email;
	private String nombreEquipo;
	private int numeroJugadores;
	private String campus;
	private String masInfo;
	private String ip;

	public Solicitud(String id, String nombreSolicitante, String apellidosSolicitante, String email,
			String nombreEquipo, int numeroJugadores, String campus, String masInfo, String ip) {
		super();
		this.id = id;
		this.nombreSolicitante = nombreSolicitante;
		this.apellidosSolicitante = apellidosSolicitante;
		this.email = email;
		this.nombreEquipo = nombreEquipo;
		this.numeroJugadores = numeroJugadores;
		this.campus = campus;
		this.masInfo = masInfo;
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getApellidosSolicitante() {
		return apellidosSolicitante;
	}

	public void setApellidosSolicitante(String apellidosSolicitante) {
		this.apellidosSolicitante = apellidosSolicitante;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public int getNumeroJugadores() {
		return numeroJugadores;
	}

	public void setNumeroJugadores(int numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getMasInfo() {
		return masInfo;
	}

	public void setMasInfo(String masInfo) {
		this.masInfo = masInfo;
	}

	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", nombreSolicitante=" + nombreSolicitante + ", apellidosSolicitante="
				+ apellidosSolicitante + ", email=" + email + ", nombreEquipo=" + nombreEquipo + ", numeroJugadores="
				+ numeroJugadores + ", campus=" + campus + ", masInfo=" + masInfo + ", ip=" + ip + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidosSolicitante == null) ? 0 : apellidosSolicitante.hashCode());
		result = prime * result + ((campus == null) ? 0 : campus.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((masInfo == null) ? 0 : masInfo.hashCode());
		result = prime * result + ((nombreEquipo == null) ? 0 : nombreEquipo.hashCode());
		result = prime * result + ((nombreSolicitante == null) ? 0 : nombreSolicitante.hashCode());
		result = prime * result + numeroJugadores;
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
		Solicitud other = (Solicitud) obj;
		if (apellidosSolicitante == null) {
			if (other.apellidosSolicitante != null)
				return false;
		} else if (!apellidosSolicitante.equals(other.apellidosSolicitante))
			return false;
		if (campus == null) {
			if (other.campus != null)
				return false;
		} else if (!campus.equals(other.campus))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (masInfo == null) {
			if (other.masInfo != null)
				return false;
		} else if (!masInfo.equals(other.masInfo))
			return false;
		if (nombreEquipo == null) {
			if (other.nombreEquipo != null)
				return false;
		} else if (!nombreEquipo.equals(other.nombreEquipo))
			return false;
		if (nombreSolicitante == null) {
			if (other.nombreSolicitante != null)
				return false;
		} else if (!nombreSolicitante.equals(other.nombreSolicitante))
			return false;
		if (numeroJugadores != other.numeroJugadores)
			return false;
		return true;
	}

	
}
