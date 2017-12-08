package API.Mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private EnvioEmail mail;
	
	public MailService(){}

	public EnvioEmail getMail() {
		return mail;
	}

	public void setMail(EnvioEmail mail) {
		this.mail = mail;
	}
	
	
}
