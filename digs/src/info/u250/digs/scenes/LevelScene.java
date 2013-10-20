package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.level.Level1_tour1;
import info.u250.digs.scenes.level.Level2_tour2;
import info.u250.digs.scenes.level.Level3_tour3;
import info.u250.digs.scenes.level.Level4_tour4;
import info.u250.digs.scenes.level.LevelMaker;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		
		Table decoTable = new Table();
		decoTable.add(new Image(atlas.findRegion("spring"))).space(10);
		decoTable.row();
		decoTable.add(new Image(atlas.findRegion("unknown"))).space(10);
		decoTable.row();
		decoTable.add(new Image(atlas.findRegion("unknown"))).space(10);
		decoTable.row();
		decoTable.add(new Image(atlas.findRegion("unknown"))).space(10);
		decoTable.row();
		decoTable.pack();
		decoTable.setPosition(20,2);
		
		Table levelTable = new Table(); 
		LevelMaker.levelMaker(this, levelTable);
		levelTable.pack();
		
		final ScrollPane levelPanel = new ScrollPane(levelTable);
		levelPanel.setSize(740, 560);
		levelPanel.setPosition(0, 0);
		levelPanel.setStyle(new ScrollPaneStyle(null,null,null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		levelPanel.setFillParent(false);
		levelPanel.setScrollingDisabled(true, false);
		levelPanel.setFlickScroll(true);
		levelPanel.setFadeScrollBars(false);
		levelPanel.setOverscroll(true, true);
		levelPanel.setScrollbarsOnTop(false);
		levelPanel.setX(220);
		
		
//		Image rightImage = new Image(atlas.findRegion("promo_fd"))
//		{
//			@Override
//			public void act(float delta) {
//				this.setColor(new Color((0+levelPanel.getScrollPercentY()*150)/255f,
//						(200-levelPanel.getScrollPercentY()*200)/255f,0/255f,
//						(55+levelPanel.getScrollPercentY()*200f)/255f));
//				super.act(delta);
//			}
//		};
//		rightImage.setSize(225, Engine.getHeight());
//		rightImage.setX(10);
//		this.addActor(rightImage);
		this.addActor(levelPanel);
		this.addActor(decoTable);
	}
	public void startLevel(int level){
		LevelConfig config = null;
		switch(level){
		case 1:
			config = new Level1_tour1();
			break;
		case 2:
			config = new Level2_tour2();
			break;
		case 3:
			config = new Level3_tour3();
			break;
		case 4:
			config = new Level4_tour4();
			break;
		default:
			config = new LevelConfig();
			config.bottomColor = WebColors.BLACK.get();
			config.topColor = WebColors.CADET_BLUE.get();
			config.lineHeight = 380;
			break;
		}
		
		this.drive.getGameScene().configGame(config);
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
