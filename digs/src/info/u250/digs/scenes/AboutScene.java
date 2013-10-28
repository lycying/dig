package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.c2d.graphic.surfaces.SurfaceData;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.digs.Digs;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.about.StepInfo;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class AboutScene extends SceneStage{
	DigsEngineDrive drive;
	Animation anim = null;
	Sprite sprite = null;
	ShaderProgram shader ;
	FrameBuffer frameBuffer;
	Mesh mesh ;
	TriangleSurfaces surface;
	StepInfo info ;
	String[] contributes = new String[]{
			"--Credits--",
			"Programming: Lycying",
			"Artwork and Graphics: Anny",
			"Music: Anyone?",
			"Sound Effects: Lycying",
			"Story: Lycying",
			"Idea: Miner4k \n https://mojang.com/notch/j4k2k6/miners4k/",
			"Engine: Libgdx,C2d-engine",
			"Blog: http://www.u250.info"
	};
	public AboutScene(final DigsEngineDrive drive){
		this.drive = drive;
		shader = createDefaultShader();
		mesh = new Mesh(true,4,6,new VertexAttribute(VertexAttributes.Usage.Position, 2, "position"));
		mesh.setVertices(new float[] { -1, -1,  -1 , 1,  1, -1 , 1 ,1});
		mesh.setIndices(new short[]{0, 1, 2, 2, 1, 3});

		
		frameBuffer = new FrameBuffer(Format.RGB565, (int)(Engine.getWidth()/4), (int)(Engine.getHeight()/4), true);
		resolution.set(frameBuffer.getWidth(), frameBuffer.getHeight());
		
		TextureAtlas atlas = Engine.resource("All");
		TextureRegion[] npcRegions = new TextureRegion[4];
		npcRegions[0] = atlas.findRegion("npc1");
		npcRegions[1] = atlas.findRegion("npc2");
		npcRegions[2] = atlas.findRegion("npc3");
		npcRegions[3] = atlas.findRegion("npc4");
		
		anim = new Animation(0.05f, npcRegions);
		sprite = new Sprite(anim.getKeyFrame(0));
		sprite.setPosition(420, 40);
		sprite.setScale(1.5f);
		sprite.setColor(new Color(Color.GRAY));
		
		final SurfaceData data2 = new SurfaceData();
		data2.primitiveType = GL10.GL_TRIANGLE_FAN;
		data2.texture="Texture";
		data2.points = new Array<Vector2>(){{
			add(new Vector2(0,0));
			add(new Vector2(0,100));
			add(new Vector2(Engine.getWidth(),100));
			add(new Vector2(Engine.getWidth(),0));
		}};
		surface  = new TriangleSurfaces(data2);
		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(50, 300);
			tower.addAction(Actions.forever(Actions.sequence(Actions.moveBy(-10, -10,1),Actions.moveBy(10, 10,1))));
			this.addActor(tower);
		}
		

		ParticleEffect e = Engine.resource("Effect");
		ParticleEffectActor p = new ParticleEffectActor(e,"about-fire");
		p.setPosition(0, 0);
		this.addActor(p);
		

		{
			GoldTowerEntity tower = new GoldTowerEntity();
			tower.setPosition(900, 150);
			this.addActor(tower);
		}
		
		
		final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
		pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("grass")), new Vector2(1,1), new Vector2(0,1000), new Vector2(0,0)));
		this.addActor(pbg);
		
		ParticleEffectActor p2 = new ParticleEffectActor(e,"effect-dot-mu");
		p2.setPosition(Engine.getWidth(), 100);
		this.addActor(p2);
		
		info = new StepInfo();
		this.addActor(info);
		
		final Image back = new Image(atlas.findRegion("about-back"));
		back.setPosition(Engine.getWidth()-back.getWidth(),Engine.getHeight()-back.getHeight());
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				drive.setToStartUpScene();
				super.clicked(event, x, y);
			}
		});
		this.addActor(back);
		
	}
	float accum = 0;
	float accum_text = 2;
	int index = 0;
	@Override
	public void act(float delta) {
		accum+=delta;
		accum_text+=delta;
		sprite.setRegion(anim.getKeyFrame(accum,true));
		if(accum_text>5){
			accum_text-=5;
			info.play(Digs.RND.nextInt(2), contributes[index%contributes.length]);
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
		GL20 gl = Gdx.gl20;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		frameBuffer.begin();
		Gdx.gl20.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
		Gdx.gl20.glClearColor(0f, 1f, 0f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shader.begin();
		shader.setUniformf("resolution", resolution );
		shader.setUniformf("time", accum);
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();
		frameBuffer.end();
	
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Engine.getSpriteBatch().begin();
		Engine.getSpriteBatch().setColor(Color.WHITE);
		Engine.getSpriteBatch().draw(frameBuffer.getColorBufferTexture(), 0, 0,Engine.getWidth(),Engine.getHeight());
		Engine.getSpriteBatch().end();
//		surface.render(0);
		super.draw();
		Engine.getSpriteBatch().begin();
		sprite.draw(Engine.getSpriteBatch());
		Engine.getSpriteBatch().end();
		
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
	static public ShaderProgram createDefaultShader () {
		String vertexShader = "attribute vec3 position; void main() { gl_Position = vec4( position, 1.0 ); }";
		String fragmentShader = "#ifdef GL_ES\r\n" + 
				"precision highp float;\r\n" + 
				"#endif\r\n" + 
				" \r\n" + 
				"uniform vec2 resolution;\r\n" + 
				"uniform float time;\r\n" + 
				" \r\n" + 
				"void main(void)\r\n" + 
				"{\r\n" + 
				"  // Be Cool\r\n" + 
				"  vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / resolution.xy;\r\n" + 
				" \r\n" + 
				"  float a = atan(p.y, p.x);\r\n" + 
				"  float r = length(p) + 0.0001;\r\n" + 
				" \r\n" + 
				"  float b = 1.9 * sin(8.0 * r - time - 2.0 * a);\r\n" + 
				"  b = 0.3125 / r + cos(7.0 * a + b * b) / (100.0 * r);\r\n" + 
				"  b *= smoothstep(0.2, 0.8, b);\r\n" + 
				" \r\n" + 
				"  gl_FragColor = vec4(b, 0.67 * b + 0.1 * sin(a + time), 0.0, 1.0);\r\n" + 
				"}\r\n" + 
				"";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
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
