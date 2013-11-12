package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level.FingerMode;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/* nothing can block me , came to die !! */
public class Boss extends AbstractMoveable {
	enum KillWho{
		None,
		Npc,
		Ka,
		Enemy,
	}
	static final int  DELAY_RANDOM = 20;
	TextureRegion[] npcRegions = new TextureRegion[5];
	TextureRegion[] destroyRegions = new TextureRegion[2];
	static final float N_WIDTH = 24f;
	float bossLandHeight = 200;
	int blockMyWayIndex = 0;
	boolean stopAllDestoryEverything = false;
	KillWho killWho = KillWho.None;
	AbstractMoveable kill = null;
	int count = 0;
	int delayCount = 0;
	int tickCount = 0;
	public Boss(){
		TextureAtlas atlas = Engine.resource("All");
		npcRegions[0] = atlas.findRegion("boss-1");
		npcRegions[1] = atlas.findRegion("boss-2");
		npcRegions[2] = atlas.findRegion("boss-3");
		npcRegions[3] = atlas.findRegion("boss-4");
		npcRegions[4] = atlas.findRegion("boss-5");

		
		destroyRegions[0] = atlas.findRegion("boss-attack-1");
		destroyRegions[1] = atlas.findRegion("boss-attack-2");
		
		regions = npcRegions;		
		this.setSize(N_WIDTH, N_WIDTH/regions[0].getRegionWidth()*regions[0].getRegionHeight());
		this.drawable.setSize(this.getWidth(), this.getHeight());
		this.setOrigin(this.getWidth()/2, 3);
		this.drawable.setOrigin(this.getWidth()/2, 3);
		this.drawable.setRegion(regions[0]);
	}
	
	public void tick(){
		tick2xSpeed();
		tick2xSpeed();
	}
	public void tick2xSpeed(){
		if(null == level)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX(); //got it x
		y = this.getY(); //got it y
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		if (y < 8) {
			level.fillTerrain(x, y, 30,FingerMode.Fill);
			y = 50;
		}
		
		if(userDefAction()) return;	//when the addActions run , block everything until the action done
		if(stopAllDestoryEverything){
			//play the destroy animation
			count++;
			if(count%10==0){
				Engine.getSoundManager().playSound("SoundBossXO");
			}
			if(count%2==0){
				regionsIndex++;
			}
			if(count>=40){
				stopAllDestoryEverything = false;
				regions = npcRegions;
				switch (killWho) {
				case None:
					Engine.getSoundManager().playSound("SoundBossBreak");
					level.dig(12,getX(), getY()+(Digs.RND.nextBoolean()?Digs.RND.nextFloat()*20:-Digs.RND.nextFloat()*5));
					break;
				case Enemy:
				case Ka:
				case Npc:
					kill.die();
					break;
				default:
					break;
				}
				kill = null;
			}
			sync();
			return;
		}
		if(tryKillNpc())return;
		if(tryKillEnemy())return;
		if(tryKillKa())return;
		delayCount++;
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
					for (int p = 1; p <= 4 /* not 5 */; p++) { //try lower until 2, this makes the npc clamp quickly // boss can move higher!!!!!!
						if (level.tryMove(x+direction, y+p)) { //clamp and stop this time
							x += direction;
							y += p;
							hit = false;
							regionsIndex++;
							velocity = -1;
							break;
						}
					}
					if(hit){//well when we hit 17 time of the wall , we make a big destroy of the word
						blockMyWayIndex++;
						if(blockMyWayIndex%6==5){
							regions = destroyRegions;
							//nothing can block me after the wall block me at  the 17 time!!!!
							stopAllDestoryEverything = true;
							regionsIndex = 0;
							count = 0;
							killWho = KillWho.None;
						}
						velocity = 16;//boss always jump when hit
						if(Digs.RND.nextInt(3)!=0){
							direction *= -1;
						}
					}
				}
			}
		}
		if(y<bossLandHeight){
			if(regionsIndex%14==13){
				level.fillTerrain(x, y-6, 8,FingerMode.Fill);
			}
		}
		sync();
	}
	boolean tryKillNpc(){
		if(delayCount<100) return false;
		for(Npc e:level.getNpcs()){
			if(e.readyToDie)continue;
			if(e.drawable.getBoundingRectangle().overlaps(drawable.getBoundingRectangle())){
				killWho = KillWho.Npc;
				regions = destroyRegions;
				stopAllDestoryEverything = true;
				regionsIndex = 0;
				count = 0;
				e.readyToDie = true;
				kill = e;
				delayCount = 0;
				kill.addAction(Actions.forever(Actions.sequence(Actions.moveBy(2, 0,0.03f),Actions.moveBy(-2, 0,0.03f))));
				return true;
			}
		}
		return false;
	}
	boolean tryKillKa(){
		if(delayCount<100) return false;
		for(Ka e:level.getKas()){
			if(e.readyToDie)continue;
			if(e.drawable.getBoundingRectangle().overlaps(drawable.getBoundingRectangle())){
				killWho = KillWho.Ka;
				regions = destroyRegions;
				stopAllDestoryEverything = true;
				regionsIndex = 0;
				count = 0;
				e.readyToDie = true;
				kill = e;
				delayCount = 0;
				kill.addAction(Actions.forever(Actions.sequence(Actions.moveBy(2, 0,0.03f),Actions.moveBy(-2, 0,0.03f))));
				return true;
			}
		}
		return false;
	}
	boolean tryKillEnemy(){
		if(delayCount<100) return false;
		for(EnemyMiya e:level.getEnemyMyiyas()){
			if(e.readyToDie)continue;
			if(e.drawable.getBoundingRectangle().overlaps(drawable.getBoundingRectangle())){
				killWho = KillWho.Enemy;
				regions = destroyRegions;
				stopAllDestoryEverything = true;
				regionsIndex = 0;
				count = 0;
				e.readyToDie = true;
				kill = e;
				delayCount = 0;
				kill.addAction(Actions.forever(Actions.sequence(Actions.moveBy(2, 0,0.03f),Actions.moveBy(-2, 0,0.03f))));
				return true;
			}
		}
		return false;
	}
	public void setBossLandHeight(float bossLandHeight) {
		this.bossLandHeight = bossLandHeight;
	}
	
}
