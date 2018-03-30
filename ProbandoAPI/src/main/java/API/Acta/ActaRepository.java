package API.Acta;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActaRepository extends MongoRepository<Acta,String>{
	Acta findById(String id);
	Acta findByIdPartidoIgnoreCase(String idPartido);
	List<Acta> findByEquipoLocalId(ObjectId equipoLocal);
	List<Acta> findByEquipoVisitanteId(ObjectId equipoVisitante);
	List<Acta> findByArbitroId(ObjectId arbitro);
	List<Acta> findByFecha(String fecha);
	List<Acta> findByAceptadaFalse();
	List<Acta> findByAceptadaTrue();	
}
