package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.background.SimpleMeshBackground;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.ui.AnimationDrawable;
import info.u250.digs.scenes.ui.ParticleEffectActor;
import info.u250.digs.scenes.ui.SpineActor;
import info.u250.digs.scenes.ui.Ticker;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AboutScene extends SceneStage{
	DigsEngineDrive drive;
//	ShaderProgram shader ;
//	FrameBuffer frameBuffer;
//	Mesh mesh ;
//	StepInfo info ;
	final SimpleMeshBackground bg ;
	final Rain[] rains;
	final ParallaxGroup pbg;
//	boolean buildSuccess = false;
	String[] contributes = new String[]{
			"--Credits--",
			"Design: Lycying@gmail.com" ,
			"Programming:  Lycying@gmail.com",
			"Artwork and Graphics: Annie",
			"Music: Tyler Johnson (Casualties of War)",
			"Sound Effects: Lycying@gmail.com",
			"Story: Annie",
			"Idea: Miner4k \n https://mojang.com/notch/j4k2k6/miners4k/",
			"Engine: C2d-engine,Libgdx",
			"Blog: http://www.u250.info",
			"--Thanks--",
			"Thanks: Zhaoyunhello@gmail.com",
			"Thanks: Yadongx@gmail.com"
	};
	public AboutScene(final DigsEngineDrive drive){
		this.drive = drive;
		final TextureAtlas atlas = Engine.resource("All");
		int number = (int)(Engine.getWidth()/Rain.WIDTH);
		rains = new Rain[number];
		for(int i=0;i<number;i++){
			rains[i] = new Rain(atlas.findRegion("rain"+(Digs.RND.nextInt(4)+1)));
			rains[i].setX(Rain.WIDTH*i);
		}
		bg = new SimpleMeshBackground(Color.GRAY,Color.BLACK);
//		shader = createDefaultShader();
//		mesh = new Mesh(true,4,6,new VertexAttribute(VertexAttributes.Usage.Position, 2, "position"));
//		mesh.setVertices(new float[] { -1, -1,  -1 , 1,  1, -1 , 1 ,1});
//		mesh.setIndices(new short[]{0, 1, 2, 2, 1, 3});
//
//		
//		frameBuffer = new FrameBuffer(Format.RGB565, (int)(Engine.getWidth()/4), (int)(Engine.getHeight()/4), true);
//		resolution.set(frameBuffer.getWidth(), frameBuffer.getHeight());
		
		
//		{
//			GoldTowerEntity tower = new GoldTowerEntity();
//			tower.setPosition(50, 300);
//			tower.addAction(Actions.forever(Actions.sequence(Actions.moveBy(-10, -10,1),Actions.moveBy(10, 10,1))));
//			this.addActor(tower);
//		}
		

		ParticleEffect e = Engine.resource("Effect");
		ParticleEffectActor p = new ParticleEffectActor(e,"about-fire");
		p.setPosition(0, 0);
		this.addActor(p);
		

//		{
//			GoldTowerEntity tower = new GoldTowerEntity();
//			tower.setPosition(900, 150);
//			this.addActor(tower);
//		}
		
		final SpineActor wmr = new SpineActor("null", atlas,"idle",1);
		wmr.setColor(Color.BLACK);
		wmr.setX(240);
		this.addActor(wmr);
		
		pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("grass")), new Vector2(1,0), new Vector2(0,1000), new Vector2(0,0)));
//		DefaultParallaxGroupGestureListener gestureListener=new DefaultParallaxGroupGestureListener(pbg);
//		pbg.setDefaultGestureDetector(gestureListener);
		this.addActor(pbg);
		
//		ParticleEffectActor p2 = new ParticleEffectActor(e,"effect-dot-mu");
//		p2.setPosition(Engine.getWidth(), 100);
//		this.addActor(p2);
		
//		info = new StepInfo();
//		this.addActor(info);
		
//		final Image back = new Image(atlas.findRegion("about-back"));
//		TextureAtlas atlas = Engine.resource("All");
//		BitmapFont font = Engine.resource("BigFont");
//		TextButtonStyle style = new TextButtonStyle(new NinePatchDrawable(atlas.createPatch("btn")), new NinePatchDrawable(atlas.createPatch("btn-click")), null, font);
//		style.fontColor = Color.BLACK;
//		style.downFontColor = Color.RED;
//		final TextButton back = new TextButton("Back",style);
//		back.setColor(Color.GRAY);
//		back.padRight(60);
//		back.pack();
//		back.setPosition(Engine.getWidth()-back.getWidth(),Engine.getHeight()-back.getHeight());
//		this.addActor(back);
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				drive.setToStartUpScene();
				super.clicked(event, x, y);
			}
		});
		
		
	
		TextureRegion[] npcRegions = new TextureRegion[4];
		npcRegions[0] = atlas.findRegion("npc-s1-1");
		npcRegions[1] = atlas.findRegion("npc-s1-2");
		npcRegions[2] = atlas.findRegion("npc-s1-3");
		npcRegions[3] = atlas.findRegion("npc-s1-4");
		Animation npcAnim = new Animation(0.05f, npcRegions);
		Image npcImage = new Image(new AnimationDrawable(npcAnim));
		npcImage.setPosition(420, 40);
		npcImage.setScale(1.5f);
		npcImage.setColor(new Color(Color.GRAY));
		this.addActor(npcImage);
		
		TextureRegion[] kaRegions = new TextureRegion[4];
		kaRegions[0] = atlas.findRegion("npc-ka1");
		kaRegions[1] = atlas.findRegion("npc-ka2");
		kaRegions[2] = atlas.findRegion("npc-ka3");
		kaRegions[3] = atlas.findRegion("npc-ka4");
		Animation kaAnim = new Animation(0.05f, kaRegions);
		Image kaImage = new Image(new AnimationDrawable(kaAnim));
		kaImage.setPosition(400, 40);
		kaImage.setScale(1.2f);
		this.addActor(kaImage);
		
		ticker = new Label("", new LabelStyle(Engine.resource("BigFont", BitmapFont.class), Color.WHITE));
		ticker.setPosition(100, 430);
		this.addActor(ticker);
