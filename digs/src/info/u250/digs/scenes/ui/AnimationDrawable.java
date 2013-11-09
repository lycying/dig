package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class AnimationDrawable extends BaseDrawable{
	private Sprite sprite;
	private Animation animation;
	public AnimationDrawable (Animation animation) {
		this.animation = animation;
		setSprite(new Sprite(animation.getKeyFrame(0)));
	}

	float accum = 0;
	@SuppressWarnings("deprecation")
	public void draw (SpriteBatch batch, float x, float y, float width, float height) {
		accum += Engine.getDeltaTime();
		sprite.setRegion(animation.getKeyFrame(accum, true));
		sprite.setBounds(x, y, width, height);
		Color color = sprite.getColor();
		sprite.setColor(Color.tmp.set(color).mul(batch.getColor()));
		sprite.draw(batch);
		sprite.setColor(color);
	}

	public void setSprite (Sprite sprite) {
		this.sprite = sprite;
		setMinWidth(sprite.getWidth());
		setMinHeight(sprite.getHeight());
	}

	public Sprite getSprite () {
		return sprite;
	}
}
