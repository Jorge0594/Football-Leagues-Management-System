package API.Sancion;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "Sancion")
public class Sancion {

	@Id
	private String id;
	private String tipo;
	private String jugadorId;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicioSancion;
	private String arbitroSdrId;
	private String estado;
	private int partidosSancionados;
	private int partidosCumplidos;
	
	public Sancion() {}
	
	
	
	public Sancion(String id, String tipo, String jugadorId, Date inicioSancion, String arbitroSdrId, String estado,
			int partidosSancionados, int partidosCumplidos) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.jugadorId = jugadorId;
		this.inicioSancion = inicioSancion;
		this.arbitroSdrId = arbitroSdrId;
		this.estado = estado;
		this.partidosSancionados = partidosSancionados;
		this.partidosCumplidos = partidosCumplidos;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getJugadorId() {
		return jugadorId;
	}
	public void setJugadorId(String jugadorId) {
		this.jugadorId = jugadorId;
	}
	public Date getInicioSancion() {
		return inicioSancion;
	}
	public void setInicioSancion(Date inicioSancion) {
		this.inicioSancion = inicioSancion;
	}
	public String getArbitroSdrId() {
		return arbitroSdrId;
	}
	public void setArbitroSdrId(String arbitroSdrId) {
		this.arbitroSdrId = arbitroSdrId;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getPartidosSancionados() {
		return partidosSancionados;
	}
	public void setPartidosSancionados(int partidosSancionados) {
		this.partidosSancionados = partidosSancionados;
	}
	public int getPartidosCumplidos() {
		return partidosCumplidos;
	}
	public void setPartidosCumplidos(int partidosCumplidos) {
		this.partidosCumplidos = partidosCumplidos;
	}
	
	
	
	

	
		
	
	
}
