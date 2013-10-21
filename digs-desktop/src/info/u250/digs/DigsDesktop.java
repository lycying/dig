package info.u250.digs;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DigsDesktop {

	
	public static void main(String[] args) {
		Digs game = new Digs();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)Engine.getWidth();
		config.height = (int)Engine.getHeight();
//		config.width = 10;
//		config.height =10;
		config.useGL20 = true;
		
		
		new LwjglApplication(game,config);

	}

}
