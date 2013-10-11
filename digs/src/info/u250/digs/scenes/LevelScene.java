package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.level.LevelMaker;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LevelScene extends SceneStage {
	DigsEngineDrive drive;
	final TextureAtlas atlas;
	
	public LevelScene(DigsEngineDrive drive){
		this.drive = drive;
		
		atlas = Engine.resource("All");
		
		
		Table levelTable = new Table(); 
		LevelMaker.levelMaker(this, levelTable);
		levelTable.pack();
		
		final ScrollPane levelPanel = new ScrollPane(levelTable);
		levelPanel.setSize(940, 560);
		levelPanel.setPosition(0, 0);
		levelPanel.setStyle(new ScrollPaneStyle(null,null,null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		levelPanel.setFillParent(false);
		levelPanel.setScrollingDisabled(true, false);
		levelPanel.setFlickScroll(true);
		levelPanel.setFadeScrollBars(false);
		levelPanel.setOverscroll(true, true);
		levelPanel.setScrollbarsOnTop(false);
		
//		Image rightImage = new Image(atlas.findRegion("color")){
//			@Override
//			public void act(float delta) {
//				this.setColor(new Color((100+levelPanel.getScrollPercentY()*150)/255f,(255-levelPanel.getScrollPercentY()*200)/255f,100f/255f,1));
//				super.act(delta);
//			}
//		};
//		rightImage.setSize(150, Engine.getHeight());
//		rightImage.setX(Engine.getWidth()-rightImage.getWidth());
//		this.addActor(rightImage);
		
		this.addActor(levelPanel);
	}
	public void startLevel(int level){
		this.drive.setToNpcListScene();
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
