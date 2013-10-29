package info.u250.digs.googleapi;

import java.security.MessageDigest;

public class Sha1 {
//	keytool -exportcert -alias androiddebugkey -keystore path-to-debug-or-production-keystore -list -v
//	keytool -list -v -keystore mystore.keystore
	public static void main(String args[]) {
		String str = "info.u250.dig";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			String result = getFormattedText(messageDigest.digest());
			System.out.println(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

}
