package API.Acta;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActaRepository extends MongoRepository<Acta,String>{
	Acta findById(String id);
	Acta findByIdPartidoIgnoreCase(String idPartido);
	List<Acta> findByEquipoLocalIgnoreCase (String equipoLocal);
	List<Acta> findByEquipoVisitanteIgnoreCase (String equipoVisitante);
}
