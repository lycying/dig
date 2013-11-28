package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Npc extends AbstractMoveable {
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
	
	public Ka withKa = null;//with ka
	private NpcStatus status = NpcStatus.Free;
	
	static final float N_WIDTH = 14f;
	
	
	
	float yOffsetPre = Integer.MAX_VALUE;
	
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
		this.drawable.setRegion(regions[0]);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(withKa!=null){
			withKa.x=this.getX();
			withKa.y=this.getY()+10;
			withKa.direction = direction;
			withKa.sync();
			withKa.draw(batch, parentAlpha);
		}
		super.draw(batch, parentAlpha);
	}
	@Override
	public void die() {	
		//release ka
		if(status==NpcStatus.WithKa){
			withKa.x=this.getX();
			withKa.y=this.getY()+10;
			withKa.direction = -direction;
			withKa.sync();
			withKa.velocity = 16;//jump
			level.addKa(withKa);
			withKa = null;
		}
		super.die();
	}
	
	public void tick(){
		if(null == level)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX(); //got it x
		y = this.getY(); //got it y
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		if (y < 8){
			die();
			return ;
		}
		
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
				downDownDown = false;
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
				downDownDown = true;
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
							downDownDown = false;
							break;
						}
					}
					
					if(hit){
						if(level.tryMove(x+direction, y)){
							x += direction;
							hit = false;
							regionsIndex++;
						}
					}
					
					if (hit) {
						direction *= -1;
						if(y>yOffsetPre){
							velocity = 16;
						}
						yOffsetPre = y;
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
		
		switch(level.tryDig(NpcStatus.Free == status)){
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
	void meetKa(){
		if(regionsIndex>5)
		for(Ka ka:level.getKas()){
			if(ka.drawable.getBoundingRectangle().overlaps(this.drawable.getBoundingRectangle())){
				Engine.getSoundManager().playSound("SoundMeet");
				status = NpcStatus.WithKa;
				regions = npcRegions;
				withKa = ka;
				level.removeKa(ka);//ok ok ok , i have catch ka
				this.addAction(Actions.delay(Digs.RND.nextFloat()));// random delay...
				break;
			}
		}
	}
	void tryGoldDock(){
		for(GoldTowerEntity dock:level.getDocks()){
			if(this.drawable.getBoundingRectangle().overlaps(dock.getRect())){
				if(!dock.isReady())return;
				if(status==NpcStatus.HoldGold){
					this.direction *= -1;
					dock.addNumber();
					regions = npcRegions;
					if(COIN_SOUND_CTL>0.2f){
						Engine.getSoundManager().playSound("SoundCoin");
						COIN_SOUND_CTL = 0;
					}
					this.addAction(Actions.delay(Digs.RND.nextFloat()));// random delay...
				}else if(status==NpcStatus.WithKa){
					withKa.x = 0;
					withKa.y = 0;
					withKa.sync();
					level.removeKa(withKa);
					dock.addKa(withKa);
					withKa = null;
					direction*=-1;
					this.addAction(Actions.delay(Digs.RND.nextFloat()));// random delay...
					Engine.getSoundManager().playSound("SoundDockKa");
				}
				status = NpcStatus.Free;
				break;
			}
		}
	}
	
	public NpcStatus getStatus() {
		return status;
	}
	
}
