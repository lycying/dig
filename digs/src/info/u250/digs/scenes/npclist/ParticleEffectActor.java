package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {

	private ParticleEffect emitter;
	private boolean spiral;
	private float r = 0;

	protected ParticleEffectActor(ParticleEffect emitter) {
		this.emitter = emitter;
		this.spiral = false;
	}

	public ParticleEffect getEmitter() {
		return emitter;
	}

	public void setEmitter(ParticleEffect emitter) {
		this.emitter = emitter;
	}

	public void setSpiral(boolean spiral) {
		this.spiral = spiral;
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
			if (this.getRotation() == 0) {
				this.emitter.setPosition(this.getX(), this.getY());
				this.emitter.draw(batch, Engine.getDeltaTime());
			} else {
				final float cos = (float) Math.cos(this.getRotation()* MathUtils.degreesToRadians);
				final float sin = (float) Math.sin(this.getRotation()* MathUtils.degreesToRadians);

				float x;
				float y;

				if (spiral) {
					if (r == 0) {
						r = Math.abs(this.getOriginX()) + 20;
					}
					r -= Engine.getDeltaTime() * 100;
					if (r < 0) {
						r = -0.1f;
					}

					x = this.getX() - (Math.abs(this.getOriginX()) + 20 - r * cos);
					y = this.getY() + r * sin;
				} else {
					r = Math.abs(this.getOriginX()) + 20;
					x = this.getX() - (r - r * cos);
					y = this.getY() + r * sin;
				}

				this.emitter.setPosition(x, y);
				this.emitter.draw(batch, Engine.getDeltaTime());
			}

		}
	}
}
