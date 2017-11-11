package API.Incidencia;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Incidencias")		
public class Incidencia {
@Id
private String id;
private String tipo;
private String idJugador;
private String idPartido;

			
public Incidencia() {}

public Incidencia(String id, String tipo, String jugador, String partido) {
	super();
	this.id = id;
	this.tipo = tipo;
	this.idJugador = jugador;
	this.idPartido = partido;
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


public String getIdJugador() {
	return idJugador;
}


public void setIdJugador(String jugador) {
	this.idJugador = jugador;
}

public String getIdPartido() {
	return idPartido;
}

public void setIdPartido(String partido) {
	this.idPartido = partido;
}




}
