package info.u250.digs.gdx_encrypt;

public class Wahaha {
	public final static String wahaha(String file){
		return "qvg/"+Des.encryptDES(file.getBytes(), "fuckyoua").replace("/", "@@@@");
	}
}
