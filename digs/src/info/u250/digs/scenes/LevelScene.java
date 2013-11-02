package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.level.LevelPack;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelScene extends SceneStage {
	public SimpleMeshBackground meshBackground ;
	DigsEngineDrive drive;
	final TextureAtlas atlas;
	final ScrollPane levelPanel;
	
	public LevelScene(final DigsEngineDrive drive){
		this.drive = drive;
		meshBackground = new SimpleMeshBackground(WebColors.CADET_BLUE.get(),Color.BLACK);
		
		
		atlas = Engine.resource("All");
		
		final Image bg = new Image(atlas.findRegion("level"));
		bg.setScale(Engine.getWidth()/bg.getWidth());
		bg.setY(Engine.getHeight()-bg.getHeight()*bg.getScaleX());
		bg.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.4f,0.5f),Actions.alpha(1,1))));
		this.addActor(bg);
		final Image levelSceneImg = new Image(atlas.findRegion("level-scene"));
		levelSceneImg.setScale(1f);
		levelSceneImg.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.2f,2f),Actions.alpha(1,4))));
		this.addActor(levelSceneImg);
		{
			ParticleEffect e = Engine.resource("Effect");
			ParticleEffectActor p = new ParticleEffectActor(e,"level-waterfall");
			p.setPosition(0, Engine.getHeight());
			this.addActor(p);
		}
		{
			ParticleEffect e = Engine.resource("Effect");
			ParticleEffectActor p = new ParticleEffectActor(e,"level");
			p.setPosition(200, 200);
			this.addActor(p);
		}
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(850, 400);
			this.addActor(tower);
		}
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(0, 300);
			this.addActor(tower);
		}
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(60, 190);
			this.addActor(tower);
		}
		
		
		levelPanel = new ScrollPane(null);
		levelPanel.setSize(740, 540);
		levelPanel.setPosition(0, 0);
//		levelPanel.setStyle(new ScrollPaneStyle(null,null,null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		levelPanel.setFillParent(false);
		levelPanel.setScrollingDisabled(true, false);
		levelPanel.setFlickScroll(true);
		levelPanel.setFadeScrollBars(false);
		levelPanel.setOverscroll(true, true);
		levelPanel.setScrollbarsOnTop(false);
		levelPanel.setX(140);
		this.addActor(levelPanel);
		
		final Image back = new Image(atlas.findRegion("about-back"));
		back.setPosition(0,Engine.getHeight()-back.getHeight());
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				drive.setToStartUpScene();
				super.clicked(event, x, y);
			}
		});
		this.addActor(back);
		LevelPack btn_table = new LevelPack(this,0);
		btn_table.setPosition(200, -12);
		this.addActor(btn_table);
		
	}
	
	public void startLevel(int pack,int level){
		this.drive.getGameScene().startLevel(pack,level);
		this.drive.setToGameScene();
	}
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
//		meshBackground.render(0);
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

	public ScrollPane getLevelPanel() {
		return levelPanel;
	}
	
}
