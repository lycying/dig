package info.u250.digs.scenes;

import info.u250.c2d.engine.CoreProvider.CoreEvents;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.dialog.FunctionPane;
import info.u250.digs.scenes.game.dialog.InformationPane;
import info.u250.digs.scenes.game.dialog.PauseDialog;
import info.u250.digs.scenes.level.LevelIdx;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScene extends SceneStage {
	public DigsEngineDrive drive;
	public SimpleMeshBackground meshBackground ;
	public Level level = null;
	int packIndex = 0;
	int levelIndex = 0;
	InformationPane gameInformationPane ;
	PauseDialog pauseDialog;

	Group functionGroup ;
	final ScrollPane scroll;
	final Image pauseButton;
	public GameScene(DigsEngineDrive drive){
		this.drive = drive;
		TextureAtlas atlas = Engine.resource("All");
		meshBackground = new SimpleMeshBackground(new Color(0, 0, 0, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
		
		pauseDialog = new PauseDialog(this);
		functionGroup = new Group();
		
		scroll = new ScrollPane(null);
		scroll.setStyle(new ScrollPaneStyle(null,null, null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		scroll.setFillParent(false);
		scroll.setScrollingDisabled(true, false);
		scroll.setWidth(Engine.getWidth());
		scroll.setHeight(Engine.getHeight());
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setOverscroll(false, false);
		scroll.setScrollbarsOnTop(false);
		scroll.setScrollBarPositions(true, false);
		
		gameInformationPane = new InformationPane();
		pauseButton = new Image(new TextureRegionDrawable(atlas.findRegion("pause")));
		pauseButton.setPosition(Engine.getWidth()-pauseButton.getWidth(), Engine.getHeight()- pauseButton.getHeight());
		pauseButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseGame();
				super.clicked(event, x, y);
			}
		});
		
		final FunctionPane functionPane = new FunctionPane(this);
		final Button btn_actor = new Button(new TextureRegionDrawable(atlas.findRegion("expand")),null,new TextureRegionDrawable(atlas.findRegion("pitch")));
		btn_actor.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				functionPane.setVisible(!btn_actor.isChecked());
				super.clicked(event, x, y);
			}
		});
		functionPane.setPosition(btn_actor.getWidth(),0);
		functionGroup.addActor(functionPane);
		functionGroup.addActor(btn_actor);
		functionGroup.setPosition(0, Engine.getHeight()-functionPane.getHeight()+10);
		setupPauseResume();
		
		this.addActor(scroll);
		this.addActor(functionGroup);
		this.addActor(pauseButton);
	}
	public void nextLevel(){
		startLevel(this.packIndex,this.levelIndex+1);
	}
	public void restart(){
		startLevel(this.packIndex,this.levelIndex);
	}
	public void startLevel(int pack,int level){
		this.packIndex = pack;
		this.levelIndex = level;
		configGame(LevelIdx.getLevelConfig(pack,level));
	}
	private void configGame(LevelConfig config){
		//rebuilt it
		this.clear();
		this.addActor(scroll);
		this.addActor(functionGroup);
		this.addActor(pauseButton);
		
		if(config.height>Engine.getHeight()){
			functionGroup.setX(35);
		}else{
			functionGroup.setX(0);
		}
		
		config.idx = levelIndex;//remark it
		if(null!=level){
			level.dispose();
		}
		meshBackground = new SimpleMeshBackground(config.bottomColor,config.topColor);
		level = new Level(this,config);
		scroll.setWidget(level);
		
		//delay play the music
		this.addAction(Actions.delay(2, Actions.run(new Runnable() {
			@Override
			public void run() {
				Engine.getMusicManager().playMusic("MusicBattle", true);
			}
		})));
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
		if(level==null)return;
		if(level.isMapMaking())return;//avoid the resource load many times
		if(level.config.levelCompleteCallback.isFail() || level.config.levelCompleteCallback.isWin()){
			return;
		}
		addActor(pauseDialog);
		Engine.doPause();
	}
	@Override
	public void draw() {
		meshBackground.render(Engine.getDeltaTime());
		super.draw();
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
					if(!level.config.levelCompleteCallback.isFail() && !level.config.levelCompleteCallback.isWin()){
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
					if(!level.config.levelCompleteCallback.isFail() && !level.config.levelCompleteCallback.isWin()){
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
