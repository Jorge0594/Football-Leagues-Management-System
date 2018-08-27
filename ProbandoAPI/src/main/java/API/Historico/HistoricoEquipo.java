package API.Historico;

import org.springframework.data.mongodb.core.mapping.Document;

import API.Equipo.Equipo;
import API.Vistas.VistaTemporada;

@Document(collection = "HistoricoEquipo")
public class HistoricoEquipo extends Historico{

	private Equipo equipo;
	
	public HistoricoEquipo(){}
	
	public HistoricoEquipo(Equipo equipo) {
		super();
		this.equipo = equipo;
	}
	
	public HistoricoEquipo(String id, VistaTemporada temporada, Equipo equipo){
		super(id, temporada);
		this.equipo = equipo;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	@Override
	public String toString() {
		return "HistoricoEquipo" + super.toString() + ", equipo=" + equipo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoEquipo other = (HistoricoEquipo) obj;
		if (equipo == null) {
			if (other.equipo != null)
				return false;
		} else if (!equipo.equals(other.equipo))
			return false;
		return true;
	}
	
}
