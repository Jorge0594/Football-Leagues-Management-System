package API.Sancion;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Param;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import API.Acta.Acta;

public interface SancionRepository extends MongoRepository<Sancion,String>{

	List<Sancion> findAll();

	Sancion findById(String id);
	
	Sancion findByIdAndSancionadoId(String idSancion, String sancionadoId);

	List<Sancion> findByInicioSancion(String inicioSancion);

	List<Sancion> findBySancionadoId(String sancionadoId);

	List<Sancion> findByArbitroSdrId(String arbitroId);
	
	List<Sancion>findBySancionadoIdAndEnVigor(String sancionadoId, boolean enVigor);
	
	 	/*@Query("SELECT * FROM Sancion s WHERE s.sancionadoId in (idJugadores)")
	    public List<Sancion> findBySancionadoIdAndEnVigorTrue(List<String> idJugadores);*/
	 	
	List<Sancion>findBySancionadoIdAndEnVigorTrue(List<String> jugadoresId);
	
	List<Sancion>findByEnVigorTrue();
}
