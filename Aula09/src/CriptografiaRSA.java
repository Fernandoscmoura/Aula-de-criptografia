import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CriptografiaRSA {
	private static KeyPair gerarChavesAssimetricas() throws Exception {
		KeyPairGenerator objGerador = KeyPairGenerator.getInstance("RSA");
		objGerador.initialize(4096);
		return objGerador.generateKeyPair();
	}
	
	private static String encriptarRSA(KeyPair parDeChaves, byte[] chaveTemporaria) throws Exception {
		Cipher objCifra = Cipher.getInstance("RSA");
		objCifra.init(Cipher.ENCRYPT_MODE, parDeChaves.getPublic());
		return Base64.getEncoder().encodeToString(objCifra.doFinal(chaveTemporaria));
	}
	
	private static byte[] decriptarRSA(KeyPair parDeChaves, String criptograma) throws Exception {
		Cipher objCifra = Cipher.getInstance("RSA");
		objCifra.init(Cipher.DECRYPT_MODE, parDeChaves.getPrivate());
		return objCifra.doFinal(Base64.getDecoder().decode(criptograma));
	}
	
	private static String encriptarAES(String texto,byte[] chave) throws Exception{
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		objCifra.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"), new IvParameterSpec("Textocom16caract".getBytes()));
		byte[] vetor = objCifra.doFinal(texto.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(vetor);
	}
	
	
	private static String decriptarAES(String criptograma ,byte[] chave) throws Exception{
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		objCifra.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"), new IvParameterSpec("Textocom16caract".getBytes()));
		byte[] vetor = objCifra.doFinal(Base64.getDecoder().decode(criptograma));
		return new String(vetor, "UTF-8");
	}
	
	private static byte[] calcularHash(byte[] chaveTemporaria) throws Exception {
		MessageDigest objHash = MessageDigest.getInstance("SHA-256");
		return objHash.digest(chaveTemporaria);
	}
	
	
	public static void main(String[] args) {	
		//Declaração de variavies
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		KeyPair parDeChaves = null;
		String texto = "";
		String criptograma = "";
		String chave = "";
		
		//Processamento
		try {
			System.out.print("Digite o texto: ");
			texto = leitor.readLine();
			
			byte[] chaveTemporaria = new byte[100];
			for (int i = 0 ; i < chaveTemporaria.length ; i++ ) {
				chaveTemporaria[i] = ((byte) (256 * Math.random()));
			}
			
			parDeChaves = gerarChavesAssimetricas();
			
			chave = encriptarRSA(parDeChaves, chaveTemporaria);
			System.out.println("Chave: " + chave);
			
			criptograma = encriptarAES(texto, calcularHash(chaveTemporaria));
			System.out.println("Criptograma: " + criptograma);
			
			
			chaveTemporaria = decriptarRSA(parDeChaves, chave);
			System.out.println(decriptarAES(criptograma, calcularHash(chaveTemporaria)));
		} catch (Exception erro) {
			System.out.println(erro);
		}
	}
}

