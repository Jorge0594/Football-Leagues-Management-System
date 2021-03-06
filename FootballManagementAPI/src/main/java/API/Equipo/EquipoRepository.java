package API.Equipo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
public interface EquipoRepository extends MongoRepository<Equipo, String> {
	
	List<Equipo>findByGrupoIdGrupo(String grupo);
	
	List<Equipo>findByGrupoNombreIgnoreCase(String nombreGrupo);
	
	List<Equipo>findByAceptado(boolean aceptado);
	
	Equipo findByPosicion(int posicion);
	
	Equipo findByNombreIgnoreCase(String nombre);
	
	Equipo findByGrupoAndNombreAllIgnoreCase(String grupo, String nombre);
	
	Equipo findById (String id);
	

	@Query(value = "{'grupo.idGrupo':?0, 'aceptado': true}", fields = "{'nombre':1, 'goles':1, 'liga':1, 'grupo':1, 'puntos':1, 'golesEncajados':1, 'partidosGanados':1, 'partidosPerdidos':1, 'partidosEmpatados': 1, 'partidosJugados':1, 'imagenEquipo':1 })")
	List<Equipo> findCustomClasificacion(String grupo, Sort sort);
	
	@Query(value = "{'grupo.idGrupo':?0, 'aceptado':?1}", fields = "{'id':1, 'nombre':1, 'imagenEquipo':1}")
	List<Equipo>findCustomEquiposGrupo(String grupo, boolean aceptado);
	
	@Query(value = "{'id': ?0}", fields = "{'partidosJugados': 1}")
	Equipo getPartidosJugadosEquipo(String id);
}
