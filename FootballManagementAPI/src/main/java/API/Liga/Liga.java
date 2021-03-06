package API.Liga;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonView;

import API.Temporada.Temporada;
import API.Vistas.VistaTemporada;


@Document(collection = "Liga")
public class Liga {

	public interface LigaAtt {
	}

	@Id
	@JsonView(LigaAtt.class)
	private String id;

	@JsonView(LigaAtt.class)
	private String nombre;
	@DBRef
	@JsonView(LigaAtt.class)
	private List<Temporada> temporadas = new ArrayList<>();
	
	@JsonView(LigaAtt.class)
	private VistaTemporada temporadaActual;
	
	@JsonView(LigaAtt.class)
	private String imagenLiga;


	public Liga(){}
	
	public Liga(String nombre) {
		super();
		this.temporadas = null;
		this.nombre = nombre;
		this.temporadaActual = null;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public VistaTemporada getTemporadaActual() {
		return temporadaActual;
	}

	public void setTemporadaActual(VistaTemporada temporadaActual) {
		this.temporadaActual = temporadaActual;
	}
	
	public List<Temporada> getTemporadas() {
		return this.temporadas;
	}

	public void setTemporadas(List<Temporada> temporadas) {
		this.temporadas = temporadas;
	}

	public String getImagenLiga() {
		return imagenLiga;
	}

	public void setImagenLiga(String imagenLiga) {
		this.imagenLiga = imagenLiga;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((temporadas == null) ? 0 : temporadas.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Liga other = (Liga) obj;
		if (temporadas == null) {
			if (other.temporadas != null)
				return false;
		} else if (!temporadas.equals(other.temporadas))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
