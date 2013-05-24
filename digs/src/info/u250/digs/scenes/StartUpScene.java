package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StartUpScene extends SceneStage{
	TextureAtlas atlas = null;
	public StartUpScene(){
		atlas = Engine.resource("All");
		
		Image btnStartUp = new Image(new TextureRegionDrawable(atlas.findRegion("play")));
		this.addActor(btnStartUp);
		btnStartUp.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.setMainScene(new NpcListScene());
				super.clicked(event, x, y);
			}
		});
	}
}
