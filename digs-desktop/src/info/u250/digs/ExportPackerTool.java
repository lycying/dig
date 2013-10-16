package info.u250.digs;

import info.u250.c2d.engine.tools.CrackTextureAtlasTool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ExportPackerTool extends ApplicationAdapter{
	

	@Override
	public void create() {
		try {
			CrackTextureAtlasTool.crack("C:/Users/Administrator/Desktop/com.snowforge.flyingbob/assets/data/images/xhdpi/menu.atlas","C:/Users/Administrator/Desktop/test/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
		super.create();
	}
	public static void main(String args[]){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 10;
		config.height= 10;
		new LwjglApplication(new ExportPackerTool(),config);
	}
}
