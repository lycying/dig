package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Water {
	static final class WaterColumn {
		public float TargetHeight;
		public float Height;
		public float Speed;

		public void Update(float dampening, float tension) {
			float x = TargetHeight - Height;
			Speed += tension * x - Speed * dampening;
			Height += Speed;
		}
	}
	public Water(int segment,float height,Color topColor,Color endColor) {
		columns = new WaterColumn[segment];

		for (int i = 0; i < columns.length; i++) {
			columns[i] = new WaterColumn();
			columns[i].Height = height;
			columns[i].TargetHeight = height;
			columns[i].Speed = 0;

		}
		scale = Engine.getWidth() / (columns.length -1);
		this.color1 = endColor;
		this.color2 = topColor;

	}
	Color color1 = new Color(0.2f, 0.7f, 1f, 0.9f);
	Color color2 = new Color(0.2f, 0.5f, 1f, 0.8f);
	public float tension = 0.025f;
	public float dampening = 0.025f;
	public float spread = 0.25f;
	int tinkness = 4; 
	float scale = 0;
	Random rnd = new Random();

	WaterColumn[] columns = null;

	public void tick() {
		for (int i = 0; i < columns.length; i++)columns[i].Update(dampening, tension);

		float[] lDeltas = new float[columns.length];
		float[] rDeltas = new float[columns.length];

		// do some passes where columns pull on their neighbours
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < columns.length; i++) {
				if (i > 0) {
					lDeltas[i] = spread * (columns[i].Height - columns[i - 1].Height);
					columns[i - 1].Speed += lDeltas[i];
				}
				if (i < columns.length - 1) {
					rDeltas[i] = spread * (columns[i].Height - columns[i + 1].Height);
					columns[i + 1].Speed += rDeltas[i];
				}
			}

			for (int i = 0; i < columns.length; i++) {
				if (i > 0)
					columns[i - 1].Height += lDeltas[i];
				if (i < columns.length - 1)
					columns[i + 1].Height += rDeltas[i];
			}
		}

	}
	
	public void draw() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		final ImmediateModeRenderer renderer = Engine.getShapeRenderer().getRenderer();
		Engine.getShapeRenderer().begin(ShapeType.Filled);
		for (int i = 1; i < columns.length; i++) {

			Vector2 p1 = new Vector2((i - 1) * scale, 0);
			Vector2 p2 = new Vector2((i - 1) * scale, columns[i - 1].Height);
			Vector2 p3 = new Vector2(i * scale, columns[i].Height);
			Vector2 p4 = new Vector2(i * scale, 0);

			renderer.color(color1.r, color1.g, color1.b, color1.a);
			renderer.vertex(p1.x, p1.y, 0);
			renderer.color(color2.r, color2.g, color2.b, color2.a);
			renderer.vertex(p2.x, p2.y, 0);
			renderer.color(color2.r, color2.g, color2.b,color2.a);
			renderer.vertex(p3.x, p3.y, 0);

			renderer.color(color2.r, color2.g, color2.b, color2.a);
			renderer.vertex(p3.x, p3.y, 0);
			renderer.color(color1.r, color1.g, color1.b, color1.a);
			renderer.vertex(p4.x, p4.y, 0);
			renderer.color(color1.r, color1.g, color1.b,color1.a);
			renderer.vertex(p1.x, p1.y, 0);
			
			
			renderer.color(color1.r, color1.g, color1.b, color1.a*0.3f);
			renderer.vertex(p1.x, p2.y-tinkness, 0);
			renderer.color(color2.r, color2.g, color2.b, color2.a*0.3f);
			renderer.vertex(p2.x, p2.y, 0);
			renderer.color(color2.r, color2.g, color2.b,color2.a*0.3f);
			renderer.vertex(p3.x, p3.y, 0);

			renderer.color(color2.r, color2.g, color2.b, color2.a*0.3f);
			renderer.vertex(p3.x, p3.y, 0);
			renderer.color(color1.r, color1.g, color1.b, color1.a*0.3f);
			renderer.vertex(p4.x, p3.y-tinkness, 0);
			renderer.color(color1.r, color1.g, color1.b,color1.a*0.3f);
			renderer.vertex(p1.x, p2.y-tinkness, 0);
		}
		Engine.getShapeRenderer().end();
		
	}
	
	public void splash(float xPosition, float speed) {
		int index = (int) MathUtils.clamp(xPosition / scale, 0,columns.length - 1);
		for (int i = Math.max(0, index - 0); i < Math.min(columns.length - 1,index + 1); i++){
			columns[index].Speed = speed;
		}

	}

	static float ACC = 1.0f / 40.0f;
	float deltaAppend = 0;
	public void update(float delta){
		deltaAppend+=delta;
		while(deltaAppend>=ACC){
			this.tick();
			deltaAppend -= ACC;
		}
	}
}
