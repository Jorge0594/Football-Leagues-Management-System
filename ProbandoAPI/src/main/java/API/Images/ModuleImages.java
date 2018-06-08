package API.Images;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ModuleImages {

	private String fileName;

	public ModuleImages() {

	}

	public ModuleImages(String fileName) {
		this.fileName = fileName;
	}

	public void handleFileDownload(String fName, HttpServletResponse res) throws FileNotFoundException, IOException {

		String filePath = "src/main/resources/static/images" + fName + ".jpg";
		File file = new File(filePath);

		if (file.exists()) {
			res.setContentType("image/jpg");
			res.setContentLength(new Long(file.length()).intValue());
			FileCopyUtils.copy(new FileInputStream(file), res.getOutputStream());
		} else {
			res.sendError(404, "File" + fileName + "(" + file.getAbsolutePath() + ") does not exist");
		}

	}
	public boolean cambiarFoto(String id, MultipartFile file) {
		String FILES_FOLDER = "src\\main\\resources\\static\\images";
		fileName = "profile" + id + ".jpg";

		if (!file.isEmpty()) {
			try {

				File filesFolder = new File(FILES_FOLDER);
				if (!filesFolder.exists()) {
					filesFolder.mkdirs();
				}

				File uploadedFile = new File(filesFolder.getAbsolutePath(), fileName);
				uploadedFile.createNewFile();
				FileOutputStream writeFile = new FileOutputStream(uploadedFile);
				
				writeFile.write(file.getBytes());
				writeFile.close();
				
				BufferedImage bImage = ImageIO.read(uploadedFile);
				Image imageScaled;
				if(bImage.getWidth()>=800 && bImage.getHeight()>=800){
					imageScaled = bImage.getScaledInstance((int)(bImage.getWidth()*0.2), (int)(bImage.getHeight()*0.2), BufferedImage.SCALE_SMOOTH);//render de la imagen
				}else{
					imageScaled = bImage.getScaledInstance((int)(bImage.getWidth()*0.7), (int)(bImage.getHeight()*0.7), BufferedImage.SCALE_SMOOTH);//render de la imagen
				}
				
				BufferedImage bImageResize = toBufferedImage(imageScaled);
				
				ImageIO.write(bImageResize, "jpg", uploadedFile);

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	private static BufferedImage toBufferedImage(Image image){
		if(image instanceof BufferedImage){//Si la imagen recibida ya es BufferedImage
			return (BufferedImage)image;
		}
		
		BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bImage.getGraphics().drawImage(image, 0, 0, null);
		
		return bImage;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
