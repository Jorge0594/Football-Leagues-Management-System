package API.Jugador;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface JugadorRepository extends MongoRepository<Jugador, String> {
	
	List<Jugador> findByNombreIgnoreCase(String nombre);
	
	List<Jugador>findByEquipoIgnoreCase(String equipo);
	
	List<Jugador>findByEstadoIgnoreCase(String estado);
	
	List<Jugador>findByEstadoAndEquipoAllIgnoreCase(String estado, String equipo);
	
	List<Jugador>findByGrupoNombreIgnoreCase(String nombreGrupo);
	
	List<Jugador>findByGrupoIdGrupo(String id);
	
	List<Jugador>findByAceptado(boolean aceptado);
	
	Jugador findByNombreAndApellidosAllIgnoreCase(String nombre, String apellido);
	
	Jugador findById(String id);
	
	Jugador findByEmailIgnoreCase(String email);
	
	Jugador findByDniIgnoreCase(String dni);
	
	Jugador findByDelegadoAndEquipo(boolean capitan, String idEquipo);
	
	Jugador findByDorsalAndEquipo(int dorsal,String equipo);
	
	Jugador findByNombreUsuarioIgnoreCase(String nombreUsuario);
	
	@Query(value = "{'grupo.idGrupo':?0}")
	List<Jugador> getRankings(String grupoId, Pageable page);
	
	@Query(value = "{'posicion': 'Portero', 'grupo.idGrupo': ?0}", fields = "{'nombre':1, 'equipo':1, 'fotoJugador':1}")
	List<Jugador> getPorteros(String idGrupo);
	
}
