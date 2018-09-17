package API.Mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EnvioEmail {
	
	@Autowired
	private JavaMailSender envioEmail;
	
	private final String bienvenida = "Bienvenido a nuestra aplicación.\n"
			+ "\nEn nuestra app podrá visualizar de manera rápida y sencilla toda la información referente a su liga, equipo, o de su propio perfil.\n"
			+ "\nSe le ha asignado el siguiente usuario y contraseña para el acceso a la aplicación:\n";
	
	private final String credencialesTexto = "Su usuario y contraseña de acceso es:\n";
	
	public void mandarEmail (String destinatario, String asunto, String texto, String tipo){
		
		/*String credenciales [] = texto.split(";");
		
		String cuerpoCorreo = "Hola " + credenciales[0] + "\n\n" + bienvenida + "\n" + "USUARIO: " + credenciales[1] +
				"\n\n" + "CONTRASEÑA: " + credenciales[2] +
				"\n\n Le recomendamos que cambie su contraseña por una más segura una vez haya accedido a la aplicación.\n"
				+ "\n¡Buena suerte en sus partidos!"; */
		String credenciales [] = texto.split(";");
		String cuerpoCorreo = generarCuerpoMensaje(tipo, credenciales);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(destinatario);//Correo del usuario.
		mail.setSubject(asunto);
		mail.setText(cuerpoCorreo);
		
		envioEmail.send(mail);
	}
	
	private String generarCuerpoMensaje(String tipo, String[] credenciales){
		String cuerpo = "";
		switch(tipo){
		case "jugador":
			cuerpo = "Hola " + credenciales[0] + "\n\n" + bienvenida + "\n" + "USUARIO: " + credenciales[1] +
					"\n\n" + "CONTRASEÑA: " + credenciales[2] +
					"\n\n Le recomendamos que cambie su contraseña por una más segura una vez haya accedido a la aplicación.\n"
					+ "\n¡Buena suerte en sus partidos!"; 
			break;
		case "claveusuario":
			cuerpo = credencialesTexto + "\n\n"+ "USUARIO: " + credenciales[0] +"\n\n" +  "CONTRASEÑA: " + credenciales[1];
			break;
		case "temporal":
			cuerpo = credencialesTexto + "\n\n"+ "USUARIO: " + credenciales[0] +"\n\n" +  "CONTRASEÑA: " + credenciales[1] +
					 "\n\n Recuerde solo tendrá acceso a la aplicación durante 7 días o hasta que su equipo sea aceptado por el comité.";
			break;
		default:
			cuerpo = "ERROR";	
		}
		
		return cuerpo;
	}
}
