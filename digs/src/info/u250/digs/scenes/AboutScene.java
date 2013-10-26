package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.WebColors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class AboutScene implements Scene{
	Animation anim = null;
	Sprite sprite = null;
	ShaderProgram shader ;
	FrameBuffer frameBuffer;
	Mesh mesh ;
	public AboutScene(){
		shader = createDefaultShader();
		mesh = new Mesh(true,4,6,new VertexAttribute(VertexAttributes.Usage.Position, 2, "position"));
		mesh.setVertices(new float[] { -1, -1,  -1 , 1,  1, -1 , 1 ,1});
		mesh.setIndices(new short[]{0, 1, 2, 2, 1, 3});

		
		frameBuffer = new FrameBuffer(Format.RGB565, (int)(Engine.getWidth()/4), (int)(Engine.getHeight()/4), false);
		resolution.set(frameBuffer.getWidth(), frameBuffer.getHeight());
		
		TextureAtlas atlas = Engine.resource("All");
		TextureRegion[] npcRegions = new TextureRegion[4];
		npcRegions[0] = atlas.findRegion("npc1");
		npcRegions[1] = atlas.findRegion("npc2");
		npcRegions[2] = atlas.findRegion("npc3");
		npcRegions[3] = atlas.findRegion("npc4");
		
		anim = new Animation(0.05f, npcRegions);
		sprite = new Sprite(anim.getKeyFrame(0));
		sprite.setPosition(420, 140);
		sprite.setScale(3);
		sprite.setRotation(30);
	}
	float accum = 0;
	float x = 0;
	@Override
	public void update(float delta) {
		accum+=delta;
		x += 100*delta;
		sprite.setRegion(anim.getKeyFrame(accum,true));
	}
	Vector2 resolution= new Vector2(Engine.getWidth(),Engine.getHeight());
	@Override
	public void render(float delta) {
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
		float xx = 960+240-(x%(960-240)+240);
		Engine.getSpriteBatch().begin();
		Engine.getSpriteBatch().draw(frameBuffer.getColorBufferTexture(), 0, 0,Engine.getWidth(),Engine.getHeight());
		Engine.getSpriteBatch().end();
		
		Engine.getShapeRenderer().setColor(WebColors.DARK_KHAKI.get());
		Engine.getShapeRenderer().begin(ShapeType.Filled);
		Engine.getShapeRenderer().triangle(240, 0, Engine.getWidth(), 0, Engine.getWidth(), Engine.getHeight());
		Engine.getShapeRenderer().end();
		
		Gdx.gl.glLineWidth(2);
		Engine.getShapeRenderer().begin(ShapeType.Line);
		Engine.getShapeRenderer().setColor(WebColors.PEACH_PUFF.get());
		Engine.getShapeRenderer().line(240, 0, 960,540);
		Engine.getShapeRenderer().end();
		
		Gdx.gl.glLineWidth(10);
		Engine.getShapeRenderer().begin(ShapeType.Line);
		Engine.getShapeRenderer().setColor(Color.GREEN);
		Engine.getShapeRenderer().line(xx, 540f/(960f-240f)*(xx-240), xx+40,540f/(960f-240f)*(xx+40-240));
		Engine.getShapeRenderer().end();
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
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return null;
	}
	static public ShaderProgram createDefaultShader () {
		String vertexShader = "attribute vec3 position; void main() { gl_Position = vec4( position, 1.0 ); }";
		String fragmentShader = Gdx.files.internal("glsl/vo.fr").readString();

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}
}
