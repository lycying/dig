package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public  class GreenHat extends BaseEntity{
	public GreenHat(){  
		this.hp = 5;
		
		TextureAtlas atlas = Engine.resource("All");
		
		walkRightAnimation = new Animation(0.205f, 
				atlas.findRegion("npc1-walk-right-1"),
				atlas.findRegion("npc1-walk-right-2"));
		walkLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc1-walk-left-1"),
				atlas.findRegion("npc1-walk-left-2"));
		
		goldWalkAnimation = new Animation(0.205f, atlas.findRegion("npc3"),atlas.findRegion("npc4"));
		
		animation = walkLeftAnimation;
	}
	

	Animation walkRightAnimation ;
	Animation walkLeftAnimation ;
	Animation goldWalkAnimation ;

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
		return goldWalkAnimation;
	}

	@Override
	public Animation getGoldAnimationRight() {
		return goldWalkAnimation;
	}

	@Override
	public Animation getSkillAnimationLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Animation getSkillAnimationRight() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
