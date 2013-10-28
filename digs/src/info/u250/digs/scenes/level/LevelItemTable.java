package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.LevelScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelItemTable extends  Group{
	private int pack;
	private int level;
	public LevelItemTable(final LevelScene levelScene,final int pack,final int level ,String levelName){
		this.pack = pack;
		this.level = level;
		
		this.setSize(680, 80);
		TextureAtlas texs = Engine.resource("Texs");
		TextureAtlas atlas = Engine.resource("All");
		Image bg = new Image( atlas.createPatch("level-item-bg"));
		bg.setColor(new Color(1,1,1,0.4f));
		bg.setSize(this.getWidth(), this.getHeight());
		Label title = new Label(level+":"+levelName,new LabelStyle(Engine.resource("BigFont",BitmapFont.class),Color.WHITE));
		title.setPosition(100, 20);
		Image icon = new Image(texs.getRegions().get(level));
		icon.setPosition(5.5f, 2.5f);
		this.addActor(bg);
		this.addActor(icon);
		this.addActor(title);
		
		Image menu_play = new Image(atlas.findRegion("menu_play"));
		menu_play.setPosition(this.getWidth()-menu_play.getWidth()-20,(this.getHeight()-menu_play.getHeight())/2);
		this.addActor(menu_play);
		
		menu_play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				levelScene.startLevel(pack,level);
				super.clicked(event, x, y);
			}
		});
	} 
	public void refresh(){
		String key = "au"+pack+level;
		Engine.getPreferences().getString(key);
	}
}
