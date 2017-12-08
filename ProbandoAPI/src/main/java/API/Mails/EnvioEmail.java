package API.Mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EnvioEmail {
	
	@Autowired
	private JavaMailSender envioEmail;
	
	private final String correo = "Bienvenido a nuestra aplicación.\n"
			+ "\nEn nuestra app podrá visualizar de manera rápida y sencilla toda la información referente a su liga, equipo, o de su propio perfil.\n"
			+ "\nSe le ha asignado el siguiente usuario y contraseña para el acceso a la aplicación:\n";
	
	public void mandarEmail (String destinatatio, String asunto, String texto){
		
		String credenciales [] = texto.split(";");
		
		String cuerpoCorreo = "Hola " + credenciales[0] + "\n\n" + correo + "\n\n" + "USUARIO: " + credenciales[1] +
				"\n\n" + "CONTRASEÑA: " + credenciales[2] +
				"\n\n Le recomendamos que cambie su contraseña por una más segura una vez haya accedido a la aplicación.\n"
				+ "\n¡Buena suerte en sus partidos!"; 
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(credenciales[3]);//Correo del usuario.
		mail.setSubject("tfg");
		mail.setText(cuerpoCorreo);
		
		envioEmail.send(mail);
	}
}
