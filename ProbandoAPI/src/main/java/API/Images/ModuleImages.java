package API.Images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import API.ConexionesAmazon.AmazonBucket;

@Component
@EnableAutoConfiguration
public class ModuleImages {

	@Autowired
	private AmazonBucket amazonBucket;
	private String nombreFichero;

	public ModuleImages() {

	}

	public ModuleImages(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/*
	 * public boolean cambiarFoto(String id, MultipartFile file) { final String
	 * FILES_FOLDER = "src\\main\\resources\\static\\images"; nombreFichero =
	 * "profile" + id + ".jpg";
	 * 
	 * if (!file.isEmpty()) { try {
	 * 
	 * File filesFolder = new File(FILES_FOLDER); if (!filesFolder.exists()) {
	 * filesFolder.mkdirs(); }
	 * 
	 * File uploadedFile = new File(filesFolder.getAbsolutePath(),
	 * nombreFichero); uploadedFile.createNewFile(); FileOutputStream writeFile
	 * = new FileOutputStream(uploadedFile);
	 * 
	 * writeFile.write(file.getBytes()); writeFile.close();
	 * 
	 * BufferedImage bImage = ImageIO.read(uploadedFile); Image imageScaled; if
	 * (bImage.getWidth() >= 800 && bImage.getHeight() >= 800) { imageScaled =
	 * bImage.getScaledInstance((int) (bImage.getWidth() * 0.2), (int)
	 * (bImage.getHeight() * 0.2), BufferedImage.SCALE_SMOOTH); } else {
	 * imageScaled = bImage.getScaledInstance((int) (bImage.getWidth() * 0.7),
	 * (int) (bImage.getHeight() * 0.7), BufferedImage.SCALE_SMOOTH); }
	 * 
	 * BufferedImage bImageResize = toBufferedImage(imageScaled);
	 * 
	 * ImageIO.write(bImageResize, "jpg", uploadedFile);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return false; } } return
	 * true; }
	 */

	public boolean cambiarFoto(String id, MultipartFile multipartFile) {
		nombreFichero = "profile" + id + ".jpg";
		try {
			File file = conversorFicheros(multipartFile);
			redimensionadorImagen(file);
			amazonBucket.añadirFichero(nombreFichero, file);
			file.delete();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean eleminarFoto(String nombre){
		try {
			amazonBucket.eliminarFichero(nombre);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	private BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bImage.getGraphics().drawImage(image, 0, 0, null);

		return bImage;
	}

	private File conversorFicheros(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(file)){
			
			fos.write(multipartFile.getBytes());
			fos.close();

			return file;
		} catch (IOException e) {
			throw new IOException("No se ha podido realziar la conversion de MultipartFile a File: ", e);
		}
	}

	private void redimensionadorImagen(File file) throws IOException {
		try {
			BufferedImage bImage = ImageIO.read(file);

			Image imageScaled;
			if (bImage.getWidth() >= 800 && bImage.getHeight() >= 800) {
				imageScaled = bImage.getScaledInstance((int) (bImage.getWidth() * 0.2), (int) (bImage.getHeight() * 0.2), BufferedImage.SCALE_SMOOTH);
			} else {
				imageScaled = bImage.getScaledInstance((int) (bImage.getWidth() * 0.7), (int) (bImage.getHeight() * 0.7), BufferedImage.SCALE_SMOOTH);
			}

			BufferedImage bImageResize = toBufferedImage(imageScaled);

			ImageIO.write(bImageResize, "jpg", file);
		} catch (IOException e) {
			throw new IOException("No se ha podido redimensionar la imagen: ", e);
		}
	}

}
