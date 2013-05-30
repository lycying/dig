package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.Terrain;
import info.u250.digs.scenes.game.TerrainConfig;
import info.u250.digs.scenes.game.entity.GreenHat;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StartUpScene extends SceneStage{
	DigsEngineDrive drive;
	TextureAtlas atlas = null;
	Terrain terrain;
	float deltaAppend;
	SimpleMeshBackground meshBackground ;
	public StartUpScene(DigsEngineDrive drive){
		this.drive = drive;
		
		atlas = Engine.resource("All");
		
		meshBackground = new SimpleMeshBackground(new Color(1, 1, 1, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));

		
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(-50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(30,1000), new Vector2(0,350)));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("ground")), new Vector2(0.3f,1), new Vector2(0,1000), new Vector2(0,0)));
		this.addActor(pbg);
		
		GreenHat greenHat = new GreenHat();
		greenHat.setScale(13);
		greenHat.setShowHp(false);
		greenHat.setAnimation(greenHat.getGoldAnimationLeft());
		greenHat.setPosition(780, 195);
		this.addActor(greenHat);
		
		TerrainConfig config = new TerrainConfig();
		config.surfaceFile = "texs/DSRT.png";
//		config.surfaceFile = "data/DSRT.png";
		config.runnerNumber = 20;
		config.segment = 8;
		config.width = (int)Engine.getWidth()/2;
		terrain = new Terrain(config);
		this.addActor(terrain);
		Image logo = new Image(atlas.findRegion("logo"));
		logo.setPosition(580, 400);
		this.addActor(logo);
		
		
		Image grass = new Image(atlas.findRegion("grass"));
		grass.setPosition(Engine.getWidth()/2-grass.getWidth()/2, -150);
		this.addActor(grass);
		
		Image water = new Image(atlas.findRegion("water"));
		water.setWidth(Engine.getWidth());
		water.addAction(Actions.forever(Actions.sequence(
				Actions.moveTo(0,-8,0.5f,Interpolation.swingIn),
				Actions.moveTo(0,0,0.5f,Interpolation.swingOut)
		)));
		this.addActor(water);
		
		Button adventure = new Button(new TextureRegionDrawable(atlas.findRegion("btn-start-adventure-1")), new TextureRegionDrawable(atlas.findRegion("btn-start-adventure-2")));
		Button training = new Button(new TextureRegionDrawable(atlas.findRegion("btn-start-training-1")), new TextureRegionDrawable(atlas.findRegion("btn-start-training-2")));
		adventure.setPosition(550, 310);
		training.setPosition(550, 220);
	
		this.addActor(adventure);
		this.addActor(training);
		
		adventure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.setMainScene(StartUpScene.this.drive.getNpcListScene());
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}
		});
		training.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//Engine.setMainScene(StartUpScene.this.drive.getNpcListScene());
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}
		});
		
	}
	@Override
	public void act(float delta) {
		deltaAppend += delta;
		if(deltaAppend>20){
			terrain.dispose();
			terrain.addTerrains();
			//terrain.addDocks();
			terrain.addNpcs();
			deltaAppend = 0;
		}
		super.act(delta);
	}
	@Override
	public void draw() {
		meshBackground.render(Engine.getDeltaTime());
		super.draw();
	}
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				System.exit(0);
			}
		} else {
			if (keycode == Keys.DEL) {
				System.exit(0);
			}
		}
		return super.keyDown(keycode);
	}
}
