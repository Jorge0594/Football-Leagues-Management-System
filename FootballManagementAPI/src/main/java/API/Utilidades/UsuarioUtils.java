package API.Utilidades;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import API.Usuario.UsuarioRepository;

@Component
public class UsuarioUtils {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public String generarNombreUsuario(String nombre, String apellidos){
		String apellido[] = apellidos.split(" ");
		String usuario = nombre + apellido[0];
		Random rnd = new Random();

		while (usuarioRepository.findByNombreUsuarioIgnoreCase(usuario) != null) {
			
			int num = rnd.nextInt(1000);
			if (usuarioRepository.findByNombreUsuarioIgnoreCase((usuario += num)) == null) {
				usuario += num;
			}
		}
		return usuario;
	}
	
	public String generarClave(){
		String clave = "";
		Random rnd = new Random();
		
		for (int i = 0; i < 5; i++) {
			clave = clave + ((char) (rnd.nextInt(27) + 63));// Caracteres del
															// '?' a la 'Z'
			clave = clave + ((char) (rnd.nextInt(25) + 97));// Carateres de la
															// 'a' a la 'z'
		}
		return clave;
	}
	
	
}