package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.svg.SVGParse;
import info.u250.svg.elements.SVGRootElement;
import info.u250.svg.glutils.SVGTextureData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SvgTest extends Engine{
	public static void main(String args[]){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height= 540;
		config.useGL20 = true;
		new LwjglApplication(new SvgTest(),config);
	}
	public static class TestScene extends SceneStage {
		public TestScene() {
			final Texture texture ;
			SVGRootElement svgFile  = new SVGRootElement();
			svgFile.format = 4;
			svgFile.width = 960;
			svgFile.height = 540;

			svgFile.min_x = 0;
			svgFile.min_y = 0;
			svgFile.max_x = 0;
			svgFile.max_y = 0;

			svgFile.scale = 1f;

			SVGParse parse = new SVGParse (Gdx.files.internal ("vg/1.svg"));

			parse.parse (svgFile);

			texture = new Texture (new SVGTextureData (svgFile));
			
			this.addActor(new Image(texture));
		}
		@Override
		public void draw() {
//			Gdx.gl.glClearColor(1, 1, 1, 0.5f);
			super.draw();
		}
	}
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineDrive() {
			
			@Override
			public EngineOptions onSetupEngine() {
				EngineOptions opt = new EngineOptions(new String[]{}, 960, 540);
				return opt;
			}
			
			@Override
			public void onResourcesRegister(AliasResourceManager<String> reg) {
			}
			
			@Override
			public void onLoadedResourcesCompleted() {		
				Engine.setMainScene(new TestScene());
			}
			
			@Override
			public void dispose() {
				
			}
		};
	}
}
