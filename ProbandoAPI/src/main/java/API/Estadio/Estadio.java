package API.Estadio;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Estadios")
public class Estadio {
	@Id
	private String id;
	private String nombre;
	private int capacidad;
	private String pais;
	private String provincia;
	private String poblacion;
	private int cp;
	private String localizacion;
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
	};

}
