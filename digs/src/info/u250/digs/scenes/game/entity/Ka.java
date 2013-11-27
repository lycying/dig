package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ka extends AbstractMoveable {
	static final int  DELAY_RANDOM = 3;
	TextureRegion[] npcRegions = new TextureRegion[7];

	static final float N_WIDTH = 10.5f;
	public Ka(){
		
		TextureAtlas atlas = Engine.resource("All");
		npcRegions[0] = atlas.findRegion("npc-ka1");
		npcRegions[1] = atlas.findRegion("npc-ka2");
		npcRegions[2] = atlas.findRegion("npc-ka3");
		npcRegions[3] = atlas.findRegion("npc-ka4");
		
		npcRegions[4] = atlas.findRegion("npc-ka2");
		npcRegions[5] = atlas.findRegion("npc-ka3");
		npcRegions[6] = atlas.findRegion("npc-ka4");
	
		
		regions = npcRegions;		
		this.setSize(N_WIDTH, N_WIDTH/regions[0].getRegionWidth()*regions[0].getRegionHeight());
		this.drawable.setSize(this.getWidth(), this.getHeight());
		this.setOrigin(this.getWidth()/2, 3);
		this.drawable.setOrigin(this.getWidth()/2, 3);
		this.drawable.setRegion(regions[0]);
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
		tryGoldDock();
	
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
						direction *= -1;
						if(Digs.RND.nextInt(3)!=0)velocity = 16;
					}
				}
			}
		}
		sync();
	}
	
	void tryGoldDock(){
		for(GoldTowerEntity dock:level.getDocks()){
			if(dock.getRect().overlaps(this.drawable.getBoundingRectangle())){
				direction*=-1;
				break;
			}
		}
	}
	@Override
	public void die() {
		super.die();
		if(null != level.getGame()){
			level.config.levelCompleteCallback.failLevel(level);//Not allow any ka die
		}
	}
	
	//my move attribute , bellow method is only used for the Ka in tower.
	float OCX = 0f;
	void onTower(GoldTowerEntity tower){
		if(Digs.RND.nextInt(10)>1){
			OCX+=direction;
			regionsIndex++;
		}
		x = 10+tower.getX() + OCX;
		y = tower.getY() +95;
		
		if (x-tower.getX()< 8) {
			direction = 1;//edge
		}
		if (x-tower.getX() > 90) {
			direction = -1;//edge
		}
		this.sync();
	}
}
