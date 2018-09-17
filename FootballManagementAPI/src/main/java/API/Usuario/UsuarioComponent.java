package API.Usuario;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioComponent {
	
	private Usuario usuario;
	
	public Usuario getLoggedUser() {
		return usuario;
	}

	public void setLoggedUser(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLoggedUser() {
		return this.usuario != null;
	
	}
}
