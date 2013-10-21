package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Proverb;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class FollowLabel extends Group{
	Label label;
	Level level;
	Npc npc = null;
	public FollowLabel(Level level){
		this.level = level;
		final TextureAtlas atlas = Engine.resource("All");
		label = new Label(Proverb.get(), new LabelStyle(Engine.resource("Font",BitmapFont.class), Color.WHITE));
		label.getStyle().background = new NinePatchDrawable(atlas.createPatch("npc-say"));
		label.pack();
		this.addActor(label);
		this.setSize(this.label.getWidth(), this.label.getHeight());
		this.setOriginX(this.getWidth()/2);
		this.getColor().a = 0;
		this.setScale(0);
	}
	
	void remaker(){
		label.setText(Proverb.get());
		label.pack();
		this.setSize(this.label.getWidth(), this.label.getHeight());
		this.setOriginX(this.getWidth()/2);
		Engine.getSoundManager().playSound("SoundEnv"+(Digs.RND.nextInt(20)+1));
	}
	float accum = 15;
	boolean show = false;
	int soundIndex = 0 ;
	@Override
	public void act(float delta) {
		soundIndex++;
		accum +=delta;
		if(accum>3){
			if(show){
				this.addAction(Actions.parallel(Actions.fadeOut(0.2f),Actions.scaleTo(0, 0,0.2f)));
			}	
		}
		if(accum > 20){
			accum = 0;
			this.addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.scaleTo(1, 1,0.2f)));
			npc = null;
			remaker();
			show = true;
		}
		if(null!=npc && npc.getParent()!=null){
			this.setPosition(npc.getX()-this.getWidth()/2, npc.getY()+15);
		}else{
			npc = level.getRandomNpc();
			if(null != npc)npc.velocity = 16;
		}
		super.act(delta);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(this.getColor().a != 0 && null!=npc && npc.getParent()!=null)super.draw(batch, parentAlpha);
	}
}
