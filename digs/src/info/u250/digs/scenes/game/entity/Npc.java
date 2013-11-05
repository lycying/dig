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
	static final int  DELAY_RANDOM = 10;
	/**
	 * when the npc walks , it check the area surrounds it to determine 
	 * if it hit the gold or the bomb .
	 * We need the result to give some effect and logic to the npc so it is.
	 */
	public enum DigResult{
		None,
		Gold,
		Bomb,
	}
	public enum NpcStatus{
		Free,
		HoldGold,
		WithKa,
	}
	TextureRegion[] npcRegions = new TextureRegion[4];
	TextureRegion[] npcGoldRegions = new TextureRegion[4];
	TextureRegion[] regions = null;
	int regionsIndex = 0;
	
	float x ,y ;
	public int direction = Digs.RND.nextBoolean()?1:-1;
	public int velocity = 1;
	public Sprite drawable = new Sprite();
	
	public static float DIE_SOUND_CTL = 0;
	public static float HURT_SOUND_CTL = 0;
	public static float TRANS_SOUND_CTL = 0;
	public static float COIN_SOUND_CTL = 0;
	
	public Ka withKa = null;//with ka
	
	private NpcStatus status = NpcStatus.Free;
	
	//the main terrain
	protected Level level;
	static final float N_WIDTH = 10.5f;
	public Npc(){
		int themeID = Digs.RND.nextInt(5)+1;
		TextureAtlas atlas = Engine.resource("All");
		npcRegions[0] = atlas.findRegion("npc-s"+themeID+"-1");
		npcRegions[1] = atlas.findRegion("npc-s"+themeID+"-2");
		npcRegions[2] = atlas.findRegion("npc-s"+themeID+"-3");
		npcRegions[3] = atlas.findRegion("npc-s"+themeID+"-4");
		
		npcGoldRegions[0] = atlas.findRegion("npc-s"+themeID+"-gold-1");
		npcGoldRegions[1] = atlas.findRegion("npc-s"+themeID+"-gold-2");
		npcGoldRegions[2] = atlas.findRegion("npc-s"+themeID+"-gold-3");
		npcGoldRegions[3] = atlas.findRegion("npc-s"+themeID+"-gold-4");
		
		regions = npcRegions;		
		this.setSize(N_WIDTH, N_WIDTH/regions[0].getRegionWidth()*regions[0].getRegionHeight());
		this.drawable.setSize(this.getWidth(), this.getHeight());
		this.setOrigin(this.getWidth()/2, 3);
		this.drawable.setOrigin(this.getWidth()/2, 3);
	}
	
	
	public void init(Level terrain){
		this.level = terrain;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(withKa!=null){
			withKa.x=this.getX();
			withKa.y=this.getY()+10;
			withKa.direction = direction;
			withKa.sync();
			withKa.draw(batch, parentAlpha);
		}
		drawable.draw(batch);
	}
	
	
	public void tick(){
		if(null == level)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX(); //got it x
		y = this.getY(); //got it y
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		
		if(userDefAction()) return;	//when the addActions run , block everything until the action done
		if(tryKillRay()) return;	//if the npc touch the kill circle , then kill it , block
		if(tryTransPort()) return;	//if the npc touch the teleport, transfer it , block
		if(tryClampLadder()) return;//if the npc touch the clamp ladder , then go with the logic
		
		// when the NPC is jumping
		if (velocity > 1) { 
			if (level.tryMove(x+direction, y + velocity / 4)) { // if he can jump to the position then sub the direction
				x += direction;
				y += velocity / 4;
				velocity--;
				regionsIndex++;
			} else {
				// Stop jumping
				velocity = 0;
			}
		} else {
			//try move down 
			if (level.tryMove(x , y-1)) {
				y--;
				//move down again
				if (level.tryMove(x , y-1)) {
					y--;
				}
				velocity = 0;
			} else	{
				//try move up , the npc can clamp as high as 5 pixels
				if (Digs.RND.nextInt(DELAY_RANDOM) != 0) {
					// Assume the miner has hit a wall
					boolean hit = true;
					for (int p = 1; p <= 5; p++) { //try higher until 5, this makes the npc clamp quickly 
						if (level.tryMove(x+direction, y+p)) { //clamp and stop this time
							x += direction;
							y += p;
							hit = false;
							regionsIndex++;
							velocity = -1;
							break;
						}
					}
					if (Digs.RND.nextInt(hit ? 10 : 8000) == 0) {
						if(hit){
							direction *= -1;
						}else{
							if(NpcStatus.Free == status){//if hold gold , not change its direction
								direction *= -1;
							}
						}
						if(Digs.RND.nextInt(3)!=0)velocity = 16;
					}
				}
			}
		}
		switch(status){
		case Free:
			meetKa();
			break;
		case HoldGold:
			meetKa(); 	//the npc can meet a ka even if he hold a gold , this will waste much gold
			tryGoldDock();
			break;
		case WithKa:
			tryGoldDock();
			break;
		}
		
		switch(level.tryDig(NpcStatus.HoldGold == status)){
		case None:break;
		case Bomb:
			die();	
			if(HURT_SOUND_CTL>0.2f){
				Engine.getSoundManager().playSound("SoundHurt");
				HURT_SOUND_CTL = 0;
			}
			break;
		case Gold:
			status = NpcStatus.HoldGold;
			direction *= -1;
			regions = npcGoldRegions;
			break;
		default:
			break;
		}
		sync();
	}
	int soundCoinIndex = 0;
	void sync(){
		this.setX(x);
		this.setY(y);
		drawable.setRegion(regions[(regionsIndex/2)%regions.length]);
		drawable.setColor(this.getColor());
		drawable.setPosition(x-this.getOriginX(), y-this.getOriginY());
		if(direction<0)drawable.flip(true, false);
	}
	void meetKa(){
		if(regionsIndex>5)
		for(Ka ka:level.getKas()){
			if(ka.drawable.getBoundingRectangle().overlaps(this.drawable.getBoundingRectangle())){
				Engine.getSoundManager().playSound("SoundMeet");
				status = NpcStatus.WithKa;
				withKa = ka;
				level.removeKa(ka);//ok ok ok , i have catch ka
				this.direction = -withKa.direction*this.direction;
				break;
			}
		}
	}
	void tryGoldDock(){
		for(GoldTowerEntity dock:level.getDocks()){
			if(this.drawable.getBoundingRectangle().overlaps(dock.getRect())){
				if(status==NpcStatus.HoldGold){
					this.direction *= -1;
					dock.number++;
					regions = npcRegions;
					if(COIN_SOUND_CTL>0.2f){
						Engine.getSoundManager().playSound("SoundCoin");
						COIN_SOUND_CTL = 0;
					}
				}else if(status==NpcStatus.WithKa){
					withKa.x = 0;
					withKa.y = 0;
					withKa.sync();
					dock.addKa(withKa);
					withKa = null;
					this.addAction(Actions.delay(1));//delay...
					Engine.getSoundManager().playSound("SoundDockKa");
				}
				status = NpcStatus.Free;
				break;
			}
		}
	}
	boolean tryClampLadder(){
		for(StepladderEntity ladder:level.getLadders()){
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
				x = ladder.getRect().x + (ladder.getPrefWidth())/2;
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
		for(final TeleportEntity inout:level.getInouts()){
			if(inout.getRect().contains(x,y)){
				x = inout.getRect().x+37;
				y = inout.getRect().y+20;
				sync();
				if(TRANS_SOUND_CTL>0.2f){
					Engine.getSoundManager().playSound("SoundTrans");
					TRANS_SOUND_CTL = 0;
				}
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
					level.clearTransPort(inout.getTransX()+5, inout.getTransY()+40-5, 30);
				}
				return true;
			}
		}
		return false;
	}
	boolean tryKillRay(){
		for(KillCircleEntity kill:level.getKillrays()){
			if(kill.overlaps(x, y) || kill.overlaps(x, y+8)){//the bottom and top
				die();
				if(DIE_SOUND_CTL>0.2f){
					Engine.getSoundManager().playSound("SoundDie");
					DIE_SOUND_CTL = 0;
				}
				return true;
			}
		}
		return false;
	}
	
	void die(){
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
		level.removeNpc(this);
	}


	public NpcStatus getStatus() {
		return status;
	}
	
}
