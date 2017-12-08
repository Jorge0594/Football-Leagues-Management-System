package API.Mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EnvioEmail {
	
	@Autowired
	public JavaMailSender envioEmail;
	
	
	public void mandarEmail (String destinatatio, String asunto, String texto){
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo("jsm0594@gmail.com");
		mail.setSubject("tfg");
		mail.setText(texto);
		
		envioEmail.send(mail);
	}
}
