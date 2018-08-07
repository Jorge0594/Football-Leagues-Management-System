package API.Jugador;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface JugadorRepository extends MongoRepository<Jugador, String> {
	
	List<Jugador> findByNombreIgnoreCase(String nombre);
	
	List<Jugador>findByEquipoIgnoreCase(String equipo);
	
	List<Jugador>findByEstadoIgnoreCase(String estado);
	
	List<Jugador>findByEstadoAndEquipoAllIgnoreCase(String estado, String equipo);
	
	List<Jugador>findByGrupoIgnoreCase(String liga);
	
	List<Jugador>findByAceptado(boolean aceptado);
	
	Jugador findByNombreAndApellidosAllIgnoreCase(String nombre, String apellido);
	
	Jugador findById(String id);
	
	Jugador findByEmailIgnoreCase(String email);
	
	Jugador findByDniIgnoreCase(String dni);
	
	Jugador findByDelegadoAndEquipo(boolean capitan, String idEquipo);
	
	Jugador findByDorsalAndEquipo(int dorsal,String equipo);
	
	Jugador findByNombreUsuarioIgnoreCase(String nombreUsuario);
	
	@Query(value = "{'grupo':?0, 'liga':?1}")
	List<Jugador> getRankings(String grupo, String liga, Pageable page);
	
	
	
}
