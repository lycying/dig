package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Stepladder extends Table{
	private Rectangle rect = new Rectangle();
	public Stepladder(int number,float x,float y){
		TextureRegion region = Engine.resource("All",TextureAtlas.class).findRegion("ladder");
		for(int i=0;i<number;i++){
			this.add(new Image(new TextureRegionDrawable(region)));
			this.row();
		}
		this.setPosition(x, y);
		this.pack();
		rect.x = this.getX();
		rect.y = this.getY();
		rect.width = this.getPrefWidth();
		rect.height= this.getPrefHeight();
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		super.draw(batch, parentAlpha);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	public Rectangle getRect(){
		return this.rect;
	}
}