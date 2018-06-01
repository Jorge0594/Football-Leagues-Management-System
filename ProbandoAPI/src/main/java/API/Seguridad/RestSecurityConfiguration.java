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

		http.authorizeRequests().antMatchers(HttpMethod.GET, "/jugadores/clave/{dni}").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/jugadores/**").hasAnyRole("MIEMBROCOMITE","ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/jugadores/clave/{email}").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/jugadores/validar/email/{email}").hasAnyRole("JUGADOR","ARBITRO", "MIEMBROCOMITE","ADMIN","TEMPORAL");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/jugadores/validar/dni/{dni}").hasAnyRole("JUGADOR","ARBITRO", "MIEMBROCOMITE","ADMIN","TEMPORAL");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/jugadores/**").hasAnyRole("JUGADOR","ARBITRO","MIEMBROCOMITE","ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/jugadores/**").hasAnyRole("JUGADOR", "MIEMBROCOMITE","ARBITRO","ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/jugadores/**").hasAnyRole("MIEMBROCOMITE","ADMIN");
		
		//Equipos
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/equipos/temporal").hasAnyRole("MIEMBROCOMITE", "ADMIN","TEMPORAL");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/equipos/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/equipos/validar/{nombre}/{liga}").hasAnyRole("JUGADOR","ARBITRO", "MIEMBROCOMITE","ADMIN","TEMPORAL");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/equipos/id/{id}").hasAnyRole("JUGADOR","ARBITRO", "MIEMBROCOMITE","ADMIN","TEMPORAL");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/equipos/**").hasAnyRole("JUGADOR","ARBITRO", "MIEMBROCOMITE","ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/equipos/**").hasAnyRole("MIEMBROCOMITE","ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/equipos/**").hasAnyRole("MIEMBROCOMITE","ADMIN");
		
		//Arbitros
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/arbitros/**").hasAnyRole("ARBITRO", "MIEMBROCOMITE","ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/arbitros/**").hasAnyRole("ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/arbitros/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/arbitros/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//MiembrosComite
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/miembrosComite/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Incidencias
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/incidencias/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/incidencias/**").hasAnyRole("ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/incidencias/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Sanciones
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/sanciones/**").hasAnyRole("ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/sanciones/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/sanciones/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Partidos
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/partidos/**").hasAnyRole("JUGADOR","ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/partidos/addConvocadoLocal/**").hasAnyRole("MIEMBROCOMITE", "ADMIN","ARBITRO");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/partidos/addConvocadoVisitante/**").hasAnyRole("MIEMBROCOMITE", "ADMIN","ARBITRO");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/partidos/**").hasAnyRole("MIEMBROCOMITE", "ADMIN", "ARBITRO");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/partidos/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/partidos/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Usuarios temporales
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/temporales/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/temporales/usuario").hasAnyRole("MIEMBROCOMITE", "ADMIN","TEMPORAL");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/temporales/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/temporales/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/temporales/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Liga
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/ligas/nombres").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/ligas/**").hasAnyRole("JUGADOR","ARBITRO","MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/ligas/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/ligas/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/ligas/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
		//Solicitudes
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/solicitudes/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/solicitudes/email/{email}").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/solicitudes/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/solicitudes/**").hasAnyRole("MIEMBROCOMITE", "ADMIN");
		
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
