package API.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;

@Component
public class UsuarioRepositoryAuthenticationProvider implements AuthenticationProvider {
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioComponent userComponent;
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String nombreUsuario = auth.getName();
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(nombreUsuario);

		if (usuario == null) {
			throw new BadCredentialsException("User not found");
		}
		String password = (String) auth.getCredentials();
		if (!new BCryptPasswordEncoder().matches(password, usuario.getClave())) {
			throw new BadCredentialsException("Wrong password");
		}
		userComponent.setLoggedUser(usuario);
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(usuario.getRol()));
		

		return new UsernamePasswordAuthenticationToken(nombreUsuario, password, roles);
	}

	@Override
	public boolean supports(Class<?> authenticationObject) {
		return true;
	}

}
