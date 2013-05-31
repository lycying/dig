package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.c2d.graphic.surfaces.SurfaceData;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.Terrain;
import info.u250.digs.scenes.game.TerrainConfig;
import info.u250.digs.scenes.game.entity.GreenHat;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class StartUpScene extends SceneStage{
	DigsEngineDrive drive;
	TextureAtlas atlas = null;
	Terrain terrain;
	float deltaAppend;
	SimpleMeshBackground meshBackground ;
	final TriangleSurfaces surface;
	public StartUpScene(DigsEngineDrive drive){
		this.drive = drive;
		
		atlas = Engine.resource("All");
		
		meshBackground = new SimpleMeshBackground(new Color(0, 0, 0, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));

		
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(-50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(30,1000), new Vector2(0,350)));
//		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("ground")), new Vector2(0.3f,1), new Vector2(0,1000), new Vector2(0,0)));
		this.addActor(pbg);
		
		
		final SurfaceData data = new SurfaceData();
		data.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data.texture="Texture";
		data.points = new Array<Vector2>(){{
			add(new Vector2(-27.005554f,87f));
			add(new Vector2(-20,-4));
			add(new Vector2(119,250));
			add(new Vector2(200.99362f,-14f));
			add(new Vector2(293.00104f,300));
			add(new Vector2(356f,-9f));
			add(new Vector2(360f,310));
			add(new Vector2(380,-9f));
			add(new Vector2(400,300));
			add(new Vector2(458f,-9f));
			add(new Vector2(510f,280));
			add(new Vector2(556.0f,-7f));
			add(new Vector2(593f,270));
			add(new Vector2(650f,-53f));
			add(new Vector2(700f,290));
			add(new Vector2(735f,-53f));
			add(new Vector2(800f,290));
			add(new Vector2(850f,0));
			add(new Vector2(900f,290));
			add(new Vector2(960f,0));
			add(new Vector2(1024,350));
		}};
		surface  = new TriangleSurfaces(data);
		
		GreenHat greenHat = new GreenHat();
		greenHat.setScale(13);
		greenHat.setShowHp(false);
		greenHat.setAnimation(greenHat.getGoldAnimationLeft());
		greenHat.setPosition(720, 290);
		this.addActor(greenHat);
		
		TerrainConfig config = new TerrainConfig();
		config.surfaceFile = "texs/LoamWalls.jpg";
//		config.surfaceFile = "data/DSRT.png";
		config.runnerNumber = 20;
		config.segment = 8;
		config.width = (int)Engine.getWidth()/2;
		terrain = new Terrain(config);
		this.addActor(terrain);
		Image logo = new Image(atlas.findRegion("logo"));
		logo.setPosition(570, 120);
		logo.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0,-50,0.5f,Interpolation.swingIn),
				Actions.moveBy(0,50,0.5f,Interpolation.swingOut)
		)));
		this.addActor(logo);
		
		
		Image grass = new Image(atlas.findRegion("grass"));
		grass.setPosition(Engine.getWidth()/2-grass.getWidth()/2, -100);
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
		adventure.setPosition(Engine.getWidth()-adventure.getWidth()-20, 20);
		training.setPosition(adventure.getX()-adventure.getWidth()-20, 20);
	
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
		surface.render(Engine.getDeltaTime());

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
