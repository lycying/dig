package info.u250.digs.gdx_encrypt;


public abstract interface IDES {
	public  byte[] encryptDES(byte[] encryptString, String encryptKey);
	public  byte[] decryptDES(byte[] b64d, String decryptKey);
}
