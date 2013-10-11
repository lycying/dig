package info.u250.digs;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class DigsPacker {

	public static void main(String[] args) {
		String input = "raw/";
		String output = "assets/data/";
		
		TexturePacker2.process(input, output, "all");
		
		input = "raw_texs/";
		TexturePacker2.process(input, output, "texs");
	}

}
