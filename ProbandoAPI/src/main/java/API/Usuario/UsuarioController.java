package API.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>>verUsuarios(){
		return new ResponseEntity<List<Usuario>>(usuarioRepository.findAll(),HttpStatus.OK);
	}
}
