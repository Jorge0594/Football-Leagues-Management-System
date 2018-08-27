package API.Equipo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import API.Estadio.Estadio;
import API.Jugador.Jugador;
import API.Vistas.VistaGrupo;

@Document(collection = "Equipo")
public class Equipo implements Comparable<Equipo> {

	public interface RankAtt {
	}

	public interface PerfilAtt {
	}

	@JsonView(RankAtt.class)
	@Id
	private String id;

	@JsonView(RankAtt.class)
	private String nombre;

	@JsonView(RankAtt.class)
	private VistaGrupo grupo;
	
	@JsonView(RankAtt.class)
	private String liga;

	@JsonView(PerfilAtt.class)
	private String ciudad;
	
	@JsonView(PerfilAtt.class)
	private String delegado;

	@JsonView(RankAtt.class)
	private String imagenEquipo;

	@JsonView(PerfilAtt.class)
	private boolean aceptado;

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
	
	@JsonView (PerfilAtt.class)
	@DBRef
	private Estadio estadioEquipo;

	@JsonView(RankAtt.class)
	@DBRef
	private List<Jugador> plantillaEquipo = new ArrayList<>();

	public Equipo() {
	}

	public Equipo(String id, String nombre, String liga, VistaGrupo grupo, String ciudad, int posicion, int golesEncajados, int goles,
			List<Jugador> plantillaEquipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.aceptado = false;
		this.liga = liga;
		this.grupo = grupo;
		this.ciudad = ciudad;
		this.posicion = posicion;
		this.puntos = partidosGanados * 3 + partidosEmpatados;
		this.golesEncajados = golesEncajados;
		this.goles = goles;
		this.partidosJugados = partidosGanados + partidosPerdidos + partidosEmpatados;
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

	public VistaGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(VistaGrupo grupo) {
		this.grupo = grupo;
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

	public void setPuntos() {
		this.puntos = partidosGanados * 3 + partidosEmpatados;
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

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public void setPartidosGanados(int partidosGanados) {
		this.puntos = this.puntos + 3 * (partidosGanados - this.partidosGanados);
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
		this.puntos = this.puntos + (partidosEmpatados - this.partidosEmpatados);
		this.partidosEmpatados = partidosEmpatados;
	}

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados() {
		this.partidosJugados = partidosGanados + partidosPerdidos + partidosEmpatados;
	}

	public List<Jugador> getPlantillaEquipo() {
		return plantillaEquipo;
	}

	public void setPlantillaEquipo(List<Jugador> plantillaEquipo) {
		this.plantillaEquipo = plantillaEquipo;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public Estadio getEstadioEquipo() {
		return estadioEquipo;
	}

	public void setEstadioEquipo(Estadio estadioEquipo) {
		this.estadioEquipo = estadioEquipo;
	}

	public String getDelegado() {
		return delegado;
	}

	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}

	@Override
	public String toString() {
		return "Equipo [id=" + id + ", nombre=" + nombre + ", grupo=" + grupo + ", liga=" + liga + ", ciudad=" + ciudad + ", delegado=" + delegado + ", imagenEquipo=" + imagenEquipo + ", aceptado=" + aceptado + ", posicion=" + posicion + ", puntos=" + puntos + ", goles=" + goles
				+ ", golesEncajados=" + golesEncajados + ", partidosGanados=" + partidosGanados + ", partidosPerdidos=" + partidosPerdidos + ", partidosEmpatados=" + partidosEmpatados + ", partidosJugados=" + partidosJugados + ", estadioEquipo=" + estadioEquipo + ", plantillaEquipo="
				+ plantillaEquipo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + goles;
		result = prime * result + golesEncajados;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagenEquipo == null) ? 0 : imagenEquipo.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + partidosEmpatados;
		result = prime * result + partidosGanados;
		result = prime * result + partidosJugados;
		result = prime * result + partidosPerdidos;
		result = prime * result + ((plantillaEquipo == null) ? 0 : plantillaEquipo.hashCode());
		result = prime * result + posicion;
		result = prime * result + puntos;
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
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
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

	@Override
	public int compareTo(Equipo o) {
		if (this.puntos > o.puntos) {
			return -1;
		} else if (this.puntos < o.puntos) {
			return 1;
		}

		if (this.puntos == o.puntos) {
			if ((this.goles - this.golesEncajados) > (o.goles - o.golesEncajados)) {
				return -1;
			} else if((this.goles - this.golesEncajados) == (o.goles - o.golesEncajados)) {
				if(this.goles > o.goles){
					return -1;
				} else if(this.goles == o.goles) {
					if(this.getTarjetasRojas() < o.getTarjetasRojas()){
						return -1;
					} else if(this.getTarjetasRojas() == o.getTarjetasRojas()){
						if(this.getTarjetasAmarillas() < o.getTarjetasRojas()){
							return -1;
						} 
					}
				}
			}
		}
		return 1;
	}
	
	private int getTarjetasAmarillas(){
		return this.getPlantillaEquipo().stream()
				.map(Jugador::getTarjetasAmarillas)
				.reduce(0, (j1, j2) -> j1 + j2);
	}
	
	private int getTarjetasRojas(){
		return this.getPlantillaEquipo().stream()
				.map(Jugador::getTarjetasRojas)
				.reduce(0, (j1, j2) -> j1 + j2);
	}

}
