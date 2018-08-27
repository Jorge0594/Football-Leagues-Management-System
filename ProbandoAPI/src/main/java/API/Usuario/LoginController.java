package API.Usuario;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsuarioComponent usuarioComponent;
	
	@RequestMapping("/iniciarSesion/{rol}")
	public ResponseEntity<Usuario> logIn(@PathVariable (value = "rol")String rol) {
		if (!usuarioComponent.isLoggedUser() || !usuarioComponent.getLoggedUser().getRol().equals(rol)) {
			log.info("Not user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			Usuario loggedUser = usuarioComponent.getLoggedUser();
			log.info("Logged as " + loggedUser.getId());
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}
	
	@RequestMapping("/iniciarSesion")
	public ResponseEntity<Usuario> logInGlobal() {
		if (!usuarioComponent.isLoggedUser()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			Usuario loggedUser = usuarioComponent.getLoggedUser();
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}

	@RequestMapping("/cerrarSesion")
	public ResponseEntity<Boolean> logOut(HttpSession session) {
		if (!usuarioComponent.isLoggedUser()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}

}