package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.LevelScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LevelItemTable extends  Table{
	
	public LevelItemTable(final LevelScene levelScene,final int level ,String levelName){
		TextureAtlas atlas = Engine.resource("All");
		this.setBackground(new NinePatchDrawable(atlas.createPatch("ui-npc-item")));
		
		Label levelLbl = new Label("Level:"+level,new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.RED));
		Label title = new Label(levelName,new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.WHITE));
		title.setWrap(true);
		this.add(levelLbl).minWidth(740);
		this.row();
		this.add(title).minWidth(740);
		this.row();
		this.pack();
		this.setWidth(860);
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				levelScene.startLevel(level);
				super.clicked(event, x, y);
			}
		});
	} 
}
