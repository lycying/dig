package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.surfaces.SurfaceData;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.IO;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.level.LevelPack;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public class LevelScene extends SceneStage {
	DigsEngineDrive drive;
	final TextureAtlas atlas;
	final ScrollPane levelPanel;
	final TriangleSurfaces surface2;
	final TriangleSurfaces surface3;
	public LevelScene(final DigsEngineDrive drive){
		this.drive = drive;
		final SurfaceData data2 = new SurfaceData();
		data2.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data2.texture="Texture2";
		data2.points = new Array<Vector2>(){{
			add(new Vector2(-27.005554f,660f));
			add(new Vector2(0,480));
			add(new Vector2(15,600));
			add(new Vector2(30,500));
			add(new Vector2(60,600));
			add(new Vector2(120,480));
			add(new Vector2(240,620));
			add(new Vector2(280,500));
			add(new Vector2(320,620));
			add(new Vector2(360,480));
			add(new Vector2(480,700));
			add(new Vector2(520,520));
			add(new Vector2(590,700));
			add(new Vector2(620,500));
			add(new Vector2(650,700));
			add(new Vector2(680,480));
			add(new Vector2(730,700));
			add(new Vector2(740,450));
			add(new Vector2(750,700));
			add(new Vector2(770,450));
			add(new Vector2(790,700));
			add(new Vector2(810,480));
			add(new Vector2(820,700));
			add(new Vector2(830,500));
			add(new Vector2(835,700));
			add(new Vector2(850,520));
			add(new Vector2(890,700));
			add(new Vector2(900,520));
			add(new Vector2(930,700));
			add(new Vector2(940,500));
			add(new Vector2(1024,600));
		}};
		
		surface2  = new TriangleSurfaces(data2);
		
		final SurfaceData data3 = new SurfaceData();
		data3.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data3.texture="Texture2";
		data3.points = new Array<Vector2>(){{
			add(new Vector2(-27.005554f,-100));
			add(new Vector2(0,50));
			add(new Vector2(15,-100));
			add(new Vector2(30,40));
			add(new Vector2(60,-100));
			add(new Vector2(120,30));
			add(new Vector2(240,-100));
			add(new Vector2(280,50));
			add(new Vector2(320,-100));
			add(new Vector2(360,45));
			add(new Vector2(480,-100));
			add(new Vector2(520,30));
			add(new Vector2(590,-100));
			add(new Vector2(620,25));
			add(new Vector2(650,-100));
			add(new Vector2(680,30));
			add(new Vector2(730,-100));
			add(new Vector2(740,35));
			add(new Vector2(750,-100));
			add(new Vector2(770,45));
			add(new Vector2(790,-100));
			add(new Vector2(810,20));
			add(new Vector2(820,-100));
			add(new Vector2(830,24));
			add(new Vector2(835,-100));
			add(new Vector2(850,12.5f));
			add(new Vector2(890,-100));
			add(new Vector2(900,50));
			add(new Vector2(930,-100));
			add(new Vector2(940,50));
			add(new Vector2(945,-100));
			add(new Vector2(950,640));
			add(new Vector2(1024,-100));
		}};
		surface3  = new TriangleSurfaces(data3);
		
		atlas = Engine.resource("All");
		
		final Image bg = new Image(atlas.findRegion("level"));
		bg.setScale(Engine.getWidth()/bg.getWidth());
		bg.setY(Engine.getHeight()-bg.getHeight()*bg.getScaleX());
		bg.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.4f,0.5f),Actions.alpha(1,1))));
		this.addActor(bg);
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
		levelPanel.setFillParent(false);
		levelPanel.setScrollingDisabled(true, false);
		levelPanel.setFlickScroll(true);
		levelPanel.setFadeScrollBars(false);
		levelPanel.setOverscroll(true, true);
		levelPanel.setScrollbarsOnTop(false);
		levelPanel.setX(130);
		this.addActor(levelPanel);
		
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("BigFont");
		TextButtonStyle style = new TextButtonStyle(new NinePatchDrawable(atlas.createPatch("btn")), new NinePatchDrawable(atlas.createPatch("btn-click")), null, font);
		style.fontColor = Color.BLACK;
		style.downFontColor = Color.RED;
		final TextButton back = new TextButton("Back",style);
		back.padRight(60);
		back.pack();
		back.setPosition(0,Engine.getHeight()-back.getHeight()-30);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				drive.setToStartUpScene();
				super.clicked(event, x, y);
			}
		});
		this.addActor(back);
		LevelPack btn_table = new LevelPack(this,IO.getPackScroll());
		btn_table.setPosition(810, 120);
		this.addActor(btn_table);
		
	}
	
	public void startLevel(int pack,int level){
		this.drive.getGameScene().startLevel(pack,level);
		this.drive.setToGameScene();
	}
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		super.draw();
		surface2.render(Engine.getDeltaTime());
		surface3.render(Engine.getDeltaTime());
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
