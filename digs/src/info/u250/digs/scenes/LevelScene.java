package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.surfaces.SurfaceData;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.digs.Digs;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.IO;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.level.LevelPack;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class LevelScene extends SceneStage {
	DigsEngineDrive drive;
	final TextureAtlas atlas;
	final ScrollPane levelPanel;
	final LevelPack levelPack;
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
		{
			ParticleEffect e = Engine.resource("Effect");
			ParticleEffectActor p = new ParticleEffectActor(e,"level-screen"){
				float timeDelta = 0;
				
				Vector2 speed = new Vector2(400,200);
				Vector2 direction = new Vector2(1,1).nor();
				float SPEED = 400;
				@Override
				public void act(float delta) {
					timeDelta += delta;
					if(timeDelta>0.5f){
						timeDelta = 0;
						direction.set(Digs.RND.nextFloat()*Digs.RND.nextFloat(),Digs.RND.nextFloat()).nor();
					}
					
					if(getX()>920){
						speed.x = -SPEED*2 ;
					}
					if(getY()>500){
						speed.y = -SPEED ;
					}
					if(getX()<10){
						speed.x = SPEED *2;
					}
					if(getY()<10){
						speed.y = SPEED ;
					}
					
					this.translate(direction.x*speed.x*delta,direction.y*speed.y*delta);
				}
			};
			p.setPosition(100, 370);
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
		
		
		Button back = new Button(new TextureRegionDrawable(atlas.findRegion("btn-back-1")), new TextureRegionDrawable(atlas.findRegion("btn-back-2")));

		back.setPosition(0,Engine.getHeight()-back.getHeight()-50);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				drive.setToStartUpScene();
				super.clicked(event, x, y);
			}
		});
		this.addActor(back);
		levelPack = new LevelPack(this,IO.getPackScroll());
		levelPack.setPosition(840, 100);
		this.addActor(levelPack);
		
		KillCircleEntity kill = new KillCircleEntity(-80, -80, 120, Color.CYAN);
		this.addActor(kill);
		KillCircleEntity kill2 = new KillCircleEntity(0, 60, 60, Color.WHITE);
		this.addActor(kill2);
		KillCircleEntity kill3 = new KillCircleEntity(60, 60, 40, Color.YELLOW);
		this.addActor(kill3);
		
		
		Button button = new Button(new TextureRegionDrawable(atlas.findRegion("btn-achievements-1")), new TextureRegionDrawable(atlas.findRegion("btn-achievements-2")), null);
		button.setPosition(0, 120);
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				Digs.getGPSR().gpsShowAchievement();
				super.clicked(event, x, y);
			}
		});
		this.addActor(button);
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
		Digs.getAdmob().showInterstitial();
		this.levelPack.refresh();
		Engine.getMusicManager().playMusic("MusicBackground", true);
	}
	@Override
	public void hide() {
		Engine.getMusicManager().pauseMusic("MusicBackground");
		Engine.getMusicManager().stopMusic("MusicBattle");
		Engine.getMusicManager().stopMusic("MusicTimer");
		Engine.getMusicManager().stopMusic("MusicCollection");
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
