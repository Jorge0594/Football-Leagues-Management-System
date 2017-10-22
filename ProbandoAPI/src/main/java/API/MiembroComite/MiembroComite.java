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


@Override
public String toString() {
	return "MiembroComite [Id= " + id + ", Nombre= " + nombre + ", Apellidos= " + apellidos +", Usuario= " + usuario + ", Contraseña= " + clave
			+ ", Comite=" + comite + "]";
}


}
