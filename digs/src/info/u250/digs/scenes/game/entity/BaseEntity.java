package info.u250.digs.scenes.game.entity;

import info.u250.digs.scenes.game.Dock;
import info.u250.digs.scenes.game.Level;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseEntity extends Actor {

	enum NpcStatus{
		Walk,
		GoldWalk,
	}
	public BaseEntity(){
		this.setSize(7, 10);
		this.drawable.setSize(7, 10);
	}

	private Random random   = new Random();
	float x ,y ;
	public int speedX = random.nextBoolean()?1:-1;
	public int speedY = 1;
	private float stateTime; 
	private boolean isHoldGold = false;
	
	protected NpcStatus status = NpcStatus.Walk;
	protected Animation animation ;
	public Sprite drawable = new Sprite();

	
	
	public int goldHold = 1;
	public int cost = 50;
		
	//the main terrain
	protected Level terrain;
	public void init(Level terrain){
		this.terrain = terrain;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		drawable.draw(batch);
	}
	
	
	public void tick(){
		if(null == terrain)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX();
		y = this.getY();
		if(x<8)speedX = 1; 
		if(x>terrain.getWidth()-10) speedX=-1;
		if (speedY > 1) {
			if (terrain.tryMove(x+speedX, y + speedY / 8)) {
				x += speedX;
				y += speedY / 8;
				speedY--;
			} else {
				// Stop jumping
				speedY = 0;
			}
		} else {
			if (terrain.tryMove(x, y-1)) {
				y--;
				if (terrain.tryMove(x, y-1)) {
					y--;
					speedY = 0;
				}
			} else	{

				if (random.nextInt(10) != 0) {
					// Assume the miner has hit a wall
					boolean hit = true;
					for (int p = 1; p <= 4; p++) {
						if (terrain.tryMove(x+speedX, y+p)) {
							x += speedX;
							y += p;
							hit = false;
							break;
						}
					}
					if (random.nextInt(hit ? 10 : 4000) == 0) {
						speedX *= -1;
						if (hit) {
							if(random.nextInt(3)!=0)speedY = 16;
						}else{
							if(random.nextInt(3)==0)speedY = 16;
						}
					}
				}
			}
		}

		this.setX(x);
		this.setY(y);

      		
		switch (status) {
		case Walk:
			animation = this.speedX>0?getWalkAnimationRight():getWalkAnimationLeft();
			break;
		case GoldWalk:
			animation = this.speedX>0?getGoldAnimationRight():getGoldAnimationLeft();
			break;
		default:
			break;
		}
	}
	@Override
	public void act(float delta) {
		/*============Put Down Gold ================================*/
		if(isHoldGold){
			for(Dock dock:terrain.docks){
				if(this.drawable.getBoundingRectangle().overlaps(dock.actor.getBoundingRectangle())){
					isHoldGold = false;
					this.speedX = - this.speedX;
					status = NpcStatus.Walk;
					dock.number++;
				}
			}
		}
		
	
		stateTime+=delta;
		drawable.setRegion(animation.getKeyFrame(stateTime, true));
		drawable.setColor(this.getColor());
		drawable.setPosition(this.getX(), this.getY());
		drawable.setScale(this.getScaleX(),this.getScaleY());	
		
		
		
	}
	
	void doDig(float x,float y,float r){
		this.speedX = -this.speedX;
		terrain.dig(r, x, y);
		
		isHoldGold = true;
		status = NpcStatus.GoldWalk;
	}
	
	
	
	public final void setAnimation(Animation animation){
		this.animation = animation;
	}
	
	public abstract Animation getWalkAnimationLeft();
	public abstract Animation getWalkAnimationRight();
	public abstract Animation getGoldAnimationLeft();
	public abstract Animation getGoldAnimationRight();
	public abstract Animation getSkillAnimationLeft();
	public abstract Animation getSkillAnimationRight();
	protected TextureRegion flipRegion(TextureRegion region){
		region.flip(true, false);
		return region;
	}
	private int nextAnimationIndex = 0;
	public void setNextAnimationForShow(){
		if(nextAnimationIndex>=3)nextAnimationIndex = 0;
		if(0==nextAnimationIndex){
			nextAnimationIndex++;
			if(null != getGoldAnimationRight()){
				this.animation = getGoldAnimationRight();
				return;
			}
		}
		if(1==nextAnimationIndex){
			nextAnimationIndex++;
			if(null != getSkillAnimationRight()){
				this.animation = getSkillAnimationRight();
				return;
			}
		}
		if(2==nextAnimationIndex){
			nextAnimationIndex++;
			if(null != getWalkAnimationRight()){
				this.animation = getWalkAnimationRight();
				return;
			}
		}
	}

	
	public Sprite getDrawable(){
		return drawable;
	}
}
