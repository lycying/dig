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
				atlas.findRegion("npc1-walk-right-2"),
				atlas.findRegion("npc1-walk-right-3"));
		walkLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc1-walk-left-1"),
				atlas.findRegion("npc1-walk-left-2"),
				atlas.findRegion("npc1-walk-left-3"));
		
		goldWalkRightAnimation = new Animation(0.205f, 
				atlas.findRegion("npc1-gold-right-1"),
				atlas.findRegion("npc1-gold-right-2"),
				atlas.findRegion("npc1-gold-right-3"));
		goldWalkLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("npc1-gold-left-1"),
				atlas.findRegion("npc1-gold-left-2"),
				atlas.findRegion("npc1-gold-left-3"));
		
		animation = walkLeftAnimation;
	}
	

	Animation walkRightAnimation ;
	Animation walkLeftAnimation ;
	Animation goldWalkLeftAnimation ;
	Animation goldWalkRightAnimation ;

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
		return goldWalkLeftAnimation;
	}

	@Override
	public Animation getGoldAnimationRight() {
		return goldWalkRightAnimation;
	}

	@Override
	public Animation getSkillAnimationLeft() {
		return null;
	}

	@Override
	public Animation getSkillAnimationRight() {
		return null;
	}

	
	
	
}
