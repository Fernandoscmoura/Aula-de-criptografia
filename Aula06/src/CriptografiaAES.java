import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CriptografiaAES {
	public static void main(String[] args) {
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		
		String texto = "";
		String chave = "";
		String criptograma = "";
		
		//Processamento
		try {
			System.out.print("Digite o texto: ");
			texto = leitor.readLine();
			
			System.out.print("Digite a chave: ");
			chave = leitor.readLine();
			
			criptograma = encriptar(texto, chave);
			System.out.println(criptograma);
			
			System.out.println(decriptar(criptograma, chave));
		} catch (Exception erro) {
			System.out.println("DEU MERDA !!! " + erro);
		}
		
	}
	
	private static String encriptar(String texto, String chave) throws Exception{
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		objCifra.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave.getBytes("UTF-8"), "AES"),
										   new IvParameterSpec("FernandoDeSouza1".getBytes()));
		byte[] vetor = objCifra.doFinal(texto.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(vetor);	
		}
	
	private static String decriptar(String criptograma, String chave) throws Exception{
		Cipher objCifra = Cipher.getInstance("TripleDES");
		objCifra.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave.getBytes("UTF-8"), "AES"),
										   new IvParameterSpec("FernandoDeSouza1".getBytes()));
		byte[] vetor = objCifra.doFinal(Base64.getDecoder().decode(criptograma));
		return new String(vetor, "UTF-8");	
		}
}
