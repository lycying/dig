package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LineActor extends Actor{
	Sprite sprite ;
	public LineActor(float fromX,float fromY,float toX,float toY){
		final TextureAtlas atlas = Engine.resource("All");
		sprite = new Sprite(atlas.findRegion("color"));
		final Vector2 v1 = new Vector2(fromX,fromY);
		final Vector2 v2 = new Vector2(toX,toY);
		float dst = v1.dst(v2);
		float angle = v2.sub(v1).angle();
		sprite.setSize(dst, 4);
		sprite.setOrigin(0, 2);
		sprite.setRotation(angle);
		sprite.setPosition(fromX, fromY);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setColor(this.getColor());
		sprite.draw(batch);
	}
}
