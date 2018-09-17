package API.Images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	@Autowired
	ModuleImages img;
	
	public ImageService(){}
	
	public ImageService(ModuleImages img){
		this.img = img;
	}

	public ModuleImages getImg() {
		return img;
	}

	public void setImg(ModuleImages img) {
		this.img = img;
	}

}