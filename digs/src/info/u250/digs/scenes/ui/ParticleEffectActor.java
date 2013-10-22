package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {

	private ParticleEffect emitter;
	
	public ParticleEffectActor(ParticleEffect pemitter,String name) {
		this.emitter = new ParticleEffect();
		this.emitter.getEmitters().add(pemitter.findEmitter(name));
	}

	public ParticleEffect getEmitter() {
		return emitter;
	}

	public void setEmitter(ParticleEffect emitter) {
		this.emitter = emitter;
	}

	@Override
	public void setColor(Color color) {
		for (ParticleEmitter emi : emitter.getEmitters()) {
			float[] c = { color.r, color.g, color.b };
			emi.getTint().setColors(c);
		}
		super.setColor(color);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (parentAlpha == 1) {
			this.emitter.setPosition(this.getX(), this.getY());
			this.emitter.draw(batch, Engine.getDeltaTime());
		}
	}
}
