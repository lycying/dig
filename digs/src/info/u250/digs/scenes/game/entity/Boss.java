package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/* nothing can block me , came to die !! */
public class Boss extends AbstractMoveable {
	enum KillWho{
		None,
		Npc,
		Ka,
		Enemy,
	}
	static final int  DELAY_RANDOM = 3;
	TextureRegion[] npcRegions = new TextureRegion[7];
	TextureRegion[] destroyRegions = new TextureRegion[7];
	static final float N_WIDTH = 32f;
	public Boss(){
		TextureAtlas atlas = Engine.resource("All");
		npcRegions[0] = atlas.findRegion("npc-ka1");
		npcRegions[1] = atlas.findRegion("npc-ka2");
		npcRegions[2] = atlas.findRegion("npc-ka3");
		npcRegions[3] = atlas.findRegion("npc-ka4");
		npcRegions[4] = atlas.findRegion("npc-ka2");
		npcRegions[5] = atlas.findRegion("npc-ka3");
		npcRegions[6] = atlas.findRegion("npc-ka4");
		
		destroyRegions[0] = atlas.findRegion("npc-ka1");
		destroyRegions[1] = atlas.findRegion("npc-ka2");
		destroyRegions[2] = atlas.findRegion("npc-ka3");
		destroyRegions[3] = atlas.findRegion("npc-ka4");
		destroyRegions[4] = atlas.findRegion("npc-ka2");
		destroyRegions[5] = atlas.findRegion("npc-ka3");
		destroyRegions[6] = atlas.findRegion("npc-ka4");
		
		regions = npcRegions;		
		this.setSize(N_WIDTH, N_WIDTH/regions[0].getRegionWidth()*regions[0].getRegionHeight());
		this.drawable.setSize(this.getWidth(), this.getHeight());
		this.setOrigin(this.getWidth()/2, 3);
		this.drawable.setOrigin(this.getWidth()/2, 3);
		this.drawable.setRegion(regions[0]);
	}
	int blockMyWayIndex = 0;
	boolean stopAllDestoryEverything = false;
	KillWho killWho = KillWho.None;
	AbstractMoveable kill = null;
	public void tick(){
		if(null == level)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX(); //got it x
		y = this.getY(); //got it y
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		
		if(userDefAction()) return;	//when the addActions run , block everything until the action done
		if(stopAllDestoryEverything){
			//play the destroy animation
			regionsIndex++;
			if(regionsIndex%5==0){
				//TODO: play destroy sound
			}
			if(regionsIndex>=20){
				stopAllDestoryEverything = false;
				regions = npcRegions;
				switch (killWho) {
				case None:
					level.dig(16,getX(), getY());
					break;
				case Enemy:
					kill.die();
					level.removeEnemyMiya(EnemyMiya.class.cast(kill));
					break;
				case Ka:
					kill.die();
					level.removeKa(Ka.class.cast(kill));
					break;
				case Npc:
					kill.die();
					level.removeNpc(Npc.class.cast(kill));
					break;
				default:
					break;
				}
				kill = null;
				//TODO: play destroy dig sound
			}
			return;
		}
		if(tryKillNpc())return;
		if(tryKillEnemy())return;
		if(tryKillKa())return;
		
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
					for (int p = 1; p <= 10 /* not 5 */; p++) { //try higher until 5, this makes the npc clamp quickly // boss can move higher!!!!!!
						if (level.tryMove(x+direction, y+p)) { //clamp and stop this time
							x += direction;
							y += p;
							hit = false;
							regionsIndex++;
							velocity = -1;
							break;
						}
					}
					if(hit){//well when we hit 7 time of the wall , we make a big destroy of the word
						blockMyWayIndex++;
						if(blockMyWayIndex%8==7){
							regions = destroyRegions;
							//nothing can block me after the wall block me at  the 7 time!!!!
							stopAllDestoryEverything = true;
							regionsIndex = 0;
							killWho = KillWho.None;
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
	boolean tryKillNpc(){
		for(Npc e:level.getNpcs()){
			if(e.drawable.getBoundingRectangle().overlaps(drawable.getBoundingRectangle())){
				killWho = KillWho.Npc;
				regions = destroyRegions;
				stopAllDestoryEverything = true;
				regionsIndex = 0;
				kill = e;
				return true;
			}
		}
		return false;
	}
	boolean tryKillKa(){
		for(Ka e:level.getKas()){
			if(e.drawable.getBoundingRectangle().overlaps(drawable.getBoundingRectangle())){
				killWho = KillWho.Ka;
				regions = destroyRegions;
				stopAllDestoryEverything = true;
				regionsIndex = 0;
				kill = e;
				return true;
			}
		}
		return false;
	}
	boolean tryKillEnemy(){
		for(EnemyMiya e:level.getEnemyMyiyas()){
			if(e.drawable.getBoundingRectangle().overlaps(drawable.getBoundingRectangle())){
				killWho = KillWho.Enemy;
				regions = destroyRegions;
				stopAllDestoryEverything = true;
				regionsIndex = 0;
				kill = e;
				return true;
			}
		}
		return false;
	}
}
