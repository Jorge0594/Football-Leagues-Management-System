package API.MongoBulk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MongoBulk {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insertarBloque(List<? extends Object>documentos, String nombreColeccion) throws RuntimeException {
		try {
			
			BulkOperations bulk = mongoTemplate.bulkOps(BulkMode.UNORDERED, nombreColeccion);
			bulk.insert(documentos);
			
			bulk.execute();
		} catch (Exception e) {
			throw new RuntimeException("Error while trying to insert a batch documents", e);
		}
		
	}
	
	public void eliminarBloque(Query query, String nombreColeccion) throws RuntimeException {
		try {
			
			BulkOperations bulk = mongoTemplate.bulkOps(BulkMode.UNORDERED, nombreColeccion);
			bulk.remove(query);
			
			bulk.execute();	
		} catch (Exception e) {
			throw new RuntimeException("Cannot remove the documents: ", e);
		}
		
		
	}
	
	public void modificarBloque(Query query, Update update, String nombreColeccion) throws RuntimeException {
		try { 
			
			BulkOperations bulk = mongoTemplate.bulkOps(BulkMode.UNORDERED, nombreColeccion);
			bulk.updateMulti(query, update);
			
			bulk.execute();
		} catch (Exception e){
			throw new RuntimeException("Cannot modify the documents: ", e);
		}
	}
}
