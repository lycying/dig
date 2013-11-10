package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.scenes.ui.SpineActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Mv1Scene extends SceneStage{
	final SimpleMeshBackground bg ;
	final Rain[] rains;
	public Mv1Scene(){
		final TextureAtlas atlas = Engine.resource("All");
		int number = (int)(Engine.getWidth()/Rain.WIDTH);
		rains = new Rain[number];
		for(int i=0;i<number;i++){
			rains[i] = new Rain(atlas.findRegion("rain"+(Digs.RND.nextInt(4)+1)));
			rains[i].setX(Rain.WIDTH*i);
		}
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
		Engine.getSpriteBatch().begin();
		for(Rain rain:rains){
			rain.render(Engine.getDeltaTime());
		}
		Engine.getSpriteBatch().end();
		super.draw();
	}
	private class Rain extends AdvanceSprite{
		public static final float WIDTH = 15;
		float speed = 0;
		public void setup(){
			speed = Digs.RND.nextFloat()*1000+500;
			this.setY(Engine.getHeight());
		}
		public Rain(TextureRegion texture) {
			super(texture);
			this.setup();
			this.setY(this.getY()+Digs.RND.nextFloat()*200);
		}
		@Override
		public void render(float delta) {
			super.render(delta);
			if(this.getY()<-this.getHeight()){
				this.setup();
			}
			this.setY(this.getY()-speed*delta);
		}
		
	}
}
