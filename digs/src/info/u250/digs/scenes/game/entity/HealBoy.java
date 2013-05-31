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
		this.speed = 70;
		
		
		
		TextureAtlas atlas = Engine.resource("All");
		
		
		walkRightAnimation = new Animation(0.205f, 
				atlas.findRegion("npc3-walk-right-1"),
				atlas.findRegion("npc3-walk-right-2"));
		walkLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc3-walk-left-1"),
				atlas.findRegion("npc3-walk-left-2"));
		healRightAnimation = new Animation(0.205f, 
				atlas.findRegion("npc3-heal-right-1"),
				atlas.findRegion("npc3-heal-right-2"));
		healLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc3-heal-left-1"),
				atlas.findRegion("npc3-heal-left-2"));
		animation = walkLeftAnimation;
	}
	

	Animation walkRightAnimation ;
	Animation walkLeftAnimation ;
	Animation healRightAnimation ;
	Animation healLeftAnimation ;
	

	@Override
	public Animation getWalkAnimationLeft() {
		return walkLeftAnimation;
	}

	@Override
	public Animation getWalkAnimationRight() {
		return walkRightAnimation;
	}

	@Override
	public Animation getGoldAnimationLeft() {
		return null;
	}

	@Override
	public Animation getGoldAnimationRight() {
		return null;
	}

	@Override
	public Animation getSkillAnimationLeft() {
		return healLeftAnimation;
	}

	@Override
	public Animation getSkillAnimationRight() {
		return healRightAnimation;
	}

	
}
