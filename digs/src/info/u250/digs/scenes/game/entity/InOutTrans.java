package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InOutTrans extends Actor{
	Animation in ;
	Animation out ;
	float outx ,outy;
	Sprite line ;
	boolean clear = false;
	private Rectangle rect = new Rectangle();
	public InOutTrans(float inx,float iny,float outx,float outy){
		TextureAtlas atlas = Engine.resource("All",TextureAtlas.class);
		in = new Animation(0.1f,atlas.findRegion("in1"),
				atlas.findRegion("in2"),
				atlas.findRegion("in3"),
				atlas.findRegion("in4"),
				atlas.findRegion("in5"),
				atlas.findRegion("in6"));
		out = new Animation(0.1f,atlas.findRegion("out1"),
				atlas.findRegion("out2"),
				atlas.findRegion("out3"),
				atlas.findRegion("out4"),
				atlas.findRegion("out5"),
				atlas.findRegion("out6"));
		line = new Sprite(atlas.findRegion("color"));
		this.outx = outx;
		this.outy = outy;
		line.setSize(4, 4);
		line.setPosition(inx+37, iny+20);
		line.setOrigin(0, 2);
		line.setColor(new Color(1,1,1,0.5f));
		Vector2 v = new Vector2(outx+3,outy+20).sub(inx+37,iny+20);
		line.setScale(v.len()/4, 1);
		line.setRotation(v.angle());
		this.setPosition(inx, iny);
		
		rect.x = inx+8;
		rect.y = iny+8;
		rect.width = 24;
		rect.height = 24;
	}
	float stateTime = 0;
	@Override
	public void act(float delta) {
		stateTime += delta;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		line.draw(batch);
		batch.draw(in.getKeyFrame(stateTime, true), getX(),getY());
		batch.draw(out.getKeyFrame(stateTime, true),outx,outy);
	}
	public Rectangle getRect(){
		return this.rect;
	}
	public float getTransX(){
		return this.outx;
	}
	public float getTransY(){
		return this.outy;
	}
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	
}
