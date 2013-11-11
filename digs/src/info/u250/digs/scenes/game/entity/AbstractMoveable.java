package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
/* how beautiful the small dot move here and there in the screen 
 * this is what these are
 * You can change the regions at runtime when you need just pass the sequence 
 * to the regions array and when you call tick() method ,
 * it will move a step forward 
 */
public abstract class AbstractMoveable extends Actor{
	protected Level level;
	//a copy of the actor's getX(),getY() in order to make use of the advantage 
	protected float x ,y ;

	protected int direction = Digs.RND.nextBoolean()?1:-1;
	protected int velocity = 1;
	//the drawable item to received region
	protected Sprite drawable = new Sprite();
	//is selected by other things , not can be kill by another instance
	protected boolean readyToDie = false;
	
	TextureRegion[] regions = null;
	int regionsIndex = 0;
	/*
	 * Every frame to move , call this method 
	 */
	public abstract void tick();
	
	public void init(Level terrain){
		this.level = terrain;
	}
	//if our actor has user defined actions
	protected boolean userDefAction(){
		if(this.getActions().size > 0){
			sync();
			return true;
		}
		return false;
	}
	protected void sync(){
		this.setX(x);
		this.setY(y);
		drawable.setRegion(regions[(regionsIndex/2)%regions.length]);
		drawable.setColor(this.getColor());
		drawable.setPosition(x-this.getOriginX(), y-this.getOriginY());
		if(direction<0)drawable.flip(true, false);
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		drawable.draw(batch);
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	public void die(){
		TextureAtlas atlas = Engine.resource("All");
		int size = 8;
		float part = 360f/size;
		for(int i=0;i<size;i++){
			float ax = Digs.RND.nextFloat()*100*MathUtils.cosDeg(i*part);
			float ay = Digs.RND.nextFloat()*100*MathUtils.sinDeg(i*part);
			final Image image = new Image(atlas.findRegion("color"));
			image.setColor(new Color(1,0,0,0.7f));
			image.setPosition(x, y);
			image.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(x+ax, y+ay,0.2f,Interpolation.circleIn),Actions.fadeOut(0.2f)),Actions.run(new Runnable() {
				@Override
				public void run() {
					image.remove();
				}
			})));
			level.addActor(image);
		}
		if(this instanceof Ka){
			level.removeKa(Ka.class.cast(this));
		}else if(this instanceof Npc){
			level.removeNpc(Npc.class.cast(this));
		}else if(this instanceof EnemyMiya){
			level.removeEnemyMiya(EnemyMiya.class.cast(this));
		}
	}
}
