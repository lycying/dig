package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class EnemyMiya extends AbstractMoveable {
	TextureRegion[] npcRegions = new TextureRegion[4];
	
	static final int  DELAY_RANDOM = 5;
	static final float N_WIDTH = 16f;
	static final float ATTACK_RANGE = 100;
	static final float ATTACK_DELAY = 0.5f;
	public EnemyMiya(){
		
		TextureAtlas atlas = Engine.resource("All");
		npcRegions[0] = atlas.findRegion("npc2-walk-right-1");
		npcRegions[1] = atlas.findRegion("npc2-walk-right-2");
		npcRegions[2] = atlas.findRegion("npc2-walk-right-3");
		npcRegions[3] = atlas.findRegion("npc2-walk-right-4");	
		
		regions = npcRegions;		
		this.setSize(N_WIDTH, N_WIDTH/regions[0].getRegionWidth()*regions[0].getRegionHeight());
		this.drawable.setSize(this.getWidth(), this.getHeight());
		this.setOrigin(this.getWidth()/2, 3);
		this.drawable.setOrigin(this.getWidth()/2, 3);
		this.drawable.setRegion(regions[0]);
	}
	int accum = 0;
	public void tick(){
		if(null == level)return ;
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		x = this.getX(); //got it x
		y = this.getY(); //got it y
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		if (y < 8){
			y = Engine.getHeight()+100;//reborn , never try to kill a enemy , you cann't
			//not die
		}
		if(userDefAction()) return;	//when the addActions run , block everything until the action done
		accum++;
		if(accum>30){
			if(tryKillNpcAndKa()){
				accum = 0;
				return;
			}
		}
	
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

	final Vector2 tmp = new Vector2();
	boolean tryKillNpcAndKa(){
		for(Npc e:level.getNpcs()){
			if(!e.readyToDie && tmp.set(getX(), getY()).dst(e.getX(),e.getY())<ATTACK_RANGE){
				e.readyToDie = true;
				e.velocity = 16;
				e.direction*=-1;
				level.addActor(new EnemyBombo(this, e));
				this.addAction(Actions.delay(ATTACK_DELAY));
				Engine.getSoundManager().playSound("SoundShot");
				return true;
			}
		}
		for(Ka e:level.getKas()){
			if(!e.readyToDie && tmp.set(getX(), getY()).dst(e.getX(),e.getY())<ATTACK_RANGE){
				e.readyToDie = true;
				e.velocity = 16;
				e.direction*=-1;
				level.addActor(new EnemyBombo(this, e));
				Engine.getSoundManager().playSound("SoundShot");
				this.addAction(Actions.delay(ATTACK_DELAY));
				return true;
			}
		}
		return false;
	}
}
/*
 * just a bomb that will kill a npc or ka
 */
class EnemyBombo extends Image {
	Vector2 target_direction = new Vector2();
	private AbstractMoveable target;
		
	static final float SPEED = 200;
	public EnemyBombo(EnemyMiya bomber,AbstractMoveable target){
		super(Engine.resource("All",TextureAtlas.class).findRegion("color"));
		this.target = target;
		this.setPosition(bomber.getX()+bomber.getWidth()/2, bomber.getY()+bomber.getHeight()/2);
		this.setColor(Color.RED);
		this.setSize(4, 4);
	}
	float accum = 0;
	@Override
	public void act(float delta) {
		accum += delta;
		if(accum>EnemyMiya.ATTACK_RANGE/SPEED){
			this.remove();
			target.readyToDie = false;//unlock the target
			return;
		}
		target_direction.set(target.getX()+target.drawable.getWidth()/2,target.getY()+target.drawable.getHeight()/2).sub(this.getX(),this.getY()).nor();
		translate(delta*SPEED*target_direction.x, delta*SPEED*target_direction.y);
		if(target.drawable.getBoundingRectangle().contains(getX(), getY())){
			this.remove();
			target.die();
			Engine.getSoundManager().playSound("SoundDie");
		}
		super.act(delta);
	}
}
