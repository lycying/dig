package info.u250.digs.gdx_encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class JavaDES implements IDES {
	private static byte[] iv = { 1, 20, 127, 40, 50, 6, 89, 80 };

	@Override
	public byte[] encryptDES(byte[] encryptString, String encryptKey) {
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptString);
			return encryptedData;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public byte[] decryptDES(byte[] b64d, String decryptKey) {
		byte[] decryptedData = null;
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			decryptedData = cipher.doFinal(b64d);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return decryptedData;
	}
	
	public static void main(String args[]){
		JavaDES des = new JavaDES();
		String str = "jb201022323e34342";
		String encode = Base64.encode(des.encryptDES(str.getBytes(),"wahaha12"));
		System.out.println(encode);
		System.out.println(new String(des.decryptDES(Base64.decode(encode),"wahaha12")).equals(str));
	}
}
