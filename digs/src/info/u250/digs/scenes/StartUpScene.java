package info.u250.digs.scenes;

import info.u250.c2d.engine.CoreProvider.CoreEvents;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.c2d.graphic.surfaces.SurfaceData;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.Terrain;
import info.u250.digs.scenes.game.TerrainConfig;
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
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class StartUpScene extends SceneStage{
	DigsEngineDrive drive;
	TextureAtlas atlas = null;
	public Terrain terrain;
	public Group terrainContainer;
	float deltaAppend;
	SimpleMeshBackground meshBackground ;
	final TriangleSurfaces surface;
	final TriangleSurfaces surface2;
	final TriangleSurfaces surface3;
	
	SkeletonRenderer renderer;
	SkeletonData skeletonData;
	Skeleton skeleton;
	Animation animation;
	Array<com.esotericsoftware.spine.Event> events = new Array<com.esotericsoftware.spine.Event>();
	float time;

	public StartUpScene(DigsEngineDrive drive){
		loadTextures();
		
		this.drive = drive;
		this.renderer = new SkeletonRenderer();
		
		SkeletonJson json = new SkeletonJson(Engine.resource("Cat",TextureAtlas.class));
		json.setScale(0.5f);
		skeletonData = json.readSkeletonData(Gdx.files.internal("data/heiniu.json"));
		skeleton = new Skeleton(skeletonData);
		skeleton.setSkin("default");
		skeleton.setToSetupPose();
		skeleton = new Skeleton(skeleton);
		skeleton.updateWorldTransform();
		skeleton.setX(800);
		skeleton.setY(200);

		animation = skeletonData.findAnimation("idle");

		
		atlas = Engine.resource("All");
		
		meshBackground = new SimpleMeshBackground(new Color(1, 1, 1, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
//		meshBackground = new SimpleMeshBackground(new Color(1, 243f/255f,89f/255f, 1),new Color(31f/255f, 180f/255f, 59f/255f, 1f));

		
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(-50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(30,1000), new Vector2(0,350)));
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
			add(new Vector2(510f,250));
			add(new Vector2(556.0f,-7f));
			add(new Vector2(593f,250));
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
		final SurfaceData data2 = new SurfaceData();
		data2.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data2.texture="Texture2";
		data2.points = new Array<Vector2>(){{
			add(new Vector2(-27.005554f,660f));
			add(new Vector2(0,380));
			add(new Vector2(15,600));
			add(new Vector2(30,370));
			add(new Vector2(60,600));
			add(new Vector2(120,480));
			add(new Vector2(240,620));
			add(new Vector2(280,500));
			add(new Vector2(320,620));
			add(new Vector2(360,480));
			add(new Vector2(480,700));
			add(new Vector2(520,520));
			add(new Vector2(590,700));
			add(new Vector2(620,500));
			add(new Vector2(650,700));
			add(new Vector2(680,400));
			add(new Vector2(730,700));
			add(new Vector2(740,450));
			add(new Vector2(750,700));
			add(new Vector2(770,450));
			add(new Vector2(790,700));
			add(new Vector2(810,420));
			add(new Vector2(820,700));
			add(new Vector2(830,460));
			add(new Vector2(835,700));
			add(new Vector2(850,460));
			add(new Vector2(890,700));
			add(new Vector2(900,520));
			add(new Vector2(930,700));
			add(new Vector2(940,500));
			add(new Vector2(1024,600));
		}};
		surface2  = new TriangleSurfaces(data2);
		
		final SurfaceData data3 = new SurfaceData();
		data3.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data3.texture="Texture2";
		data3.points = new Array<Vector2>(){{
			add(new Vector2(-27.005554f,-100));
			add(new Vector2(0,50));
			add(new Vector2(15,-100));
			add(new Vector2(30,40));
			add(new Vector2(60,-100));
			add(new Vector2(120,30));
			add(new Vector2(240,-100));
			add(new Vector2(280,50));
			add(new Vector2(320,-100));
			add(new Vector2(360,45));
			add(new Vector2(480,-100));
			add(new Vector2(520,30));
			add(new Vector2(590,-100));
			add(new Vector2(620,25));
			add(new Vector2(650,-100));
			add(new Vector2(680,30));
			add(new Vector2(730,-100));
			add(new Vector2(740,35));
			add(new Vector2(750,-100));
			add(new Vector2(770,45));
			add(new Vector2(790,-100));
			add(new Vector2(810,20));
			add(new Vector2(820,-100));
			add(new Vector2(830,24));
			add(new Vector2(835,-100));
			add(new Vector2(850,12.5f));
			add(new Vector2(890,-100));
			add(new Vector2(900,50));
			add(new Vector2(930,-100));
			add(new Vector2(940,50));
			add(new Vector2(945,-100));
			add(new Vector2(950,640));
			add(new Vector2(1024,-100));
		}};
		surface3  = new TriangleSurfaces(data3);
		
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
		
		Button play = new Button(new TextureRegionDrawable(atlas.findRegion("btn-play")), new TextureRegionDrawable(atlas.findRegion("btn-play")));
		play.setSize(play.getPrefWidth(), play.getPrefHeight());
		play.setPosition(-20, 260);

		
		Finger finger = new Finger(atlas.findRegion("finger"), this);
		this.addActor(finger);
		
		Image water = new Image(atlas.findRegion("water"));
		water.setWidth(Engine.getWidth());
		water.addAction(Actions.forever(Actions.sequence(
				Actions.moveTo(0,-8,0.5f,Interpolation.swingIn),
				Actions.moveTo(0,0,0.5f,Interpolation.swingOut)
		)));
		this.addActor(water);
		
		
		
		play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				StartUpScene.this.drive.setToLevelScene();
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}
		});
		
//		play.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, 5,0.01f),Actions.moveBy(0, -5,0.01f))));
		
		
//		GreenHat greenHat = new GreenHat();
//		greenHat.setScale(13);
//		greenHat.setShowHp(false);
//		greenHat.setAnimation(greenHat.getGoldAnimationLeft());
//		greenHat.setPosition(720, 240);
//		this.addActor(greenHat);
		
		
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
		sound_flag.setPosition(20, 100);
		this.addActor(sound_flag);
		
		
		ParticleEffect e = new ParticleEffect();
		e.load(Gdx.files.internal("data/startscene.p"), Gdx.files.internal("data/"));
		ParticleEffectActor p = new ParticleEffectActor(e);
		p.setPosition(50, Engine.getHeight());
		
		
		this.addActor(play);
		this.addActor(p);
		
		
		Engine.getEventManager().register(CoreEvents.SystemResume, new EventListener(){
			@Override
			public void onEvent(Event event) {
				if(null!=terrain)terrain.reload();
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
			config.surfaceFile = "texs/brown169.gif";
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
		config.baseHeight = 200;
		config.width = (int)Engine.getWidth();
		terrain = new Terrain(config);
//		terrainContainer.setY(240);
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
		surface2.render(Engine.getDeltaTime());
		surface3.render(Engine.getDeltaTime());
		
		float lastTime = time;
		time += Gdx.graphics.getDeltaTime();
		events.clear();
		animation.apply(skeleton, lastTime, time, true, events);
		skeleton.updateWorldTransform();
		skeleton.update(Gdx.graphics.getDeltaTime());
		Engine.getSpriteBatch().begin();
		renderer.draw(Engine.getSpriteBatch(), skeleton);
		Engine.getSpriteBatch().end();

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
