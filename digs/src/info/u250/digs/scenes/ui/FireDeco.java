package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class FireDeco extends Group{
	public FireDeco(){
		this.setSize(72, 100);
		final TextureAtlas atlas = Engine.resource("All");
		final Image bottom = new Image(atlas.findRegion("fire-bottom"));
		this.addActor(bottom);
		
		ParticleEffect e = Engine.resource("Effect");
		ParticleEffectActor p = new ParticleEffectActor(e,"fire");
		p.setPosition(36, 40);
		this.addActor(p);
	}
}
