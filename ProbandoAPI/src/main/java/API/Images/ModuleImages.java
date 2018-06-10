package API.Images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
@ComponentScan
@EnableAutoConfiguration
public class ModuleImages {

	@Value("${amazonProperties.endpointUrl}")
	private String url;
	@Value("${amazonProperties.accessKey}")
	private String claveAccesoS3;
	@Value("${amazonProperties.secretKey}")
	private String claveSecretaS3;
	@Value("${amazonProperties.bucketName}")
	private String nombreBucket;
	private AmazonS3 clienteAmazon;
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
			clienteAmazon.putObject(new PutObjectRequest(nombreBucket, nombreFichero, file).withCannedAcl(CannedAccessControlList.PublicRead));
			file.delete();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean eleminarFoto(String nombre){
		try {
			clienteAmazon.deleteObject(new DeleteObjectRequest(nombreBucket + "/", nombre));
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClaveAccesoS3() {
		return claveAccesoS3;
	}

	public void setClaveAccesoS3(String claveAccesoS3) {
		this.claveAccesoS3 = claveAccesoS3;
	}

	public String getClaveSecreta() {
		return claveSecretaS3;
	}

	public void setClaveSecreta(String claveSecretaS3) {
		this.claveSecretaS3 = claveSecretaS3;
	}

	public String getNombreBucket() {
		return nombreBucket;
	}

	public void setNombreBucket(String nombreBucket) {
		this.nombreBucket = nombreBucket;
	}

	@PostConstruct
	private void iniciarAmazon() {
		AWSCredentials credenciales = new BasicAWSCredentials(claveAccesoS3, claveSecretaS3);
		clienteAmazon = AmazonS3ClientBuilder.standard().withRegion("eu-west-2").withCredentials(new AWSStaticCredentialsProvider(credenciales)).build();
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
