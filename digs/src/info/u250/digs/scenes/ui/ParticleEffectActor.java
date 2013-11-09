package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {

	private ParticleEffect emitter;
	private boolean pauseWithEngine = false;
	
	public ParticleEffectActor(ParticleEffect pemitter,String... name) {
		this.emitter = new ParticleEffect();
		for(String s :name){
			this.emitter.getEmitters().add(new ParticleEmitter(pemitter.findEmitter(s)));
		}
	}

	public ParticleEffect getEmitter() {
		return emitter;
	}

	public void setEmitter(ParticleEffect emitter) {
		this.emitter = emitter;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(this.isVisible()){
			if (parentAlpha == 1) {
				if(Engine.isPause() && pauseWithEngine){}else{
					this.emitter.setPosition(this.getX(), this.getY());
					this.emitter.draw(batch, Engine.getDeltaTime());
				}
			}
		}
	}

	public boolean isPauseWithEngine() {
		return pauseWithEngine;
	}

	public void setPauseWithEngine(boolean pauseWithEngine) {
		this.pauseWithEngine = pauseWithEngine;
	}
	
}
