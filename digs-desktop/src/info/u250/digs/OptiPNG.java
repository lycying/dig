package info.u250.digs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OptiPNG {
	public static void main(String[] args) throws Exception {
		String s;
		Process process = Runtime.getRuntime().exec("OptiPNG/optipng.exe  assets/data/all.png");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		while((s=bufferedReader.readLine()) != null){
			System.out.println(s);
		}
		process.waitFor();
	}

}
