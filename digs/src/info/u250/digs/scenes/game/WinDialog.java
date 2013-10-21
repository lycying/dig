package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.ui.Water;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class WinDialog extends Group {
	
	Button btnNextLevel ;
	Button btnExit ;
	Label lblGold;
	Label lblWhiteGold;
	Button btnRestart;
	Table table;
	public Water water = new Water( 201, 260, 
			new Color(151f/255f,196f/255f,188f/255f,0.2f),
			new Color(151f/255f,196f/255f,188f/255f,0.2f));
	public WinDialog(final GameScene game){		
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		this.setSize(Engine.getWidth(), Engine.getHeight());
		

		btnNextLevel = new Button(new TextureRegionDrawable(atlas.findRegion("d-nextlevel")), new TextureRegionDrawable(atlas.findRegion("d-nextlevel")));
		btnExit = new Button(new TextureRegionDrawable(atlas.findRegion("d-exit")), new TextureRegionDrawable(atlas.findRegion("d-exit")));
		
		
		lblGold = new Label("0",new LabelStyle(font, Color.WHITE));
		lblGold.getStyle().background = new NinePatchDrawable(atlas.createPatch("label"));
		lblWhiteGold = new Label("0",new LabelStyle(font, Color.WHITE));
		lblWhiteGold.getStyle().background = new NinePatchDrawable(atlas.createPatch("label"));
		btnNextLevel.setSize(btnNextLevel.getPrefWidth(), btnNextLevel.getPrefHeight());
		btnExit.setSize(btnExit.getPrefWidth(), btnExit.getPrefHeight());
		btnRestart = new Button(new TextureRegionDrawable(atlas.findRegion("restart")), new TextureRegionDrawable(atlas.findRegion("restart")));
		
		table = new Table();
		table.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
//		btnExit.setPosition(70, 380);
//		btnRestart.setPosition(btnExit.getX()+btnExit.getWidth()+30, btnExit.getY());
//		btnNextLevel.setPosition(btnRestart.getX()+btnRestart.getWidth()+30, btnRestart.getY());
		table.pad(30);
		table.add(btnExit).row().space(20);
		table.add(btnRestart).row().spaceBottom(20);
		table.add(btnNextLevel).row().spaceBottom(20);
		table.pack();

	
		Image bg = new Image(atlas.findRegion("win-bg"));
		bg.setPosition(Engine.getWidth()/2-bg.getWidth()/2,0);
		table.setPosition(bg.getX()+bg.getWidth()+40, 40);

		Image mask = new Image(atlas.findRegion("color"));
		mask.setSize(this.getWidth(), this.getHeight());
		mask.setColor(new Color(0,0,0,0.7f));
		this.addActor(mask);
		this.addActor(bg);
		this.addActor(table);
		
		btnNextLevel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				game.nextLevel();
				super.clicked(event, x, y);
			}
		});
		btnExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
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
	float accum = 0;
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		accum += Engine.getDeltaTime();
		if(accum>0.7f){
			accum -=0.7f;
			water.splash(Digs.RND.nextInt((int)Engine.getWidth()), 200*Digs.RND.nextFloat());
		}
		water.update(Engine.getDeltaTime());
		water.draw();
//		batch.end();
//		batch.begin();
//		table.draw(batch, parentAlpha);
	}
}
