package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class ShopWheel extends Group {

	protected final Image wheel ;
	public ShopWheel(){
		TextureAtlas atlas = Engine.resource("All");
		wheel = new Image(atlas.findRegion("wheel"));
		wheel.setOrigin(wheel.getWidth()/2, wheel.getHeight()/2);
		this.setSize(wheel.getWidth(), wheel.getHeight());
		this.addActor(wheel);
		this.setPosition(840, 410);
	}
}
