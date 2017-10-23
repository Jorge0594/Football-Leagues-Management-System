package API.MiembroComite;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="MiembrosComite")		
public class MiembroComite {
@Id
private String id;
private String nombre;
private String apellidos;
private String usuario;
private String clave; 
private String comite;

public MiembroComite() {}

public MiembroComite(String id, String nombre, String apellidos, String usuario, String clave, String comite) {
	super();
	this.id = id;
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.usuario = usuario;
	this.clave = clave;
	this.comite = comite;
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


public String getApellidos() {
	return apellidos;
}


public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
}


public String getUsuario() {
	return usuario;
}


public void setUsuario(String usuario) {
	this.usuario = usuario;
}


public String getClave() {
	return clave;
}


public void setClave(String clave) {
	this.clave = clave;
}


public String getComite() {
	return comite;
}


public void setComite(String comite) {
	this.comite = comite;
}



@Override
public String toString() {
	return "MiembroComite [Id= " + id + ", Nombre= " + nombre + ", Apellidos= " + apellidos +", Usuario= " + usuario + ", Contraseña= " + clave
			+ ", Comite=" + comite + "]";
}


}
