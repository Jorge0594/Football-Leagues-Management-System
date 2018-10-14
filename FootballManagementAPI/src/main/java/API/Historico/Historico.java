package API.Historico;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import API.Vistas.VistaTemporada;

@Component
public abstract class Historico implements Comparable<Historico> {
	@Id
	private String id;
	private VistaTemporada temporada;
	
	public Historico(){}
	
	public Historico(String id, VistaTemporada temporada) {
		super();
		this.id = id;
		this.temporada = temporada;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public VistaTemporada getTemporada() {
		return temporada;
	}

	public void setTemporada(VistaTemporada temporada) {
		this.temporada = temporada;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", temporada=" + temporada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((temporada == null) ? 0 : temporada.hashCode());
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
		Historico other = (Historico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (temporada == null) {
			if (other.temporada != null)
				return false;
		} else if (!temporada.equals(other.temporada))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Historico o) {
		
		int firstYear1 = añoNumericoTemporadas(this.getTemporada().getNombre());
		int firstYear2 = añoNumericoTemporadas(o.getTemporada().getNombre());
		
		if(firstYear1 > firstYear2){
			return -1;
		} else if(firstYear1 < firstYear2){
			return 1;
		}
		
		return 0;
	}
	
	private int añoNumericoTemporadas(String fecha){
		if(fecha.contains("-")){
			return Integer.parseInt(fecha.split("-")[0]);
		} else if(fecha.contains("/")){
			return Integer.parseInt(fecha.split("/")[0]);
		}
		return 0;
	}

}
