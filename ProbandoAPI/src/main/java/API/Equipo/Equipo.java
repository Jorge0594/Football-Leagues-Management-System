package API.Equipo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import API.Jugador.Jugador;

@Document(collection = "Equipo")
public class Equipo {

	public interface RankAtt {
	}

	public interface PerfilAtt {
	}

	@JsonView(RankAtt.class)
	@Id
	private String id;
	
	@JsonView(RankAtt.class)
	private String nombre;
	
	@JsonView(PerfilAtt.class)
	private String liga;
	
	@JsonView(PerfilAtt.class)
	private String ciudad;
	
	@JsonView(RankAtt.class)
	private String imagenEquipo;
	
	@JsonView(RankAtt.class)
	private int posicion;
	
	@JsonView(RankAtt.class)
	private int puntos;
	
	@JsonView(RankAtt.class)
	private int goles;
	
	@JsonView(RankAtt.class)
	private int golesEncajados;
	
	@JsonView(RankAtt.class)
	private int partidosGanados;
	
	@JsonView(RankAtt.class)
	private int partidosPerdidos;
	
	@JsonView(RankAtt.class)
	private int partidosEmpatados;
	
	@JsonView(RankAtt.class)
	private int partidosJugados;
	
	@JsonView(PerfilAtt.class)
	@DBRef
	private List<Jugador> plantillaEquipo = new ArrayList<>();
	/*
	 * @DBRef private List<Sancion>sanciones = new ArrayList<>();
	 */

	public Equipo() {
	}

	public Equipo(String id, String nombre, String liga, String ciudad, int posicion, int puntos, int golesEncajados,
			int goles, int partidosGanados, int partidosPerdidos, int partidosEmpatados, int partidosJugados,
			List<Jugador> plantillaEquipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.liga = liga;
		this.ciudad = ciudad;
		this.posicion = posicion;
		this.puntos = puntos;
		this.golesEncajados = golesEncajados;
		this.goles = goles;
		this.partidosGanados = partidosGanados;
		this.partidosPerdidos = partidosPerdidos;
		this.partidosEmpatados = partidosEmpatados;
		this.partidosJugados = partidosJugados;
		this.plantillaEquipo = plantillaEquipo;
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

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getImagenEquipo() {
		return imagenEquipo;
	}

	public void setImagenEquipo(String imagenEquipo) {
		this.imagenEquipo = imagenEquipo;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getGolesEncajados() {
		return golesEncajados;
	}

	public void setGolesEncajados(int golesEncajados) {
		this.golesEncajados = golesEncajados;
	}

	public int getGoles() {
		return goles;
	}

	public void setGoles(int goles) {
		this.goles = goles;
	}

	public int getPartidosGanados() {
		return partidosGanados;
	}

	public void setPartidosGanados(int partidosGanados) {
		this.partidosGanados = partidosGanados;
	}

	public int getPartidosPerdidos() {
		return partidosPerdidos;
	}

	public void setPartidosPerdidos(int partidosPerdidos) {
		this.partidosPerdidos = partidosPerdidos;
	}

	public int getPartidosEmpatados() {
		return partidosEmpatados;
	}

	public void setPartidosEmpatados(int partidosEmpatados) {
		this.partidosEmpatados = partidosEmpatados;
	}

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public List<Jugador> getPlantillaEquipo() {
		return plantillaEquipo;
	}

	public void setPlantillaEquipo(List<Jugador> plantillaEquipo) {
		this.plantillaEquipo = plantillaEquipo;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", nombre=" + nombre + ", liga=" + liga + ", ciudad=" + ciudad + ", imagenEquipo="
				+ imagenEquipo + ", posicion=" + posicion + ", puntos=" + puntos + ", golesEncajados=" + golesEncajados
				+ ", goles=" + goles + ", partidosGanados=" + partidosGanados + ", partidosPerdidos=" + partidosPerdidos
				+ ", partidosEmpatados=" + partidosEmpatados + ", partidosJugados=" + partidosJugados
				+ ", plantillaEquipo=" + plantillaEquipo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipo other = (Equipo) obj;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equals(other.ciudad))
			return false;
		if (goles != other.goles)
			return false;
		if (golesEncajados != other.golesEncajados)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagenEquipo == null) {
			if (other.imagenEquipo != null)
				return false;
		} else if (!imagenEquipo.equals(other.imagenEquipo))
			return false;
		if (liga == null) {
			if (other.liga != null)
				return false;
		} else if (!liga.equals(other.liga))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (partidosEmpatados != other.partidosEmpatados)
			return false;
		if (partidosGanados != other.partidosGanados)
			return false;
		if (partidosJugados != other.partidosJugados)
			return false;
		if (partidosPerdidos != other.partidosPerdidos)
			return false;
		if (plantillaEquipo == null) {
			if (other.plantillaEquipo != null)
				return false;
		} else if (!plantillaEquipo.equals(other.plantillaEquipo))
			return false;
		if (posicion != other.posicion)
			return false;
		if (puntos != other.puntos)
			return false;
		return true;
	}

}
