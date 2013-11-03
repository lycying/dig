package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.LevelScene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GuideItem extends  Image{
	public GuideItem(final LevelScene levelScene,final int pack,final int level ,String levelName){
		super(Engine.resource("All",TextureAtlas.class).findRegion("tour"+(level+1)));
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				levelScene.startLevel(pack,level);
				super.clicked(event, x, y);
			}
		});
	} 
}
