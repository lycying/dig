package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class KillRay extends Group{
float raduis;
	public KillRay(float x,float y,float raduis,Color c){
		this.raduis = raduis;
		final TextureAtlas atlas = Engine.resource("All",TextureAtlas.class);
		final Image  bg = new Image(atlas.findRegion("kill"));
		bg.setSize(2*raduis, 2*raduis);
		final Image  fg = new Image(atlas.findRegion("kill-dot"));
		this.addActor(bg);
		fg.setPosition((bg.getWidth()-fg.getWidth())/2, (bg.getHeight()-fg.getHeight())/2);
		this.addActor(fg);
		this.setSize(2*raduis, 2*raduis);
		this.setOrigin(raduis, raduis);
		this.addAction(Actions.forever(Actions.rotateBy(360*(Digs.RND.nextBoolean()?1:-1),1+2*Digs.RND.nextFloat())));
		this.setPosition(x, y);
		bg.setColor(c);
	}
	public boolean overlaps(float x,float y){
		return Math.pow(this.getX()+raduis-x,2)+Math.pow(this.getY()+raduis-y,2)<raduis*raduis;
	}
	
	
	
}