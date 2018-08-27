package API.Acta;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import API.Grupo.Grupo;

@Repository
public interface ActaRepository extends MongoRepository<Acta, String> {
	Acta findById(String id);

	Acta findByIdPartidoIgnoreCase(String idPartido);

	List<Acta> findByIdArbitro(String arbitro);

	List<Acta> findByFecha(String fecha);

	List<Acta> findByAceptadaFalse();

	List<Acta> findByAceptadaTrue();
	
	List<Acta> findByAceptadaFalseAndGrupoIdGrupo(String idGrupo);
	
	List<Acta> findByAceptadaTrueAndGrupoIdGrupo(String idGrupo);
	
	@Query(value = "{'grupo.id':?0}", fields = "{'idsPorterosLocal':1, 'idsPorterosVisitante':1, 'golesLocal':1, 'golesVisitante': 1}")
	List<Acta>findCustomByGrupoId(String idGrupo);
	
	@Query(value = "{'$or':[{'idsPorterosLocal':{'$all': [?0]}}, {'idsPorterosVisitante':{'$all': [?0]}}]}", count = true)
	long getNumeroPartidosPortero(String idPortero);
	
	@Query(value = "{'idsPorterosLocal':{'$all': [?0]}}", fields = "{'golesVisitante':1}")
	List<Acta>findCustomEncajadosPorterolLocal(String idPortero);
	
	@Query(value = "{'idsPorterosVisitante':{'$all': [?0]}}", fields = "{'golesLocal':1}")
	List<Acta>findCustomEncajadosPorterolVisitante(String idPortero);
	
}
