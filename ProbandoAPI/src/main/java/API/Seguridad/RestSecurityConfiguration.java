package API.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UsuarioRepositoryAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.httpBasic();

		// URLs that need authentication to access to it
		//Jugadores
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/jugadores").hasAnyRole("JUGADOR", "MIEMBROCOMITE");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/jugadores/**").hasAnyRole("JUGADOR", "MIEMBROCOMITE","ARBITRO");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/jugadores/**").hasAnyRole("JUGADOR", "MIEMBROCOMITE");
		
		//Arbitros
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/arbitros").hasAnyRole("ARBITRO", "MIEMBROCOMITE");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/arbitros/**").hasAnyRole("ARBITRO","MIEMBROCOMITE");
		
		//MiembrosComite
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Incidencias
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/incidencias/**").hasAnyRole("ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/miembrosComite/**").hasAnyRole("ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		http.authorizeRequests().anyRequest().permitAll();

		http.logout().logoutSuccessHandler((rq, rs, a) -> {
		});
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}
}
