package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.game.Dock;
import info.u250.digs.scenes.game.Level;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseEntity extends Actor {
	TextureRegion[] npcRegions = new TextureRegion[4];
	TextureRegion[] npcGoldRegions = new TextureRegion[4];
	TextureRegion[] regions = null;
	int regionsIndex = 0;
	public BaseEntity(){
		this.setSize(7, 10);
		this.drawable.setSize(10.5f, 15);
		
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
	        r1 = random.nextInt(256);
	        g1 = random.nextInt(256);
	        b1 = random.nextInt(256);
	        if (r1+g1+b1 > threshold) break;
	    }
	    Color c =  new Color(r1/255f, g1/255f, b1/255f,1);
	    return c;
	}
	private Random random   = new Random();
	float x ,y ;
	public int direction = random.nextBoolean()?1:-1;
	public int velocity = 1;
	private boolean isHoldGold = false;

	public Sprite drawable = new Sprite();

	//the main terrain
	protected Level level;
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
		
		if (velocity > 1) { // when the NPC is jumping
			if (level.tryMove(x+direction, y + velocity / 8)) {
				x += direction;
				y += velocity / 8;
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
					velocity = 0;
				}
			} else	{
				if (random.nextInt(10) != 0) {
					// Assume the miner has hit a wall
					boolean hit = true;
					for (int p = 1; p <= 4; p++) {
						if (level.tryMove(x+direction, y+p)) {
							x += direction;
							y += p;
							hit = false;
							regionsIndex++;
							break;
						}
					}
					if (random.nextInt(hit ? 10 : 4000) == 0) {
						direction *= -1;
						if (hit) {
							if(random.nextInt(3)!=0)velocity = 16;
						}else{
							if(random.nextInt(3)==0)velocity = 16;
						}
					}
				}
			}
		}
		if(isHoldGold){
			for(Dock dock:level.docks){
				if(this.drawable.getBoundingRectangle().overlaps(dock.actor.getBoundingRectangle())){
					isHoldGold = false;
					this.direction *= -1;
					dock.number++;
					regions = npcRegions;
				}
			}
		}else{
			if(level.tryDig()){
				isHoldGold = true;
				direction *= -1;
				regions = npcGoldRegions;
			}
		}
		this.setX(x);
		this.setY(y);

		drawable.setRegion(regions[(regionsIndex/2)%4]);
		drawable.setColor(this.getColor());
		drawable.setPosition(x, y);
		if(direction<0)drawable.flip(true, false);
	}
	
	public Sprite getDrawable(){
		return drawable;
	}
}
