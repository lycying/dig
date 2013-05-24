package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public  class Watchmen extends BaseEntity{
	
	public Watchmen(){  
		this.attack = 1;
		this.attackRange = 100;
		this.goldHold = 0;
		this.defense = true;
		this.self = false;
		
		this.speed = 45;
		
		
		TextureAtlas atlas = Engine.resource("All");
		
		
		
		walkAnimation = new Animation(0.205f, 
				atlas.findRegion("bad1"),
				atlas.findRegion("bad2"),
				atlas.findRegion("bad3"));
		goldWalkAnimation = new Animation(0.205f, atlas.findRegion("npc3"),atlas.findRegion("npc4"));
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
