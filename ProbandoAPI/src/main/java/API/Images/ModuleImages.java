package API.Images;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

	// private String filePath="src/main/resources/static/planImages/";
	private String fileName;

	public ModuleImages() {

	}

	public ModuleImages(String fileName) {
		this.fileName = fileName;
		// this.filePath=filePath;
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
				/*FileOutputStream writeFile = new FileOutputStream(uploadedFile);
				
				BufferedImage bImagen = ImageIO.read(uploadedFile);
				BufferedImage imgDimensionada = new BufferedImage(200, 200, bImagen.getType());
				
				ImageIO.write(imgDimensionada, "jpg", uploadedFile);*/
				file.transferTo(uploadedFile);

			} catch (Exception e) {

				return false;
			}
		}
		return true;
	}

	/*
	 * public String getFilePath() { return filePath; } public void
	 * setFilePath(String filePath) { this.filePath = filePath; }
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
