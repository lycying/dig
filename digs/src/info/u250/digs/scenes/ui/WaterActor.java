package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WaterActor extends Actor{
	Water water ;
	public WaterActor(float height,Color topColor,Color endColor ){
		water = new Water( 201, height, topColor, endColor);
		this.setSize(Engine.getWidth(), height);
	}
	float accum = 0;
	@Override
	public void act(float delta) {
		accum += delta;
		if(accum>0.5f){
			accum -=0.5f;
			water.splash(Engine.getWidth()*Digs.RND.nextFloat(), 80f*Digs.RND.nextFloat());
		}
		water.update(delta);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		water.setyOffset(this.getParent().getY());
		batch.end();
		water.draw();
		batch.begin();
	}
}
