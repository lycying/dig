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
import info.u250.digs.scenes.npclist.ParticleEffectActor;
import info.u250.digs.scenes.start.Finger;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class StartUpScene extends SceneStage{
	DigsEngineDrive drive;
	TextureAtlas atlas = null;
	public Terrain terrain;
	public Group terrainContainer;
	float deltaAppend;
	SimpleMeshBackground meshBackground ;
	final TriangleSurfaces surface;
	public StartUpScene(DigsEngineDrive drive){
		loadTextures();
		
		this.drive = drive;
		
		atlas = Engine.resource("All");
		
		meshBackground = new SimpleMeshBackground(new Color(1, 1, 1, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
//		meshBackground = new SimpleMeshBackground(new Color(1, 243f/255f,89f/255f, 1),new Color(31f/255f, 180f/255f, 59f/255f, 1f));

		
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(-50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(30,1000), new Vector2(0,350)));
//		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("ground")), new Vector2(0.3f,1), new Vector2(0,1000), new Vector2(0,0)));
		this.addActor(pbg);
		
		final SurfaceData data = new SurfaceData();
		data.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data.texture="Texture";
		data.points = new Array<Vector2>(){{
			add(new Vector2(-27.005554f,300f));
			add(new Vector2(-20,-4));
			add(new Vector2(119,250));
			add(new Vector2(200.99362f,-14f));
			add(new Vector2(293.00104f,300));
			add(new Vector2(356f,-9f));
			add(new Vector2(360f,310));
			add(new Vector2(380,-9f));
			add(new Vector2(400,300));
			add(new Vector2(458f,-9f));
			add(new Vector2(510f,180));
			add(new Vector2(556.0f,-7f));
			add(new Vector2(593f,200));
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
		
		
		ParticleEffect e = new ParticleEffect();
		e.load(Gdx.files.internal("data/startscene.p"), Gdx.files.internal("data/"));
		ParticleEffectActor p = new ParticleEffectActor(e);
		p.setPosition(50, Engine.getHeight());
		this.addActor(p);
		
		
		
		terrainContainer = new Group();
		this.addActor(terrainContainer);
		genTerrain();
		
		Image logo = new Image(atlas.findRegion("logo"));
		logo.setPosition(570, 120);
		logo.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0,-80,0.5f,Interpolation.swingIn),
				Actions.moveBy(0,80,0.5f,Interpolation.swingOut)
		)));
		this.addActor(logo);
		
		
		
		Image grass = new Image(atlas.findRegion("grass"));
		grass.setPosition(Engine.getWidth()/2-grass.getWidth()/2, -30);
		this.addActor(grass);
		
		Button adventure = new Button(new TextureRegionDrawable(atlas.findRegion("btn-start-adventure-1")), new TextureRegionDrawable(atlas.findRegion("btn-start-adventure-2")));
		Button training = new Button(new TextureRegionDrawable(atlas.findRegion("btn-start-training-1")), new TextureRegionDrawable(atlas.findRegion("btn-start-training-2")));
		adventure.setSize(adventure.getPrefWidth(), adventure.getPrefHeight());
		training.setSize(training.getPrefWidth(), training.getPrefHeight());
		
		training.setPosition(470,300);
		adventure.setPosition(470, 390);
		
//		adventure.setPosition(Engine.getWidth()-adventure.getWidth()-20, 20);
//		training.setPosition(adventure.getX()-adventure.getWidth()-20, 20);
	
		this.addActor(adventure);
		this.addActor(training);
		final TextureRegionDrawable sound_flag_on = new TextureRegionDrawable(atlas.findRegion("sound-on"));
		final TextureRegionDrawable sound_flag_off = new TextureRegionDrawable(atlas.findRegion("sound-off"));
		final Image sound_flag = new Image(sound_flag_on); 
		Engine.getSoundManager().setVolume(0);
		Engine.getMusicManager().setVolume(0);
		sound_flag.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(sound_flag.getDrawable() == sound_flag_off){
					sound_flag.setDrawable(sound_flag_on);
					Engine.getSoundManager().playSound("SoundClick");
				}else{
					sound_flag.setDrawable(sound_flag_off);
					Engine.getSoundManager().playSound("SoundClick");
				}
				super.clicked(event, x, y);
			}
		});
		sound_flag.setPosition(460, 240);
		this.addActor(sound_flag);
		
		Finger finger = new Finger(atlas.findRegion("finger"), this);
		this.addActor(finger);
		
		Image water = new Image(atlas.findRegion("water"));
		water.setWidth(Engine.getWidth());
		water.addAction(Actions.forever(Actions.sequence(
				Actions.moveTo(0,-8,0.5f,Interpolation.swingIn),
				Actions.moveTo(0,0,0.5f,Interpolation.swingOut)
		)));
		this.addActor(water);
		
		
		
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
	
	List<String> texs = new ArrayList<String>();
	void loadTextures(){
//		java.io.File file = new java.io.File("assets/texs");
//		for(String s:file.list()){
//			System.out.println("texs.add(\"texs/"+s+"\");");
//		}
		texs.add("texs/ALIN.png");
		texs.add("texs/BrickRound.jpg");
		texs.add("texs/brown002.jpg");
		texs.add("texs/brown063.jpg");
		texs.add("texs/brown091.png");
		texs.add("texs/brown094.jpg");
		texs.add("texs/brown096.jpg");
		texs.add("texs/brown138.jpg");
		texs.add("texs/brown155.jpg");
		texs.add("texs/brown156.jpg");
		texs.add("texs/brown169.gif");
		texs.add("texs/brown178.png");
		texs.add("texs/checker-mush.jpg");
		texs.add("texs/cobblestonesDepth.gif");
		texs.add("texs/cobblestonesDiffuse.gif");
		texs.add("texs/dgrey038.jpg");
		texs.add("texs/Dirt.png");
		texs.add("texs/DirtGrass.png");
		texs.add("texs/DSRT.png");
		texs.add("texs/flower.png");
		texs.add("texs/fluorescent-infection.jpg");
		texs.add("texs/frog-land.jpg");
		texs.add("texs/green-monkeyskin.jpg");
		texs.add("texs/green013.jpg");
		texs.add("texs/green020.jpg");
		texs.add("texs/green035.gif");
		texs.add("texs/green057.jpg");
		texs.add("texs/green058.jpg");
		texs.add("texs/green073.jpg");
		texs.add("texs/green080.jpg");
		texs.add("texs/green090.jpg");
		texs.add("texs/green092.png");
		texs.add("texs/Leather.jpg");
		texs.add("texs/lgren008.jpg");
		texs.add("texs/lgrey014.jpg");
		texs.add("texs/lgrey027.jpg");
		texs.add("texs/lgrey030.jpg");
		texs.add("texs/lgrey046.jpg");
		texs.add("texs/lgrey062.jpg");
		texs.add("texs/lgrey074.jpg");
		texs.add("texs/lgrey087.gif");
		texs.add("texs/lgrey089.gif");
		texs.add("texs/lgrey127.gif");
		texs.add("texs/lgrey133.jpg");
		texs.add("texs/LoamWalls.jpg");
		texs.add("texs/MetalGalvanized.jpg");
		texs.add("texs/micro-clorophyl.jpg");
		texs.add("texs/multi035.jpg");
		texs.add("texs/multi270.jpg");
		texs.add("texs/multi272.jpg");
		texs.add("texs/pink020.jpg");
		texs.add("texs/pink029.jpg");
		texs.add("texs/pink076.jpg");
		texs.add("texs/pink079.gif");
		texs.add("texs/pink095.jpg");
		texs.add("texs/pink101.jpg");
		texs.add("texs/planet-gravel.jpg");
		texs.add("texs/purpl043.jpg");
		texs.add("texs/purpl045.gif");
		texs.add("texs/purpl046.gif");
		texs.add("texs/purpl053.gif");
		texs.add("texs/purpl055.gif");
		texs.add("texs/purpl075.jpg");
		texs.add("texs/purpl192.jpg");
		texs.add("texs/RockLayered.jpg");
		texs.add("texs/Rust.jpg");
		texs.add("texs/simple-greenish.jpg");
		texs.add("texs/skinny-reptile.jpg");
		texs.add("texs/solitary-moss.jpg");
		texs.add("texs/Stone.jpg");
		texs.add("texs/stone57.gif");
		texs.add("texs/stonewallDepth.gif");
		texs.add("texs/trigger-turf.jpg");
		texs.add("texs/water-sheets.jpg");
		texs.add("texs/wood-emboss.jpg");
	}
	int texs_index = -1;
	void genTerrain(){
		terrainContainer.clear();
		if(null!=terrain){
			terrain.setTouchable(Touchable.disabled);
			terrain.dispose();
		}
		terrain = null;
		TerrainConfig config = new TerrainConfig();
//		
		if(-1==texs_index){
			config.surfaceFile = "texs/brown178.png";
			texs_index++;
		}else{
			config.surfaceFile = texs.get(new java.util.Random().nextInt(texs.size()));
		}
//		config.surfaceFile = texs.get(texs_index++);
		
//		config.surfaceFile = "texs/multi035.jpg";
		if(texs_index>texs.size()-1){
			texs_index = 0;
		}
//		config.surfaceFile = "data/DSRT.png";
		config.runnerNumber = 20;
		config.segment = 8;
		config.width = (int)Engine.getWidth()/2;
		terrain = new Terrain(config);
		
		terrainContainer.addActor(terrain);
	}
	@Override
	public void act(float delta) {
		deltaAppend += delta;
		if(deltaAppend>30f){
			genTerrain();
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
}
