package API.Sancion;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import API.Acta.Acta;

public interface SancionRepository extends MongoRepository<Sancion,String>{

	List<Sancion> findAll();

	Sancion findById(String id);

	List<Sancion> findByEnVigor(boolean enVigor);

	List<Sancion> findByInicioSancion(String inicioSancion);

	Sancion findBySancionadoId(String jugadorId);

	List<Sancion> findByArbitroSdrId(String arbitroId);

	

}
