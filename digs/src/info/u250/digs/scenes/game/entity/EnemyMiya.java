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

public class EnemyMiya extends Actor {
	static final int  DELAY_RANDOM = 5;
	TextureRegion[] npcRegions = new TextureRegion[4];
	TextureRegion[] regions = null;
	int regionsIndex = 0;
	
	float x ,y ;
	public int direction = Digs.RND.nextBoolean()?1:-1;
	public int velocity = 1;
	public Sprite drawable = new Sprite();
	
	//the main terrain
	protected Level level;
	static final float N_WIDTH = 16f;
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
		x = this.getX(); //got it x
		y = this.getY(); //got it y
		if (x < 8) direction = 1;//edge
		if (x > level.getWidth() - 10) direction = -1;//edge
		
		if(userDefAction()) return;	//when the addActions run , block everything until the action done
		if(tryKillRay()) return;	//if the npc touch the kill circle , then kill it , block
	
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
	public void sync(){
		this.setX(x);
		this.setY(y);
		drawable.setRegion(regions[(regionsIndex/2)%regions.length]);
		drawable.setColor(this.getColor());
		drawable.setPosition(x-this.getOriginX(), y-this.getOriginY());
		if(direction<0)drawable.flip(true, false);
	}

	boolean userDefAction(){
		if(this.getActions().size > 0){
			sync();
			return true;
		}
		return false;
	}
	boolean tryKillRay(){
		for(KillCircleEntity kill:level.getKillrays()){
			if(kill.overlaps(x, y) || kill.overlaps(x, y+8)){//the bottom and top
				die();
				Engine.getSoundManager().playSound("SoundDie");//TODO : the sound for the Kaka...
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
	}
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
