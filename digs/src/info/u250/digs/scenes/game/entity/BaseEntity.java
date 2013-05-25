package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.game.Dock;
import info.u250.digs.scenes.game.Terrain;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public abstract class BaseEntity extends Actor {
	enum NpcStatus{
		Walk,
		GoldWalk,
		Attack,
		Heal
	}
	public BaseEntity(){
		this.setSize(16, 16);
		this.drawable.setSize(16, 16);
	}
	Label hpLabel ;
	private float margin = 5;
	private Rectangle tempRectangle = new Rectangle();
	private Sprite drawable = new Sprite();
	private Random random   = new Random();
	private float speedX = 0;
	private float speedY = 0;
	private float stateTime; 
	private float gravitySpeedDelta = 0;
	private float tick = 0;
	private int currentHP     = 0;
	private boolean isActtacking = false;
	private boolean isHealing = false;
	private boolean isHoldGold = false;
	private BaseEntity attackAim ;
	private BaseEntity healAim ;
	private Vector2 tmp = new Vector2();
	
	protected NpcStatus status = NpcStatus.Walk;
	protected Animation animation ;
	
	public boolean self = true;
	public float speed = 40;
	public int attack = 0;
	public int attackRange = 0;
	public int heal   = 0;
	public int healRange   = 0;
	public int hp     = 0;
	public int goldHold = 1;
	public boolean defense = false;
	public int cost = 50;
	
	//the main terrain
	protected Terrain terrain;
	
	
	public void init(Terrain terrain){
		this.terrain = terrain;
		this.currentHP = hp;
		this.tick = random.nextFloat();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		drawable.draw(batch);
		if(defense || hp == 0){}else{
			if(currentHP<hp){
				Engine.getDefaultFont().setColor(Color.YELLOW);
			}else{
				Engine.getDefaultFont().setColor(Color.WHITE);
			}
			Engine.getDefaultFont().setScale(0.8f, 0.8f);
			Engine.getDefaultFont().draw(batch, currentHP+"", getX()+5, getY()+26);
			Engine.getDefaultFont().setColor(Color.WHITE);
		}
	}
	@Override
	public void act(float delta) {
		switch (status) {
		case Walk:
			animation = this.speedX>0?getWalkAnimationRight():getWalkAnimationLeft();
			break;
		case Attack:
			animation = this.getX()>this.attackAim.getX()?getSkillAnimationLeft():getSkillAnimationRight();
			break;
		case Heal:
			animation = this.getX()>this.healAim.getX()?getSkillAnimationLeft():getSkillAnimationRight();
			break;
		default:
			break;
		}
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
		
		/*============Put Down Gold ================================*/
		
		/*============Change the animation =========================*/
		stateTime+=delta;
		drawable.setRegion(animation.getKeyFrame(stateTime, true));
		drawable.setColor(this.getColor());
		drawable.setPosition(this.getX(), this.getY());
		drawable.setScale(this.getScaleX(),this.getScaleY());
		/*============Change the animation =========================*/
		
		
		if(null == terrain)return ;//if terrain is null , break it 
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		
		
		//reset it~~
		isActtacking = false;
		isHealing = false;
		status = NpcStatus.Walk;
		
		for(Actor actor:terrain.getChildren()){
			if(actor == this)continue;
			if(actor instanceof BaseEntity){
				BaseEntity e = BaseEntity.class.cast(actor);
				float dst = tmp.set(getX(),getY()).dst(e.getX(), e.getY());
				
				//if the enemy is not defensed and is not our patterns , then i will try attack it
				//i should have attack that bigger than zero and have not in attacking
				if(e.currentHP>0 && !e.defense && !self && attack>0){
					if(dst<attackRange){
						isActtacking = true;
						attackAim = e;
						status = NpcStatus.Attack;
						followNpc(e);
					}
				}
				//heal
				if(e.currentHP<e.hp && heal>0 && !isHealing){
					if(dst<healRange){
						isHealing = true;
						healAim = e;
						status = NpcStatus.Heal;
						followNpc(e);
					}
				}
			}
		}
		tick+=delta;
		if(tick>1f){
			tick -= 1f;
			if(isActtacking){
				this.doAttack();
			}
			if(isHealing){
				this.doAHeal();
			}
		}
		
		
		if(this.getX()<=margin){
			speedX = getRandomSpeedX() ;
		} else if(this.getX()>terrain.getWidth()-this.getWidth()-margin){
			speedX = -getRandomSpeedX();
		} else if(this.getY()>terrain.getHeight()-this.getHeight() - margin){
			this.gravityDrop(delta);
		}else{
			//calculate it
			tempRectangle.set(drawable.getBoundingRectangle());
			tempRectangle.x += delta*speedX;
			tempRectangle.y += delta*speedY;
			terrain.calculateSpriteRectColor(tempRectangle);
			
			//drop until land the ground
			if(terrain.isSpace()){
				this.gravityDrop(delta);
			}else{
				if(goldHold>0)//if this npc can hold gold
				if(!isHoldGold){
					final float r = 4;
					if(speedX < 0){
						if(!terrain.isLeftBottomSpace_gold()){
							this.doDig(this.getX(), this.getY(),r);
						}else if(!terrain.isLeftTopSpace_gold()){
							this.doDig(this.getX(), this.getY()+this.getWidth(), r);
						}
					}else if(speedX > 0){
						 if(!terrain.isRightBottomSpace_gold()){
							this.doDig(this.getX()+this.getWidth(), this.getY(),r);
						 }else if(!terrain.isRightTopSpace_gold()){
							this.doDig(this.getX()+this.getWidth(), this.getY()+this.getHeight(), r);
						 }
					}
				}
				
				if(this.speedX == 0){
					this.speedX  = (random.nextBoolean() ? 1: -1) * getRandomSpeedX() ;
				}
				if(this.speedX <0){
					if(this.speedY<0){
						if(!terrain.isLeftBottomSpace()){
							this.speedY = -this.speedX;
						}
					}else{
						if(!terrain.isLeftTopSpace()){
							this.speedY = this.speedX;
							this.speedX = -this.speedX;
						}
					}
				}else{
					if(this.speedY<0){
						if(!terrain.isRightBottomSpace()){
							this.speedY = this.speedX;
						}
					}else{
						if(!terrain.isRightTopSpace()){
							this.speedY = -this.speedX;
							this.speedX = -this.speedX;
						}
					}
				}
				this.gravitySpeedDelta = 0;
			}
		}
		
		
		if(terrain.isBlocked()){
			return;
		}
		
		this.translate((isActtacking||isHealing)?0:delta*speedX, delta*speedY);
		super.act(delta);
		
	}
	protected void gravityDrop(float delta){
		this.gravitySpeedDelta+= delta;
		this.speedY = -500*this.gravitySpeedDelta;
	}
	
	private void doDig(float x,float y,float r){
		this.speedX = -this.speedX;
		terrain.dig(r, x, y);
		
		isHoldGold = true;
		status = NpcStatus.GoldWalk;
	}
	
	private float getRandomSpeedX(){
		return speed +50*random.nextFloat();
	}
	public void doAttack(){
		attackAim.currentHP-=attack;
		if(attackAim.currentHP<0)attackAim.currentHP = 0;
		if(attackAim.currentHP==0){
			final BaseEntity ee = attackAim;
			ee.addAction(Actions.sequence(Actions.fadeOut(1),Actions.run(new Runnable() {
				@Override
				public void run() {
					ee.remove();
				}
			})));
		}
		
		TextureAtlas atlas = Engine.resource("All");
		final Image fire = new Image(atlas.findRegion("color"));
		fire.setColor(new Color(1, 0, 0, 0.5f));
		fire.setPosition(getX()+6, getY()+6);
		float dst = tmp.set(getX(),getY()).dst(attackAim.getX(), attackAim.getY());
		float angle = tmp.set(getX(),getY()).sub(attackAim.getX(),attackAim.getY()).angle();
		float scale = dst/4f;
		fire.setRotation(angle);
		fire.setOrigin(4, 2);
		fire.addAction(Actions.sequence(Actions.scaleTo(scale,0.5f,0.3f,Interpolation.circleOut),Actions.run(new Runnable() {
			@Override
			public void run() {
				fire.remove();
			}
		})));
		terrain.addActor(fire); 
	}
	public void doAHeal(){
		healAim.currentHP+=heal;
		if(healAim.currentHP>healAim.hp)healAim.currentHP = healAim.hp;
		
		TextureAtlas atlas = Engine.resource("All");
		final Image fire = new Image(atlas.findRegion("color"));
		fire.setColor(new Color( 0, 1 , 0 , 0.5f));
		fire.setPosition(getX()+6, getY()+6);
		float dst = tmp.set(getX(),getY()).dst(healAim.getX(), healAim.getY());
		float angle = tmp.set(getX(),getY()).sub(healAim.getX(),healAim.getY()).angle();
		float scale = dst/4f;
		fire.setRotation(angle);
		fire.setOrigin(4, 2);
		fire.addAction(Actions.sequence(Actions.scaleTo(scale,0.5f,0.3f,Interpolation.circleOut),Actions.run(new Runnable() {
			@Override
			public void run() {
				fire.remove();
			}
		})));
		terrain.addActor(fire); 
	}
	
	public final void setAnimation(Animation animation){
		this.animation = animation;
	}
	
	public void followNpc(BaseEntity e){
		if(e.speedX*this.speedX>0){
			if(this.speedX>0){
				if(this.getX()-90>e.getX()){
					if(Math.abs(this.speedX)>Math.abs(e.speedX)){
						this.speedX = -speedX;
					}
				}
			}else{
				if(this.getX()<e.getX()-90){
					if(Math.abs(this.speedX)>Math.abs(e.speedX)){
						this.speedX = -speedX;
					}
				}
			}
		}else{
			if(this.speedX>0){
				if(this.getX() - 90>e.getX()){
					this.speedX = -speedX;
				}
			}else{
				if(this.getX()<e.getX() - 90){
					this.speedX = -speedX;
				}
			}
		}
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
}