//		mul.addProcessor(this);
//		mul.addProcessor(pbg.getGestureDetector());
	}
	Label ticker;
	float accum = 0;
	float accum_text = 2;
	int index = 0;
	@Override
	public void act(float delta) {
		accum+=delta;
		accum_text+=delta;
		if(accum_text>3){
			accum_text-=3;
			ticker.addAction(Ticker.obtain(contributes[index%contributes.length],0.5f));
			index ++;
			Engine.getSoundManager().playSound("SoundNewContrib");
		}
		super.act(delta);
	}
	@Override
	public void show() {
		Engine.getMusicManager().playMusic("MusicCont", true);
	}
	public void hide() {
		Engine.getMusicManager().stopMusic("MusicCont");
	};
	Vector2 resolution= new Vector2(Engine.getWidth(),Engine.getHeight());
	@Override
	public void draw() {
//		if(buildSuccess){
//			frameBuffer.begin();
//			Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			Gdx.gl20.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
//			Gdx.gl20.glClearColor(0f, 1f, 0f, 1);
//			Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			shader.begin();
//			shader.setUniformf("resolution", resolution );
//			shader.setUniformf("time", accum);
//			mesh.render(shader, GL20.GL_TRIANGLES);
//			shader.end();
//			frameBuffer.end();
//		
//			Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//			Engine.getSpriteBatch().begin();
//			Engine.getSpriteBatch().setColor(Color.WHITE);
//			Engine.getSpriteBatch().draw(frameBuffer.getColorBufferTexture(), 0, 0,Engine.getWidth(),Engine.getHeight());
//			Engine.getSpriteBatch().end();
//		}
		bg.render(0);
		Engine.getSpriteBatch().begin();
		for(Rain rain:rains){
			rain.render(Engine.getDeltaTime());
		}
		Engine.getSpriteBatch().end();
		super.draw();
		
//		Gdx.gl.glStencilFunc(GL10.GL_ALWAYS, 1, 1);
//		Gdx.gl.glStencilOp(GL10.GL_KEEP, GL10.GL_KEEP, GL10.GL_REPLACE);
//		//do some draw...
//		Gdx.gl.glStencilFunc(GL10.GL_ALWAYS, 0, 1);
//		Gdx.gl.glStencilOp(GL10.GL_KEEP, GL10.GL_KEEP, GL10.GL_KEEP);
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}
//	private ShaderProgram createDefaultShader () {
//		String vertexShader = "attribute vec3 position; void main() { gl_Position = vec4( position, 1.0 ); }";
//		String fragmentShader = "#ifdef GL_ES\r\n" + 
//				"precision highp float;\r\n" + 
//				"#endif\r\n" + 
//				" \r\n" + 
//				"uniform vec2 resolution;\r\n" + 
//				"uniform float time;\r\n" + 
//				" \r\n" + 
//				"void main(void)\r\n" + 
//				"{\r\n" + 
//				"  highp vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / resolution.xy;\r\n" + 
//				" \r\n" + 
//				"  highp float a = atan(p.y, p.x);\r\n" + 
//				"  highp float r = length(p) + 0.0001;\r\n" + 
//				" \r\n" + 
//				"  highp float b = 1.9 * sin(8.0 * r - time - 2.0 * a);\r\n" + 
//				"  b = 0.3125 / r + cos(7.0 * a + b * b) / (100.0 * r);\r\n" + 
//				"  b *= smoothstep(0.2, 0.8, b);\r\n" + 
//				" \r\n" + 
//				"  gl_FragColor = vec4(b, 0.67 * b + 0.1 * sin(a + time), 0.0, 1.0);\r\n" + 
//				"}";
//		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
//		if (shader.isCompiled() == false) {
//			buildSuccess = false;
//			Gdx.app.log("GLSL", "Error compiling shader: " + shader.getLog());
//		}else{
//			buildSuccess = true;
//		}
//		return shader;
//	}
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
	private class Rain extends AdvanceSprite{
		public static final float WIDTH = 15;
		float speed = 0;
		public void setup(){
			speed = Digs.RND.nextFloat()*1000+500;
			this.setY(Engine.getHeight());
		}
		public Rain(TextureRegion texture) {
			super(texture);
			this.setup();
			this.setY(this.getY()+Digs.RND.nextFloat()*200);
		}
		@Override
		public void render(float delta) {
			super.render(delta);
			if(this.getY()<-this.getHeight()){
				this.setup();
			}
			this.setY(this.getY()-speed*delta);
		}
		
	}
//	void disposeShader(){
//		if(null != shader){
//			shader.dispose();
//		}
//		if(null != frameBuffer){
//			frameBuffer.dispose();
//		}
//		if(mesh != null){
//			mesh.dispose();
//		}
//	}
}
