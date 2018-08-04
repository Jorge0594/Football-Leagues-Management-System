package API.Temporada;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro;
import API.VistaGrupo.VistaGrupo;


@Document(collection = "Temporada")
public class Temporada {

	public interface TemporadaAtt {
	}

	@Id
	@JsonView(TemporadaAtt.class)
	private String id;

	@JsonView(TemporadaAtt.class)
	private String nombre;
	
	@JsonView(TemporadaAtt.class)
	private List<Arbitro> arbitros;
	

	@JsonView(TemporadaAtt.class)
	private List<VistaGrupo> grupos;

	public Temporada() {}
	public Temporada(String nombre) {
		super();
		this.nombre = nombre;
		this.grupos = new ArrayList<VistaGrupo>();
		this.arbitros = new ArrayList<Arbitro>();
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

	public List<Arbitro> getArbitros() {
		return this.arbitros;
	}

	public void setArbitros(List<Arbitro> arbitros) {
		this.arbitros = arbitros;
	}

	public List<VistaGrupo> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<VistaGrupo> grupos) {
		this.grupos = grupos;
	}

	public boolean addVistaGrupo(VistaGrupo vista) {
		if(grupos!= null && grupos.contains(vista)) {
			return false;
		}else{
			grupos.add(vista);
			return true;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupos == null) ? 0 : grupos.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((arbitros == null) ? 0 : arbitros.hashCode());
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
		Temporada other = (Temporada) obj;
		if (arbitros == null) {
			if (other.arbitros != null)
				return false;
		} else if (!arbitros.equals(other.arbitros))
			return false;
		if (grupos == null) {
			if (other.grupos != null)
				return false;
		} else if (!grupos.equals(other.grupos))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
