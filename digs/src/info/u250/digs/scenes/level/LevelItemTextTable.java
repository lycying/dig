package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class LevelItemTextTable extends  Group{
	
	public LevelItemTextTable(String text){
		this.setSize(680, 60);
		TextureAtlas atlas = Engine.resource("All");
		Image bg = new Image( atlas.createPatch("level-item-bg-2"));
		bg.setColor(new Color(1,1,1,1));
		bg.setSize(this.getWidth(), this.getHeight());
		Label title = new Label(text,new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.YELLOW));
		title.setPosition(this.getWidth()/2-title.getWidth()/2, 10);
		this.addActor(bg);
		this.addActor(title);

	} 
}
