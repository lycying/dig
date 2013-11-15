package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Mv1Scene extends SceneStage{
	public Mv1Scene(final LevelScene lvlSce){
		final BitmapFont passFont = Engine.resource("PassFont");
		final TextureAtlas atlas = Engine.resource("All");
		final Label test = new Label("Demo,Nothing at here...",new LabelStyle(passFont,Color.WHITE));
		test.setPosition(Engine.getWidth()/2-test.getWidth()/2, 0);
		this.addActor(new Image(atlas.findRegion("mv1-1")));
		Image npc = new Image(atlas.findRegion("mv1-2"));
		npc.setPosition(440, 160);
		this.addActor(npc);
		this.addActor(test);
		npc.addAction(Actions.forever(
				Actions.sequence(
						Actions.moveBy(10, 0,0.02f),
						Actions.moveBy(-10, 0,0.02f),
						Actions.moveBy(0, 10,0.02f),
						Actions.moveBy(0, -10,0.02f)
				)
		));
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.setMainScene(lvlSce);
				super.clicked(event, x, y);
			}
		});
	}
}
