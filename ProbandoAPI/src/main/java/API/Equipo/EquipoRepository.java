package API.Equipo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
public interface EquipoRepository extends MongoRepository<Equipo, String> {
	
	List<Equipo>findByLigaIgnoreCase(String liga);
	
	Equipo findByPosicion(int posicion);
	
	Equipo findByNombreIgnoreCase(String nombre);
	
	Equipo findByLigaAndNombreAllIgnoreCase(String liga, String nombre);
	
	Equipo findById (String id);
	
	List<Equipo> findByAceptado(boolean aceptado);
	
	@Query(value = "{'liga':?0}", fields = "{'nombre':1, 'goles':1, 'puntos':1, 'golesEncajados':1, 'partidosGanados':1, 'partidosPerdidos':1, 'partidosEmpatados': 1, 'partidosJugados':1, 'imagenEquipo':1 })")
	List<Equipo> findCustomClasificacion(String liga, Sort sort);
	
	@Query(value = "{'liga':?0, 'aceptado':?1}", fields = "{'id':1, 'nombre':1, 'imagenEquipo':1}")
	List<Equipo>findCustomEquiposLiga(String liga, boolean aceptado);
}
