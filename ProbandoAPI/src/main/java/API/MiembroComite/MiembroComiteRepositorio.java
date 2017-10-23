package API.MiembroComite;

import java.util.List;


public interface MiembroComiteRepositorio {

	List<MiembroComite> findAll();

    MiembroComite findById(String id);

  
	
}
