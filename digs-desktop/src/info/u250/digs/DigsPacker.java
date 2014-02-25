package info.u250.digs;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;


public class DigsPacker {

	public static void main(String[] args) {
		String input = "raw/";
		String output = "assets/data/";
		
		TexturePacker.Settings setting = new TexturePacker.Settings();
		setting.debug = false;
		setting.stripWhitespaceX = false;
		setting.stripWhitespaceY = false;
		setting.maxWidth = 2048;
		setting.maxHeight= 1024;
		TexturePacker.process(setting,input, output, "all");
		
	}

}
