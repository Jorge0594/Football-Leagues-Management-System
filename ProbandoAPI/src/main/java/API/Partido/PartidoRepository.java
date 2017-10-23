package API.Partido;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartidoRepository extends MongoRepository<Partido,String>{
	public Partido findById(String id);
	public List<Partido> findByLiga(String liga);
	public List<Partido> findByJornada(String jornada);
	public List<Partido> findByEquipoLocal(String equipoLocal);
	public List<Partido> findByEquipoVisitante (String equipoVisitante);
	public List<Partido> findByArbitro (String arbitro);
	
}
