package info.u250.digs;

import java.io.File;

import info.u250.digs.gdx_encrypt.Des;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.files.FileHandle;

public class QvgEncryptProcesser {

	public static void main(String[] args) throws Exception {
		new LwjglApplication(new ApplicationAdapter() {
			
			byte[] bytes_encode(byte[] bytes,int seek) {
				int length = bytes.length;
				for (int i = 0; i < seek; i++) {
					byte b = bytes[i];
					bytes[i] = bytes[length - i - 1];
					bytes[length - i - 1] = b;
				}
				return bytes;
			}
			void processFile(String fileName, String output) {
				System.out.println("Input:" + fileName + "\nOutput:" + output + "\n");
				FileHandle file = Gdx.files.absolute(fileName);
				int seek = file.readBytes().length%100+100;;//random confuse
				FileHandle fileOutput = Gdx.files.absolute(output);
				fileOutput.writeBytes(bytes_encode(file.readBytes(),seek), false);
			}
			@Override
			public void create() {
				super.create();
				String inputdir = "qvg/";
				String outputdir = "assets/qvg/";
				File dir = new File(inputdir);
				if(dir.isDirectory()){
					for(String s:dir.list()){
						String fileName = s.split("\\.")[0];
						String name = Des.encryptDES(fileName.getBytes(), "fuckyoua").replace("/", "@@@@");
						processFile(inputdir + s, outputdir + name +".dc");
					}
				}
				
				System.exit(-1);
			}
		}, "Encry", 100, 100, false);
	}
}
