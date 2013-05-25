package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.scenes.game.Terrain;
import info.u250.digs.scenes.game.TerrainConfig;
import info.u250.digs.scenes.ui.CountDownTimer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScene extends SceneStage {
	SimpleMeshBackground meshBackground ;
	Terrain terrain = null;
	public GameScene(){
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		meshBackground = new SimpleMeshBackground(new Color(0, 0, 0, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
		
	
		TerrainConfig config = new TerrainConfig();
		config.surfaceFile = "data/DSRT.png";
//		config.surfaceFile = "data/DSRT.png";
		config.runnerNumber = 20;
		terrain = new Terrain(config);
		
		
		final ScrollPane scroll = new ScrollPane(terrain);
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
		Table infoTable = new Table();
		infoTable.setBackground(new NinePatchDrawable(atlas.createPatch("topbar")));
		infoTable.add(new Image(atlas.findRegion("level")));
		infoTable.add(new Label("100", new LabelStyle(font, Color.BLACK))).spaceRight(50);
		infoTable.add(new Image(atlas.findRegion("gold")));
		infoTable.add(new Label("Gold:3232", new LabelStyle(font, Color.BLACK))).spaceRight(50);
		infoTable.add(new Image(atlas.findRegion("flag")));
		infoTable.add(new Label("Lv.32", new LabelStyle(font, Color.BLACK))).spaceRight(50);
		infoTable.add(new Image(atlas.findRegion("clock")));
		Label countDownLabel = new Label("00:00", new LabelStyle(font, Color.BLACK));
		CountDownTimer countDownTimer = new CountDownTimer(countDownLabel);
		countDownTimer.start();
		infoTable.add(countDownLabel);
		infoTable.padLeft(10).padRight(20).padTop(0).padBottom(0);
		infoTable.pack();
//		infoTable.setWidth(Engine.getWidth());
		infoTable.setY(Engine.getHeight()-infoTable.getHeight() - 5);
		
		Image pause = new Image(new TextureRegionDrawable(atlas.findRegion("pause")));
		pause.setPosition(Engine.getWidth()-pause.getWidth(), Engine.getHeight()- 55);
		
		this.addActor(pbg);
		this.addActor(scroll);
		this.addActor(controlButton);
		this.addActor(infoTable);
		this.addActor(pause);
		
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
}
