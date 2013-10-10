package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.level.LevelItemTable;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
		final Image bg = new Image(atlas.findRegion("branch-blue"));
		bg.rotate(180);
		bg.setPosition(Engine.getWidth(), +bg.getHeight());
		bg.setColor(Color.LIGHT_GRAY);
//		bg.setSize(Engine.getWidth(), Engine.getHeight());
		this.addActor(new Image(atlas.findRegion("branch-blue")));
		this.addActor(bg);
		Table backgroundFromNinePatch = new Table();
		backgroundFromNinePatch.setBackground(new NinePatchDrawable(atlas.createPatch("ui-board")));
		backgroundFromNinePatch.pack();
		backgroundFromNinePatch.setSize(Engine.getWidth()-50, 500);
		backgroundFromNinePatch.setX(25);
		this.addActor(backgroundFromNinePatch);
		
		Table levelTable = new Table(); 
		for(int i=0;i<100;i++){
			LevelItemTable item = new LevelItemTable(this,i,"The level:"+i+",Fuck the little boy!");
			switch(i%4){
			case 0:
				item.setColor(Color.WHITE);break;
			case 1:
				item.setColor(new Color(147f/255f,255f/255,109f/255f,1));break;
			case 2:
				item.setColor(new Color(((147+i)%255)/255f,255f/255,209f/255f,1));break;
			case 3:
				item.setColor(new Color(247f/255f,255f/255,((109+i)%255)/255f,1));break;
			}
			levelTable.add(item).spaceBottom(10);
			levelTable.row();
		}
		levelTable.pack();
		
		final ScrollPane levelPanel = new ScrollPane(levelTable);
		levelPanel.setSize(860, 430);
		levelPanel.setPosition(30, 35);
		levelPanel.setStyle(new ScrollPaneStyle(null,null,null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		levelPanel.setFillParent(false);
		levelPanel.setScrollingDisabled(true, false);
		levelPanel.setFlickScroll(true);
		levelPanel.setFadeScrollBars(true);
		levelPanel.setOverscroll(true, true);
		levelPanel.setScrollbarsOnTop(false);
		this.addActor(levelPanel);
	}
	public void startLevel(int level){
		Engine.setMainScene(this.drive.getNpcListScene());
	}
	@Override
	public void draw() {
		Gdx.gl.glClearColor(161f/255f, 209f/255f, 161f/255f, 1);
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
				Engine.setMainScene(drive.getStartUpScene());
			}
		} else {
			if (keycode == Keys.DEL) {
				Engine.setMainScene(drive.getStartUpScene());
			}
		}
		return super.keyDown(keycode);
	}
}
