package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public  class HealBoy extends BaseEntity{
	
	public HealBoy(){  
		this.attack = 0;
		this.attackRange = 0;
		this.goldHold = 0;
		this.defense = true;
		this.self = true;
		this.heal = 1;
		this.healRange = 100;
		this.speed = 170;
		
		
		
		TextureAtlas atlas = Engine.resource("All");
		
		
		walkAnimation = new Animation(0.205f,atlas.findRegion("good"));
		goldWalkAnimation = new Animation(0.205f, atlas.findRegion("good"),atlas.findRegion("good"));
		animation = walkAnimation;
	}
	

	Animation walkAnimation ;
	Animation goldWalkAnimation ;

	

	@Override
	public Animation getWalkAnimationLeft() {
		return walkAnimation;
	}

	@Override
	public Animation getWalkAnimationRight() {
		return walkAnimation;
	}

	@Override
	public Animation getGoldAnimationLeft() {
		return goldWalkAnimation;
	}

	@Override
	public Animation getGoldAnimationRight() {
		return goldWalkAnimation;
	}

	
}
