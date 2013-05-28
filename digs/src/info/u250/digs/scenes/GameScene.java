package info.u250.digs.scenes;

import info.u250.c2d.engine.CoreProvider.CoreEvents;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.GameInformationPane;
import info.u250.digs.scenes.game.PauseDialog;
import info.u250.digs.scenes.game.Terrain;
import info.u250.digs.scenes.game.TerrainConfig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScene extends SceneStage {
	public DigsEngineDrive drive;
	SimpleMeshBackground meshBackground ;
	Terrain terrain = null;
	GameInformationPane gameInformationPane ;
	PauseDialog pauseDialog;
	final ScrollPane scroll;
	public GameScene(DigsEngineDrive drive){
		this.drive = drive;
		TextureAtlas atlas = Engine.resource("All");
		meshBackground = new SimpleMeshBackground(new Color(0, 0, 0, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
		
		pauseDialog = new PauseDialog(this);
		
		scroll = new ScrollPane(null);
		scroll.setStyle(new ScrollPaneStyle(null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider")),null, null));
		scroll.setFillParent(false);
		scroll.setScrollingDisabled(false, true);
		scroll.setWidth(Engine.getWidth());
		scroll.setHeight(Engine.getHeight());
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setOverscroll(false, false);
		scroll.setScrollbarsOnTop(false);
		
		
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
		
		final TextureRegionDrawable drawable_clear = new TextureRegionDrawable(atlas.findRegion("control-1"));
		final TextureRegionDrawable drawable_fill  = new TextureRegionDrawable(atlas.findRegion("control"));
		final Image controlButton = new Image(drawable_clear);
		controlButton.setPosition(Engine.getWidth()-controlButton.getWidth(), 30 );
		controlButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(controlButton.getDrawable() == drawable_clear){
					controlButton.setDrawable(drawable_fill);
					terrain.setFillMode(true);
				}else{
					controlButton.setDrawable(drawable_clear);
					terrain.setFillMode(false);
				}
				super.clicked(event, x, y);
			}
		});
		gameInformationPane = new GameInformationPane();
		final Image pause = new Image(new TextureRegionDrawable(atlas.findRegion("pause")));
		pause.setPosition(Engine.getWidth()-pause.getWidth(), Engine.getHeight()- 55);
		pause.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseGame();
				super.clicked(event, x, y);
			}
		});
		
		this.addActor(pbg);
		this.addActor(scroll);
		this.addActor(controlButton);
		this.addActor(gameInformationPane);
		this.addActor(pause);
		
		setupPauseResume();
		
	}
	
	public void configGame(TerrainConfig configx){
		pauseDialog.remove();
		if(null!=terrain){
			terrain.dispose();
		}
		
		TerrainConfig config = new TerrainConfig();
		config.surfaceFile = "data/DSRT.png";
//		config.surfaceFile = "data/DSRT.png";
		config.runnerNumber = 20;
		terrain = new Terrain(config);
		scroll.setWidget(terrain);
	}
	public void setupPauseResume(){
		Engine.getEventManager().register(CoreEvents.SystemPause, new EventListener(){
			@Override
			public void onEvent(Event event) {
				//call the pause dialog 
				if(Engine.getMainScene()==GameScene.this){
					pauseGame();
				}
			}
		});
		Engine.getEventManager().register(CoreEvents.SystemResume, new EventListener(){
			@Override
			public void onEvent(Event event) {
				if(Engine.getMainScene()==GameScene.this){
					Engine.doPause();
					if(null!=terrain)terrain.reload();
				}
			}
		});

	}
	void pauseGame(){
		addActor(pauseDialog);
		Engine.doPause();
	}
	
	@Override
	public void draw() {
		meshBackground.render(Engine.getDeltaTime());
		super.draw();
	}
	
	@Override
	public void show() {
		Engine.getMusicManager().playMusic("MusicBattle", true);
		super.show();
	}
	@Override
	public void hide() {
		Engine.getMusicManager().stopMusic("MusicBattle");
		super.hide();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				if(Engine.isPause()){
					Engine.doResume();
					pauseDialog.close();
				}else{
					pauseGame();
				}
			}
		} else {
			if (keycode == Keys.DEL) {
				if(Engine.isPause()){
					Engine.doResume();
					pauseDialog.close();
				}else{
					pauseGame();
				}
			}
		}
		return super.keyDown(keycode);
	}
}
