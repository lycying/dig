package info.u250.digs.gdx_encrypt;


public class Des {
	public static IDES I_des = null;
	static{
		I_des = new JavaDES();
	}
	public static String encryptDES(byte[] encryptString, String encryptKey){
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		String data="";
		try {
			byte[] encryptedData = I_des.encryptDES(encryptString, encryptKey);
			data=Base64.encode(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static byte[] decryptDES(String decryptString, String decryptKey) {
		
		byte[] decryptedData = null;
		try {
			byte[] byteMi = Base64.decode(decryptString);
			decryptedData = I_des.decryptDES(byteMi, decryptKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return decryptedData;
	}
}
