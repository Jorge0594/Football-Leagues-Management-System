package API.Sancion;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro.ActaAtt;

@Document(collection = "Sancion")
public class Sancion {

	public interface JugadorAtt {
	}

	public interface SancionAtt {
	}

	@Id
	@JsonView(SancionAtt.class)
	private String id;

	@JsonView(JugadorAtt.class)
	private String tipo;
	@JsonView(SancionAtt.class)
	private String sancionadoId;
	@JsonView(JugadorAtt.class)
	private String inicioSancion;
	@JsonView(JugadorAtt.class)
	private String finSancion;
	@JsonView(SancionAtt.class)
	private String arbitroSdrId;
	@JsonView(SancionAtt.class)
	private boolean enVigor;
	@JsonView(JugadorAtt.class)
	private int partidosSancionados;
	@JsonView(JugadorAtt.class)
	private int partidosRestantes;
	@JsonView(JugadorAtt.class)
	private String descripcion;

	public Sancion() {
	}

	public Sancion(String id, String tipo, String sancionadoId, String inicioSancion, String finSancion,
			String arbitroSdrId, boolean enVigor, int partidosSancionados, int partidosRestantes, String descripcion) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.sancionadoId = sancionadoId;
		this.inicioSancion = inicioSancion;
		this.finSancion = finSancion;
		this.arbitroSdrId = arbitroSdrId;
		this.enVigor = enVigor;
		this.partidosSancionados = partidosSancionados;
		this.partidosRestantes = partidosRestantes;
		this.descripcion = descripcion;
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

	public String getSancionadoId() {
		return sancionadoId;
	}

	public void setSancionadoId(String sancionadoId) {
		this.sancionadoId = sancionadoId;
	}

	public String getInicioSancion() {
		return inicioSancion;
	}

	public void setInicioSancion(String inicioSancion) {
		this.inicioSancion = inicioSancion;
	}

	public String getFinSancion() {
		return finSancion;
	}

	public void setFinSancion(String finSancion) {
		this.finSancion = finSancion;
	}

	public String getArbitroSdrId() {
		return arbitroSdrId;
	}

	public void setArbitroSdrId(String arbitroSdrId) {
		this.arbitroSdrId = arbitroSdrId;
	}

	public boolean isEnVigor() {
		return enVigor;
	}

	public void setEnVigor(boolean enVigor) {
		this.enVigor = enVigor;
	}

	public int getPartidosSancionados() {
		return partidosSancionados;
	}

	public void setPartidosSancionados(int partidosSancionados) {
		this.partidosSancionados = partidosSancionados;
	}

	public int getPartidosRestantes() {
		return partidosRestantes;
	}

	public void setPartidosRestantes(int partidosRestantes) {
		this.partidosRestantes = partidosRestantes;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	
}
