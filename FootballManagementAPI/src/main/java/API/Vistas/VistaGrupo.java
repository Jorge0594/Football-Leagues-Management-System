package API.Vistas;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;



@Component
public class VistaGrupo {
	
	public interface VistaGrupoAtt{}
	
	@JsonView(VistaGrupoAtt.class)
	private String idGrupo;
	@JsonView(VistaGrupoAtt.class)
	private String nombre;

    public VistaGrupo() {}
    
	public VistaGrupo(String idGrupo, String nombre) {
		super();
		this.idGrupo = idGrupo;
		this.nombre = nombre;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		VistaGrupo other = (VistaGrupo) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
