package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.scenes.ui.SpineActor;
import info.u250.svg.SVGParse;
import info.u250.svg.elements.SVGRootElement;
import info.u250.svg.glutils.SVGTextureData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Mv1Scene extends SceneStage{
	final SimpleMeshBackground bg ;
	
	public Mv1Scene(){
		final TextureAtlas atlas = Engine.resource("All");
	
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
		SVGParse parse = new SVGParse (Gdx.files.internal ("vg/obi_Dragon_s_head.svg"));
		parse.parse (svgFile);
		texture = new Texture (new SVGTextureData (svgFile));
		final Image image = new Image(texture);
		image.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0,20,1),
				Actions.moveBy(0,-20,1)
				)));
		this.addActor(image);
		
		bg = new SimpleMeshBackground(Color.GRAY,Color.BLACK);
		final SpineActor wmr = new SpineActor("null", atlas,"idle",1);
		wmr.setColor(Color.BLACK);
		wmr.setX(240);
		this.addActor(wmr);
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("grass")), new Vector2(1,0), new Vector2(0,1000), new Vector2(0,0)));
		this.addActor(pbg);
		
	}
	@Override
	public void draw() {
		bg.render(0);
		super.draw();
	}
}
