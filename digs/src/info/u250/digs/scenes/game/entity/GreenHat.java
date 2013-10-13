package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public  class GreenHat extends BaseEntity{
	public GreenHat(){  
		TextureAtlas atlas = Engine.resource("All");
		Random r = new Random();
		walkRightAnimation = new Animation(r.nextFloat()+0.2f, 
				atlas.findRegion("npc1"),
				atlas.findRegion("npc2"),
				atlas.findRegion("npc3"));
		walkLeftAnimation = new Animation(r.nextFloat()+0.2f, 
				atlas.findRegion("npc2"),
				atlas.findRegion("npc1"),
				atlas.findRegion("npc3"));
		
		goldWalkRightAnimation = new Animation(0.205f, 
				atlas.findRegion("color"),
				atlas.findRegion("color"),
				atlas.findRegion("color"));
		goldWalkLeftAnimation = new Animation(0.205f, 
				atlas.findRegion("color"),
				atlas.findRegion("color"),
				atlas.findRegion("color"));
		
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
