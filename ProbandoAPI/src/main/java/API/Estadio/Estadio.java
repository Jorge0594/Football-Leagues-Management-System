package API.Estadio;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection = "Estadios")
public class Estadio {
	
	public interface DatosAtt{}
	public interface BasicoAtt{}
	
	
	@Id
	@JsonView(BasicoAtt.class)
	private String id;
	
	@JsonView(BasicoAtt.class)
	private String nombre;
	
	@JsonView(DatosAtt.class)
	private int capacidad;
	
	@JsonView(DatosAtt.class)
	private String pais;
	
	@JsonView(DatosAtt.class)
	private String provincia;
	
	@JsonView(DatosAtt.class)
	private String poblacion;
	
	@JsonView(DatosAtt.class)
	private int cp;
	
	@JsonView(DatosAtt.class)
	private double latitud;
	
	@JsonView(DatosAtt.class)
	private double longitud;
	
	@JsonView(DatosAtt.class)
	private String localizacion;
	
	@JsonView(DatosAtt.class)
	private String dimensiones;

	public Estadio() {
	}

	public Estadio(String id, String nombre, int capacidad, String pais, String provincia, String poblacion, int cp,
			String localizacion, String dimensiones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.pais = pais;
		this.provincia = provincia;
		this.poblacion = poblacion;
		this.cp = cp;
		this.localizacion = localizacion;
		this.dimensiones = dimensiones;
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

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacidad;
		result = prime * result + cp;
		result = prime * result + ((dimensiones == null) ? 0 : dimensiones.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((localizacion == null) ? 0 : localizacion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((poblacion == null) ? 0 : poblacion.hashCode());
		result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
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
		Estadio other = (Estadio) obj;
		if (capacidad != other.capacidad)
			return false;
		if (cp != other.cp)
			return false;
		if (dimensiones == null) {
			if (other.dimensiones != null)
				return false;
		} else if (!dimensiones.equals(other.dimensiones))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (localizacion == null) {
			if (other.localizacion != null)
				return false;
		} else if (!localizacion.equals(other.localizacion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (poblacion == null) {
			if (other.poblacion != null)
				return false;
		} else if (!poblacion.equals(other.poblacion))
			return false;
		if (provincia == null) {
			if (other.provincia != null)
				return false;
		} else if (!provincia.equals(other.provincia))
			return false;
		return true;
	}
	
	

}
