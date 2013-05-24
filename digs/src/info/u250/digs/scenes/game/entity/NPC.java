package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public  class NPC extends BaseEntity{
	public NPC(){  
		this.hp = 5;
		
		TextureAtlas atlas = Engine.resource("All");
		
		walkAnimation = new Animation(0.105f, atlas.findRegion("npc1"),atlas.findRegion("npc2"));
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
