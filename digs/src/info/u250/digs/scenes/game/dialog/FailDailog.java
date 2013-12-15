package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.ui.FireDeco;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FailDailog extends Group {
	
	public FailDailog(final GameScene game){		
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		this.setSize(Engine.getWidth(), Engine.getHeight());
		Image mask = new Image(atlas.findRegion("color"));
		mask.setSize(this.getWidth(), this.getHeight());
		mask.setColor(new Color(0,0,0,0.7f));
		this.addActor(mask);
		final Table table = new Table();
		table.add(new FireDeco());
		final Image lblPause = new Image(atlas.findRegion("lbl-fail"));
		table.add(lblPause).row();
		table.pack();
		table.setPosition(200,Engine.getHeight()/2-table.getHeight()/2);
		this.addActor(table);
		Label lbl = new Label("Touch screen to continue", new Label.LabelStyle(font,Color.WHITE));
		lbl.setPosition(460, 220);
		this.addActor(lbl);
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.drive.setToLevelScene();
				super.clicked(event, x, y);
			}
		});
		
		final Image restart = new Image(atlas.findRegion("restart"));
		this.addActor(restart);
		restart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.restart();
				super.clicked(event, x, y);
			}
		});
		restart.setPosition(Engine.getWidth()-restart.getWidth()+15, 220);
		this.addActor(restart);
	}
	public void show(){
		this.getColor().a = 0;
		this.addAction(Actions.fadeIn(1f));
		Digs.getAdmob().show();
	}
	public void close(){
		this.remove();
		Digs.getAdmob().hide();
	}
}
