package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AttackMan extends BaseEntity{
	
	public AttackMan(){  
		this.attack = 1;
		this.attackRange = 100;
		this.goldHold = 0;
		this.defense = true;
		this.self = false;
		
		this.speed = 20;
		
		
		TextureAtlas atlas = Engine.resource("All");
		
		
		
		walkRightAnimation = new Animation(0.205f, 
				atlas.findRegion("npc2-walk-right-1"),
				atlas.findRegion("npc2-walk-right-2"),
				atlas.findRegion("npc2-walk-right-3"),
				atlas.findRegion("npc2-walk-right-4"));
		walkLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc2-walk-left-1"),
				atlas.findRegion("npc2-walk-left-2"),
				atlas.findRegion("npc2-walk-left-3"),
				atlas.findRegion("npc2-walk-left-4"));
		attackRightAnimation = new Animation(0.205f, 
				atlas.findRegion("npc2-walk-right-1"));
		attackLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc2-walk-left-1"));
		animation = walkLeftAnimation;
	}
	

	Animation walkRightAnimation ;
	Animation walkLeftAnimation ;
	Animation attackRightAnimation ;
	Animation attackLeftAnimation ;

	

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
		return attackLeftAnimation;
	}

	@Override
	public Animation getSkillAnimationRight() {
		return attackRightAnimation;
	}
	
}
