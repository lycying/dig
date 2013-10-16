package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Npc extends Actor {
	TextureRegion[] npcRegions = new TextureRegion[4];
	TextureRegion[] npcGoldRegions = new TextureRegion[4];
	TextureRegion[] regions = null;
	int regionsIndex = 0;
	
	float x ,y ;
	public int direction = Digs.RND.nextBoolean()?1:-1;
	public int velocity = 1;
	private boolean isHoldGold = false;
	public Sprite drawable = new Sprite();
	
	//the main terrain
	protected Level level;
	public Npc(){
		this.setSize(10.5f, 15);
		this.drawable.setSize(10.5f, 15);
		this.setOrigin(this.getWidth()/2, 3);
		this.drawable.setOrigin(this.getWidth()/2, 3);
		
		TextureAtlas atlas = Engine.resource("All");
		npcRegions[0] = atlas.findRegion("npc1");
		npcRegions[1] = atlas.findRegion("npc2");
		npcRegions[2] = atlas.findRegion("npc3");
		npcRegions[3] = atlas.findRegion("npc4");
		
		npcGoldRegions[0] = atlas.findRegion("npc-gold1");
		npcGoldRegions[1] = atlas.findRegion("npc-gold2");
		npcGoldRegions[2] = atlas.findRegion("npc-gold3");
		npcGoldRegions[3] = atlas.findRegion("npc-gold4");
		
		regions = npcRegions;
//		this.setColor(generateColor());
	}
	
	Color generateColor() {
	    final int threshold = 150;
	    int r1, g1, b1;
	    while (true) {
	        r1 = Digs.RND.nextInt(256);
	        g1 = Digs.RND.nextInt(256);
	        b1 = Digs.RND.nextInt(256);
	        if (r1+g1+b1 > threshold) break;
	    }
	    Color c =  new Color(r1/255f, g1/255f, b1/255f,1);
	    return c;
	}
	
	public void init(Level terrain){
		this.level = terrain;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		drawable.draw(batch);
	}
	
	
	public void tick(){
		if(null == level)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX();
		y = this.getY();
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		
		if(userDefAction()) return;
		if(tryKillRay()) return;
		if(tryTransPort()) return;
		if(tryClampLadder()) return;
		if(tryKillRay()) return;
		
		if (velocity > 1) { // when the NPC is jumping
			if (level.tryMove(x+direction, y + velocity / 4)) {
				x += direction;
				y += velocity / 4;
				velocity--;
				regionsIndex++;
			} else {
				// Stop jumping
				velocity = 0;
			}
		} else {
			if (level.tryMove(x, y-1)) {
				y--;
				if (level.tryMove(x, y-1)) {
					y--;
				}
				velocity = 0;
			} else	{
				if (Digs.RND.nextInt(10) != 0) {
					// Assume the miner has hit a wall
					boolean hit = true;
					for (int p = 1; p <= 4; p++) {
						if (level.tryMove(x+direction, y+p)) {
							x += direction;
							y += p;
							hit = false;
							regionsIndex++;
							velocity = -1;
							break;
						}
					}
					if (Digs.RND.nextInt(hit ? 10 : 4000) == 0) {
						direction *= -1;
						if (hit) {
							if(Digs.RND.nextInt(3)!=0)velocity = 16;
						}else{
							if(Digs.RND.nextInt(3)==0)velocity = 16;
						}
					}
				}
			}
		}
		if(isHoldGold){
			tryGoldDock();
		}else{
			if(level.tryDig()){
				isHoldGold = true;
				direction *= -1;
				regions = npcGoldRegions;
			}
		}
		sync();
	}
	void sync(){
		this.setX(x);
		this.setY(y);
		drawable.setRegion(regions[(regionsIndex/2)%4]);
		drawable.setColor(this.getColor());
		drawable.setPosition(x, y);
		if(direction<0)drawable.flip(true, false);
	}
	void tryGoldDock(){
		for(GoldDock dock:level.docks){
			if(this.drawable.getBoundingRectangle().overlaps(dock.actor.getBoundingRectangle())){
				isHoldGold = false;
				this.direction *= -1;
				dock.number++;
				regions = npcRegions;
				break;
			}
		}
	}
	boolean tryClampLadder(){
		for(Stepladder ladder:level.ladders){
			if(ladder.getRect().contains(x, y)){
				if(y>ladder.getRect().y+ladder.getRect().height-10){
					if(velocity==-1){
						velocity = 16;
					}
					return false;
				}else if(y<ladder.getRect().y+1){
					if(velocity>-1){
						if(level.tryMove(x, y-1)){
							y+=1;
							velocity = -1;
						}else{
							y-=1;
						}
						sync();
						return true;
					}
				}
				if(velocity<0){ //is try to move to the high position
					y+=1;
				}else{
					y-=1;
				}
				x = ladder.getRect().x + (ladder.getRect().getWidth()-this.getWidth())/2;
				sync();
				return true;
			}
		}
		return false;
	}
	boolean userDefAction(){
		if(this.getActions().size > 0){
			sync();
			return true;
		}
		return false;
	}
	boolean tryTransPort(){		
		for(final InOutTrans inout:level.inouts){
			if(inout.getRect().contains(x,y)){
				x = inout.getRect().x+37;
				y = inout.getRect().y+20;
				sync();
				this.addAction(Actions.sequence(Actions.moveTo(
						inout.getTransX()+15+15*Digs.RND.nextFloat(), 
						inout.getTransY()+15+15*Digs.RND.nextFloat(),
						0.8f),Actions.run(new Runnable() {
					@Override
					public void run() {
						sync();
					}
				})));
				if(!inout.isClear()){
					inout.setClear(true);
					level.clearTransPort(inout.getRect().x+5, inout.getRect().y+40-5, 30);
					level.clearTransPort(inout.getTransX()+5, inout.getTransY()+40-5, 30);
				}
				return true;
			}
		}
		return false;
	}
	boolean tryKillRay(){
		for(KillRay kill:level.killrays){
			if(kill.overlaps(x, y)){
				TextureAtlas atlas = Engine.resource("All");
				int size = 8;
				float part = 360f/size;
				for(int i=0;i<size;i++){
					float ax = Digs.RND.nextFloat()*100*MathUtils.cosDeg(i*part);
					float ay = Digs.RND.nextFloat()*100*MathUtils.sinDeg(i*part);
					final Image image = new Image(atlas.findRegion("color"));
					image.setColor(new Color(1,0,0,0.7f));
					image.setPosition(x, y);
					image.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(x+ax, y+ay,0.2f,Interpolation.circleIn),Actions.fadeOut(0.2f)),Actions.run(new Runnable() {
						@Override
						public void run() {
							image.remove();
						}
					})));
					level.addActor(image);
				}
				this.remove();
				level.npcs.removeValue(this, true);
				return true;
			}
		}
		return false;
	}
}
