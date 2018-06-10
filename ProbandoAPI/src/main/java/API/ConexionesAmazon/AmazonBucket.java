package API.ConexionesAmazon;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonBucket {
	
	private AmazonS3 clienteAmazon;
	@Value("${amazonProperties.endpointUrl}")
	private String url;
	@Value("${amazonProperties.accessKey}")
	private String claveAccesoS3;
	@Value("${amazonProperties.secretKey}")
	private String claveSecretaS3;
	@Value("${amazonProperties.bucketName}")
	private String nombreBucket;
	
	public void añadirFichero(String nombreFichero, File file){
		clienteAmazon.putObject(new PutObjectRequest(nombreBucket, nombreFichero, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}
	
	public void eliminarFichero(String nombreFichero){
		clienteAmazon.deleteObject(new DeleteObjectRequest(nombreBucket + "/", nombreFichero));
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
	public String getClaveSecretaS3() {
		return claveSecretaS3;
	}
	public void setClaveSecretaS3(String claveSecretaS3) {
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

}
