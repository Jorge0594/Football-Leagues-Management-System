package API.Acta;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActaRepository extends MongoRepository<Acta, String> {
	Acta findById(String id);

	Acta findByIdPartidoIgnoreCase(String idPartido);

	List<Acta> findByIdArbitro(String arbitro);

	List<Acta> findByFecha(String fecha);

	List<Acta> findByAceptadaFalse();

	List<Acta> findByAceptadaTrue();
	
	@Query(value = "{'grupo.id':?0}", fields = "{'idsPorterosLocal':1, 'idsPorterosVisitante':1, 'golesLocal':1, 'golesVisitante': 1}")
	List<Acta>findCustomByGrupoId(String idGrupo);
	
}
