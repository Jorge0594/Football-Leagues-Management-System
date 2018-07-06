package API.MongoBulk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoBulk<T> {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insertarBloque(List<T>documentos, String nombreColeccion) throws Exception{
		try {
			BulkOperations bulk = mongoTemplate.bulkOps(BulkMode.UNORDERED, nombreColeccion);
			bulk.insert(documentos);
			
			bulk.execute();
		} catch (Exception e) {
			throw new RuntimeException("Error while trying to insert a batch documents", e);
		}
		
	}
}
