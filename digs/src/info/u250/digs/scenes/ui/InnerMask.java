package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class InnerMask extends Group{
	final Image top,bottom,left,right;
	final float width,height;
	public InnerMask(float width,float height){
		this.width = width;
		this.height = height;
		final TextureAtlas atlas = Engine.resource("All");
		final Color color = new Color(0,0,0,0.8f);
		top = new Image(atlas.findRegion("color"));
		bottom  = new Image(atlas.findRegion("color"));
		left  = new Image(atlas.findRegion("color"));
		right  = new Image(atlas.findRegion("color"));
		top.setColor(color);
		bottom.setColor(color);
		left.setColor(color);
		right.setColor(color);
		this.addActor(top);
		this.addActor(bottom);
		this.addActor(left);
		this.addActor(right);
		this.setSize(width, height);
//		this.setTouchable(Touchable.childrenOnly);
	}
	public void setRect(Rectangle rect){
		top.setSize(width, height-rect.y-rect.height);
		bottom.setSize(width, rect.y);
		left.setSize(rect.x, rect.height);
		right.setSize(width-rect.x-rect.width, rect.height);
		
		top.setY(rect.y+rect.height);
		left.setY(rect.y);
		right.setPosition(rect.x+rect.width, rect.y);
	}
}
