package info.u250.digs.scenes;

import info.u250.c2d.engine.CoreProvider.CoreEvents;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.digs.Digs;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.IO;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.Level.FingerMode;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.dialog.FunctionPane;
import info.u250.digs.scenes.game.dialog.PauseDialog;
import info.u250.digs.scenes.game.dialog.StatusPane;
import info.u250.digs.scenes.level.LevelIdx;
import info.u250.digs.scenes.ui.CommonDialog;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

public class GameScene extends SceneStage {
	public DigsEngineDrive drive;
	public SimpleMeshBackground meshBackground ;
	public Level level = null;
	int packIndex = 0;
	int levelIndex = 0;
	final PauseDialog pauseDialog;

	final Group functionGroup ;
	final FunctionPane functionPane;
	final StatusPane statusPane;
	final ScrollPane scroll;
	final Image pauseButton;
	final Button btnToggle;
	boolean isShowAim = true;
	public GameScene(DigsEngineDrive drive){
		this.drive = drive;
		TextureAtlas atlas = Engine.resource("All");
		meshBackground = new SimpleMeshBackground(new Color(0, 0, 0, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
		
		pauseDialog = new PauseDialog(this);
		statusPane = new StatusPane();
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
		
		pauseButton = new Image(new TextureRegionDrawable(atlas.findRegion("pause")));
		pauseButton.setPosition(Engine.getWidth()-pauseButton.getWidth(), Engine.getHeight()- pauseButton.getHeight());
		pauseButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseGame();
				super.clicked(event, x, y);
			}
		});
		
		functionPane = new FunctionPane(this);
		btnToggle = new Button(new TextureRegionDrawable(atlas.findRegion("expand")),null,new TextureRegionDrawable(atlas.findRegion("pitch")));
		btnToggle.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				functionPane.setVisible(!btnToggle.isChecked());
				super.clicked(event, x, y);
			}
		});
		functionPane.setPosition(btnToggle.getWidth(),0);
		functionGroup.addActor(functionPane);
		functionGroup.addActor(btnToggle);
		functionGroup.setPosition(0, Engine.getHeight()-80);
		
		
		setupPauseResume();
	}
	public void nextLevel(){
		if(packIndex==0 && levelIndex==LevelIdx.Level_String[0].length-1){//next scene
			startLevel(++this.packIndex,0);
			IO.savePackScroll(packIndex);
		}else if(packIndex==1 && levelIndex==LevelIdx.Level_String[1].length-1){//next scene
			startLevel(++this.packIndex,0);
			IO.savePackScroll(packIndex);
		}else if(packIndex==2 && levelIndex==LevelIdx.Level_String[2].length-1){//no more level
			CommonDialog dlg = new CommonDialog(Engine.getLanguagesManager().getString("java.nomorelevel"));
			this.addActor(dlg);
		}else{
			startLevel(this.packIndex,this.levelIndex+1);
		}
	}
	public void restart(){
		startLevel(this.packIndex,this.levelIndex);
	}
	public void startLevel(int pack,int level){
		this.packIndex = pack;
		this.levelIndex = level;
		configGame(LevelIdx.getLevelConfig(pack,level));
		Digs.getAdmob().hide();//hide the ad currently
	}
	
	public boolean isShowAim(){
		return isShowAim;
	}
	public void reallyStartLevel(){
		this.isShowAim = false;
		this.statusPane.startCounter(level);
	}
	public int leastTime(){
		return statusPane.getTimer().getSceonds();
	}
	public void levelCallback(){
		statusPane.setVisible(true);
		statusPane.show(level);
		statusPane.setPosition(Engine.getWidth()-statusPane.getWidth(), 0);
	}
	private void configGame(LevelConfig config){
		if(config.pack == 0){
			if(config.idx == 0){
				this.functionPane.tour1Button();
			}else{
				this.functionPane.basicButton();
			}
		}else{
			this.functionPane.fullButton();
		}
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
		
		//rebuilt it
		this.clear();
		isShowAim = true;
		this.addActor(scroll);
		this.addActor(functionGroup);
		this.addActor(statusPane);
		this.addActor(config.levelCompleteCallback.infoBorad(level));
		this.addActor(pauseButton);
		statusPane.setVisible(false);
		this.scroll.setScrollY(0);
	} 
	@Override
	public void show() {
		Digs.getAdmob().hide();
		super.show();
	}
	@Override
	public void hide() {
		Engine.getMusicManager().stopMusic("MusicBattle");
		Engine.getMusicManager().stopMusic("MusicTimer");
		Engine.getMusicManager().stopMusic("MusicCollection");
		Timer.instance().clear();
		super.hide();
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
	public FingerMode getFingerMode(){
		return this.functionPane.getFingerMode();
	}
	public void setFingerMode(FingerMode mode){
		this.functionPane.setFingerMode(mode);
	}
	
	public StatusPane getStatusPane() {
		return statusPane;
	}
	void pauseGame(){
		if(level==null)return;
		if(level.isMapMaking())return;//avoid the resource load many times
		if(level.config.levelCompleteCallback.isFail() || level.config.levelCompleteCallback.isWin()){
			return;
		}
		addActor(pauseDialog);
		pauseDialog.show();
		Engine.doPause();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(null!=level){
			this.statusPane.update(level);
		}
	}
	@Override
	public void draw() {
		meshBackground.render(Engine.getDeltaTime());
		super.draw();
	}
	
	public ScrollPane getScroll() {
		return scroll;
	}
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				if(level.config.levelCompleteCallback.isFail() || level.config.levelCompleteCallback.isWin()){
					drive.setToLevelScene();
				}else{
					if(Engine.isPause()){
						Engine.doResume();
						pauseDialog.close();
					}else{
						pauseGame();
					}
				}
			}
		} else {
			if (keycode == Keys.DEL) {
				if(level.config.levelCompleteCallback.isFail() || level.config.levelCompleteCallback.isWin()){
					drive.setToLevelScene();
				}else{
					if(Engine.isPause()){
						Engine.doResume();
						pauseDialog.close();
					}else{
						pauseGame();
					}
				}
			}
		}
		return super.keyDown(keycode);
	}
}
