package info.u250.digs.scenes;

import info.u250.c2d.engine.CoreProvider.CoreEvents;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.InformationPane;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.PauseDialog;
import info.u250.digs.scenes.game.WinDialog;
import info.u250.digs.scenes.level.LevelIdx;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScene extends SceneStage {
	public DigsEngineDrive drive;
	SimpleMeshBackground meshBackground ;
	Level level = null;
	int levelIndex = 0;
	InformationPane gameInformationPane ;
	PauseDialog pauseDialog;
	WinDialog winDialog;
	final ScrollPane scroll;
	public GameScene(DigsEngineDrive drive){
		this.drive = drive;
		TextureAtlas atlas = Engine.resource("All");
		meshBackground = new SimpleMeshBackground(new Color(0, 0, 0, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
		
		pauseDialog = new PauseDialog(this);
		winDialog = new WinDialog(this);
		
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
		
		
		
		final TextureRegionDrawable drawable_clear = new TextureRegionDrawable(atlas.findRegion("control-1"));
		final TextureRegionDrawable drawable_fill  = new TextureRegionDrawable(atlas.findRegion("control"));
		final Image controlButton = new Image(drawable_clear);
		controlButton.setPosition(Engine.getWidth()-controlButton.getWidth(), 30 );
		controlButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(controlButton.getDrawable() == drawable_clear){
					controlButton.setDrawable(drawable_fill);
					level.setFillMode(true);
				}else{
					controlButton.setDrawable(drawable_clear);
					level.setFillMode(false);
				}
				super.clicked(event, x, y);
			}
		});
		gameInformationPane = new InformationPane();
		final Image pause = new Image(new TextureRegionDrawable(atlas.findRegion("pause")));
		pause.setPosition(Engine.getWidth()-pause.getWidth(), Engine.getHeight()- 55);
		pause.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseGame();
				super.clicked(event, x, y);
			}
		});
		
		this.addActor(scroll);
		this.addActor(controlButton);
		this.addActor(gameInformationPane);
		this.addActor(pause);
		
		setupPauseResume();
		
	}
	public void nextLevel(){
		startLevel(this.levelIndex+1);
	}
	public void restart(){
		startLevel(this.levelIndex);
	}
	public void startLevel(int level){
		this.levelIndex = level;
		configGame(LevelIdx.getLevelConfig(level));
	}
	private void configGame(LevelConfig config){
		config.idx = levelIndex;//remark it
		pauseDialog.remove();
		winDialog.remove();
		if(null!=level){
			level.dispose();
		}
		meshBackground = new SimpleMeshBackground(config.bottomColor,config.topColor);
		level = new Level(this,config);
		scroll.setWidget(level);
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
					if(null!=level)level.reload();
				}
			}
		});

	}
	void pauseGame(){
		if(this.getActors().contains(this.winDialog, true)) return;
		addActor(pauseDialog);
		Engine.doPause();
	}
	public void win(int levelIdx,int gold,int npc,int npcDead,int time){
		Engine.doPause();
		Engine.getMusicManager().stopMusic("MusicBattle");
		Engine.getSoundManager().playSound("SoundWin");
		addActor(winDialog);
		winDialog.show(levelIdx, gold, npc, npcDead, time);
	}
	@Override
	public void draw() {
		meshBackground.render(Engine.getDeltaTime());
		super.draw();
	}
	
	@Override
	public void show() {
		//delay play the music
		this.addAction(Actions.delay(2, Actions.run(new Runnable() {
			@Override
			public void run() {
				Engine.getMusicManager().playMusic("MusicBattle", true);
			}
		})));
		
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
					if(this.getActors().contains(this.winDialog, true)) {}else{
						Engine.doResume();
						pauseDialog.close();
					}
				}else{
					pauseGame();
				}
			}
		} else {
			if (keycode == Keys.DEL) {
				if(Engine.isPause()){
					if(this.getActors().contains(this.winDialog, true)) {}else{
						Engine.doResume();
						pauseDialog.close();
					}
				}else{
					pauseGame();
				}
			}
		}
		return super.keyDown(keycode);
	}
}
