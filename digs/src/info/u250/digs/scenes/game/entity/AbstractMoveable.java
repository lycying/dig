package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	public static float HURT_SOUND_CTL = 0;
	public static float COIN_SOUND_CTL = 0;
	public static float DIE_SOUND_CTL = 0;
	public static float TRANS_SOUND_CTL = 0;
	
	protected Level level;
	//a copy of the actor's getX(),getY() in order to make use of the advantage 
	protected float x ,y ;
	//used for ladder
	boolean downDownDown = true;
	//for x
	protected int direction = Digs.RND.nextBoolean()?1:-1;
	//for y
	protected int velocity = 1;
	//the drawable item to received region
	protected Sprite drawable = new Sprite();
	//is selected by other things , not can be kill by another instance
	protected boolean readyToDie = false;
	//show the ready to die flag
	protected Sprite info ;
	
	TextureRegion[] regions = null;
	int regionsIndex = 0;
	public AbstractMoveable(){
		this.info = new Sprite(Engine.resource("All",TextureAtlas.class).findRegion("info"));
	}
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
	public void draw(Batch batch, float parentAlpha) {
		drawable.draw(batch);
		if(readyToDie){
			info.setPosition(drawable.getX()+drawable.getWidth()/2-info.getWidth()/2, drawable.getY()+drawable.getHeight());
			info.draw(batch);
		}
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	public void setDirection(int direction) {
		this.direction = direction;
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
		}else if(this instanceof Boss){
			level.removeBoss(Boss.class.cast(this));
		}
	}
	boolean tryTransPort(){		
		for(final TeleportEntity inout:level.getInouts()){
			if(inout.getRect().contains(x,y)){
				x = inout.getRect().x+37;
				y = inout.getRect().y+20;
				sync();
				if(TRANS_SOUND_CTL>0.2f){
					Engine.getSoundManager().playSound("SoundTrans");
					TRANS_SOUND_CTL = 0;
				}
				this.addAction(Actions.sequence(Actions.moveTo(
						inout.getTransX()+15+15*Digs.RND.nextFloat(), 
						inout.getTransY()+15+15*Digs.RND.nextFloat(),
						0.8f),Actions.run(new Runnable() {
					@Override
					public void run() {
						sync();
					}
				})));
				if(!inout.isClear()){
					inout.setClear(true);
					level.clearTransPort(inout.getTransX()+5, inout.getTransY()+40-5, 30);
				}
				return true;
			}
		}
		return false;
	}
	boolean tryKillRay(){
		for(KillCircleEntity kill:level.getKillrays()){
			if(kill.overlaps(x, y) || kill.overlaps(x, y+8)){//the bottom and top
				die();
				if(DIE_SOUND_CTL>0.2f){
					Engine.getSoundManager().playSound("SoundDie");
					DIE_SOUND_CTL = 0;
				}
				return true;
			}
		}
		return false;
	}
	boolean tryClampLadder(){
		for(StepladderEntity ladder:level.getLadders()){
			if(ladder.getRect().contains(x, y)){
				//justJumpDown==true is the NPC is clamp down or it is clamp up
				if(downDownDown){
					if(y+this.getHeight()<ladder.getRect().y+1){
						//judge if the bellow is empty
						if(level.tryMove(x, y-1)){
							y--;
						}else{
							downDownDown = false;
							y+=2;
						}
					}else{
						y--;
					}
				}else{
					if(y>ladder.getRect().y+ladder.getRect().height-1){
						//judge if the top is empty
						if(level.tryMove(x, y+1)){
							y++;
							velocity = 16;
						}else{
							downDownDown = true;
							y-=2;
						}
					}else{
						y++;
					}
				}
				x = ladder.getRect().x + (ladder.getPrefWidth())/2;
				sync();
				return true;
			}
		}
		return false;
	}
}
