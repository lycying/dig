package info.u250.digs.scenes.ui;

import info.u250.c2d.accessors.FloatValueAccessor;
import info.u250.c2d.accessors.FloatValueAccessor.FloatValue;
import info.u250.c2d.engine.Engine;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ProgressBar extends Group {
	protected float fullValue;
	protected FloatValue currentValue;

	protected NinePatch bg;
	protected NinePatch fg;

	public float getFullValue() {
		return fullValue;
	}

	public void setFullValue(float fullValue) {
		this.fullValue = fullValue;
	}

	public FloatValue getCurrentValue() {
		return currentValue;
	}
	

	public ProgressBar(NinePatch bg,NinePatch fg) {
		this.bg = bg;
		this.fg = fg;

		currentValue = new FloatValue();
	}

	public void setup(float width, float height) {
		this.setWidth(width) ;
		this.setHeight(height);
	}

	public void setupColor(Color colorBg, Color colorFg) {
		bg.setColor(new Color(colorBg));
		fg.setColor(new Color(colorFg));
	}

	public void countAppend(float upValue, float duration) {
		countTo(currentValue.getValue() + upValue, duration);
	}

	public void countTo(float toValue, float duration) {
		if(currentValue.getValue()!=toValue){
			Tween.to(currentValue, FloatValueAccessor.VALUE, duration * 1000)
			.target(toValue).start(Engine.getTweenManager());
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		float x = this.getX();
		float y = this.getY();
		float width = this.getWidth() * ((currentValue.getValue()) / fullValue);
		float height = this.getHeight() ;
		bg.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		if(width>8)fg.draw(batch,x,y,width,height);
		
	}
}
