package API.Arbitro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/arbitros")
public class ArbitroController {

	@Autowired
	ArbitroRepository arbitroRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	UsuarioComponent usuarioComponent;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Arbitro> creaArbitro(@RequestBody Arbitro arbitro) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<String> nombresUsuarios = new ArrayList<>();
		for (Usuario us : usuarios) {
			nombresUsuarios.add(us.getNombreUsuario());
		}
		// Comprueba si el nombre de usuario no se encuentra ya en el sistema.
		if (!nombresUsuarios.contains(arbitro.getNombreUsuario())) {
			// Se encripta la clave
			arbitro.setClaveEncriptada(arbitro.getClave());
			arbitroRepository.save(arbitro);
			Usuario nuevo = new Usuario(arbitro.getNombreUsuario(), arbitro.getClave(), "ROLE_ARBITRO");
			usuarioRepository.save(nuevo);
			return new ResponseEntity<Arbitro>(arbitro, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Arbitro> modificaArbitro(@PathVariable String id, @RequestBody Arbitro arbitroModificado) {
		Arbitro entrada = arbitroRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Usuario modificado = usuarioRepository.findByNombreUsuarioIgnoreCase(entrada.getNombreUsuario());
			// Si el usuario conectado es un árbitro
			if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
				// Si el árbitro está realizando una modificación a su propio
				// usuario.
				if (entrada.getNombreUsuario().equals(usuarioComponent.getLoggedUser().getNombreUsuario())) {
					entrada.setNombre(arbitroModificado.getNombre());
					entrada.setNombreUsuario(arbitroModificado.getNombreUsuario());
					// Si se ha realizado un cambio en la contraseña se encripta
					// y se guarda, si es la misma no se toca.
					if (!arbitroModificado.getClave().equals(entrada.getClave())) {
						entrada.setClaveEncriptada(arbitroModificado.getClave());
					}
					entrada.setFechaNacimiento(arbitroModificado.getFechaNacimiento());
					entrada.setEdad(arbitroModificado.getEdad());
					entrada.setLugarNacimiento(arbitroModificado.getLugarNacimiento());
					entrada.setEmail(arbitroModificado.getEmail());
					entrada.setTlf(arbitroModificado.getTlf());
					arbitroRepository.save(entrada);
					// Se realizan los cambios en el listado de Usuarios de la
					// API.
					modificado.setClave(entrada.getClave());
					modificado.setNombreUsuario(entrada.getNombreUsuario());
					usuarioRepository.save(modificado);
					return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			}
			// Si el usuario conectado es un miembro del comité o un
			// administrador
			if ((usuarioComponent.getLoggedUser().getRol().equals("ROLE_MIEMBROCOMITE"))
					|| (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ADMIN"))) {
				arbitroModificado.setId(entrada.getId());
				entrada.setNombre(arbitroModificado.getNombre());
				entrada.setNombreUsuario(arbitroModificado.getNombreUsuario());
				// Si se ha realizado un cambio en la contraseña se encripta y
				// se guarda, si es la misma no se toca.
				if (!arbitroModificado.getClave().equals(entrada.getClave())) {
					entrada.setClaveEncriptada(arbitroModificado.getClave());
				}
				entrada.setFechaNacimiento(arbitroModificado.getFechaNacimiento());
				entrada.setEdad(arbitroModificado.getEdad());
				entrada.setLugarNacimiento(arbitroModificado.getLugarNacimiento());
				entrada.setEmail(arbitroModificado.getEmail());
				entrada.setTlf(arbitroModificado.getTlf());
				entrada.setCategoria(arbitroModificado.getCategoria());
				entrada.setEstado(arbitroModificado.getEstado());
				entrada.setPartidosArbitrados(arbitroModificado.getPartidosArbitrados());
				entrada.setDni(arbitroModificado.getDni());
				entrada.setInternacional(arbitroModificado.isInternacional());
				entrada.setComite(arbitroModificado.getComite());
				arbitroRepository.save(entrada);
				// Se realizan los cambios en el listado de Usuarios de la API.
				modificado.setClave(entrada.getClave());
				modificado.setNombreUsuario(entrada.getNombreUsuario());
				usuarioRepository.save(modificado);
				return new ResponseEntity<Arbitro>(arbitroModificado, HttpStatus.OK);
			}
			// No debería entrar aquí nunca, por los permisos de Roles.
			else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

		}

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitros() {
		return new ResponseEntity<List<Arbitro>>(arbitroRepository.findAll(), HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroId(@PathVariable String id) {
		Arbitro entrada = arbitroRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/dni/{dni}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroDni(@PathVariable String dni) {
		Arbitro entrada = arbitroRepository.findByDni(dni);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/nombreUsuario/{nombreUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Arbitro> verArbitroNombreUsuario(@PathVariable String nombreUsuario) {
		Arbitro entrada = arbitroRepository.findByNombreUsuario(nombreUsuario);
		if (entrada == null) {
			return new ResponseEntity<Arbitro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Arbitro>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/comite/{comite}", method = RequestMethod.GET)
	public ResponseEntity<List<Arbitro>> verArbitrosComite(@PathVariable String comite) {
		List<Arbitro> entrada = arbitroRepository.findByComite(comite);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Arbitro>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Arbitro>>(entrada, HttpStatus.OK);
	}

}
