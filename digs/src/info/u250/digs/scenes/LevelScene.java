package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.level.LevelPack;
import info.u250.digs.scenes.level.LevelTable;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LevelScene extends SceneStage {
	DigsEngineDrive drive;
	final TextureAtlas atlas;
	
	public LevelScene(final DigsEngineDrive drive){
		this.drive = drive;
	
		
		
		atlas = Engine.resource("All");
		
		final Image bg = new Image(atlas.findRegion("level"));
		bg.setScale(Engine.getWidth()/bg.getWidth());
		bg.setY(Engine.getHeight()-bg.getHeight()*bg.getScaleX());
		bg.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.4f,0.5f),Actions.alpha(1,1))));

		this.addActor(bg);
		{
			ParticleEffect e = Engine.resource("Effect");
			ParticleEffectActor p = new ParticleEffectActor(e,"level");
			p.setPosition(200, 200);
			this.addActor(p);
		}
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(50, -100);
			tower.addAction(Actions.forever(Actions.sequence(
					Actions.moveBy(0, 460,5),Actions.delay(5),Actions.moveBy(-300, 0,1),Actions.moveBy(300, -460),Actions.delay(5)
			)));
			this.addActor(tower);
		}
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(0, 100);
			this.addActor(tower);
		}
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(130, -10);
			this.addActor(tower);
		}
		
		Table levelTable = new Table(); 
		levelTable.add(new Label("Choose Level",new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.YELLOW))).row();
		LevelTable.levelMaker(this, levelTable);
		levelTable.add(new Label("I feel very sorrow ,\n but i do not why ..",new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.BLUE))).row();
		levelTable.add(new Label("I do nothing but dig , \n i feel it must be sth worth to do ..",new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.YELLOW))).row();
		levelTable.add(new Label("Sadlly i can bear \n long \n long \n long \n...",new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.WHITE))).row();
		levelTable.pack();
		
		final ScrollPane levelPanel = new ScrollPane(levelTable);
		levelPanel.setSize(740, 540);
		levelPanel.setPosition(0, 0);
		levelPanel.setStyle(new ScrollPaneStyle(null,null,null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		levelPanel.setFillParent(false);
		levelPanel.setScrollingDisabled(true, false);
		levelPanel.setFlickScroll(true);
		levelPanel.setFadeScrollBars(false);
		levelPanel.setOverscroll(true, true);
		levelPanel.setScrollbarsOnTop(false);
		levelPanel.setX(220);
		this.addActor(levelPanel);
		
		final Image back = new Image(atlas.findRegion("about-back"));
		back.setPosition(0,Engine.getHeight()-back.getHeight());
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				drive.setToStartUpScene();
				super.clicked(event, x, y);
			}
		});
		this.addActor(back);
		LevelPack btn_table = new LevelPack(0);
		btn_table.setPosition(300, -12);
		

		this.addActor(btn_table);
		
	}
	static final class MyButton extends Button{
		public MyButton(Drawable up, Drawable down, Drawable checked){
			super(up,down,checked);
		}
		@Override
		public float getPrefHeight() {
			if(this.isChecked())return 120;
			else return 60;
		}
		@Override
		public float getPrefWidth() {
			if(this.isChecked())return 203;
			return 102;
		}
	}
	public void startLevel(int level){
		this.drive.getGameScene().startLevel(level);
		this.drive.setToGameScene();
	}
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0,0,0, 1);
		super.draw();
	}
	@Override
	public void show() {
		super.show();
		Engine.getMusicManager().playMusic("MusicBackground", true);
	}
	@Override
	public void hide() {
		Engine.getMusicManager().pauseMusic("MusicBackground");
		super.hide();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				this.drive.setToStartUpScene();
			}
		} else {
			if (keycode == Keys.DEL) {
				this.drive.setToStartUpScene();
			}
		}
		return super.keyDown(keycode);
	}
}
