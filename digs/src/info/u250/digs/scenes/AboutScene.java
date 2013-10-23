package info.u250.digs.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class AboutScene implements Scene{
	Animation anim = null;
	Sprite sprite = null;
	ParticleEffectActor p ;
	public AboutScene(){
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
		
		p = new ParticleEffectActor(Engine.resource("Effect",ParticleEffect.class), "effect-dot-mu");
		p.setPosition(Engine.getWidth()-100, 200);
	}
	float accum = 0;
	float x = 0;
	@Override
	public void update(float delta) {
		accum+=delta;
		x += 100*delta;
		sprite.setRegion(anim.getKeyFrame(accum,true));
	}

	@Override
	public void render(float delta) {
		float xx = 960+240-(x%(960-240)+240);
		Engine.getSpriteBatch().begin();
		p.draw(Engine.getSpriteBatch(),1);
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
}
