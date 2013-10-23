package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseDialog extends Group {
	
	Button btnBack ;
	Button btnExit ;
	Group mainGroup ;
	Label lblGold;
	Label lblWhiteGold;
	Button btnRestart;
	public PauseDialog(final GameScene game){		
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		this.setSize(Engine.getWidth(), Engine.getHeight());
		
		mainGroup = new Group();
		btnBack = new Button(new TextureRegionDrawable(atlas.findRegion("btn-back-1")), new TextureRegionDrawable(atlas.findRegion("btn-back-2")));
		btnExit = new Button(new TextureRegionDrawable(atlas.findRegion("btn-exit-1")), new TextureRegionDrawable(atlas.findRegion("btn-exit-2")));
		Table table = new Table();
		table.setBackground(new NinePatchDrawable(atlas.createPatch("pause-bg")));
		table.pack();
		table.setSize(200, Engine.getHeight());
		mainGroup.setSize(200, Engine.getHeight());
		mainGroup.setPosition(Engine.getWidth()-mainGroup.getWidth(), 0);
		
		lblGold = new Label("0",new LabelStyle(font, Color.WHITE));
		lblGold.getStyle().background = new NinePatchDrawable(atlas.createPatch("label"));
		lblWhiteGold = new Label("0",new LabelStyle(font, Color.WHITE));
		lblWhiteGold.getStyle().background = new NinePatchDrawable(atlas.createPatch("label"));
		btnBack.setSize(btnBack.getPrefWidth(), btnBack.getPrefHeight());
		btnBack.setPosition(mainGroup.getWidth()/2-btnBack.getWidth()/2, Engine.getHeight()-btnBack.getHeight()-30);
		btnExit.setSize(btnExit.getPrefWidth(), btnExit.getPrefHeight());
		btnExit.setPosition(mainGroup.getWidth()/2-btnExit.getWidth()/2,100);
	
		Table goldTable = new Table();
		goldTable.add(new Image(atlas.findRegion("flag-gold"))).spaceRight(5);
		goldTable.add(lblGold).width(100);
		goldTable.row();
		goldTable.add(new Image(atlas.findRegion("flag-white-gold"))).spaceRight(5);
		goldTable.add(lblWhiteGold).width(100);
		goldTable.pack();
		goldTable.setWidth(btnExit.getWidth());
		goldTable.setPosition(mainGroup.getWidth()/2-goldTable.getWidth()/2,btnExit.getHeight()+btnExit.getY()+20);
		
		btnRestart = new Button(new TextureRegionDrawable(atlas.findRegion("restart")), new TextureRegionDrawable(atlas.findRegion("restart")));
		btnRestart.setPosition(mainGroup.getWidth()/2-btnRestart.getWidth()/2, 300);
		mainGroup.addActor(table);
		mainGroup.addActor(btnBack);
		mainGroup.addActor(btnExit);
		mainGroup.addActor(goldTable);
		mainGroup.addActor(btnRestart);

		
	
		Image mask = new Image(atlas.findRegion("color"));
		mask.setSize(this.getWidth(), this.getHeight());
		mask.setColor(new Color(0,0,0,0.7f));
		this.addActor(mask);
		this.addActor(mainGroup);
		
		final Image lblPause = new Image(atlas.findRegion("lbl-pause"));
		lblPause.setPosition(200, Engine.getHeight()/2-lblPause.getHeight()/2);
		this.addActor(lblPause);
		
		
		
		btnBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				close();
				super.clicked(event, x, y);
			}
		});
		btnExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.drive.setToLevelScene();
				super.clicked(event, x, y);
			}
		});
		btnRestart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				game.restart();
				super.clicked(event, x, y);
			}
		});
	}
	
	public void close(){
		this.remove();
	}
	
}
