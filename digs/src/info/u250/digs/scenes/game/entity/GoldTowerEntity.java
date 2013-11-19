package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class GoldTowerEntity extends Group{
	int max ;
	int number = 0;
	private Rectangle rect = new Rectangle();
	Image dock;
	Sprite gold;
	private final Array<Ka> kas = new Array<Ka>();
	
	public GoldTowerEntity(){
		TextureAtlas atlas = Engine.resource("All",TextureAtlas.class);
		final Image tower = new Image(atlas.findRegion("tower"));
		tower.setY(-400);
		this.setSize(tower.getWidth(),100);
		dock = new Image(atlas.findRegion("dock"));
		dock.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 10,0.5f),
				Actions.moveBy(0, -10,0.5f)
				)));
		ParticleEffect e = Engine.resource("Effect");
		ParticleEffectActor p = new ParticleEffectActor(e,"fire");
		p.setPosition(this.getWidth()/2-p.getWidth()/2, 15);
		
		this.addActor(tower);
		this.addActor(p);
		this.addActor(dock);
		
		this.gold = new Sprite(Engine.resource("All",TextureAtlas.class).findRegion("gold"));
		this.max = (int)((dock.getWidth()-8)/(this.gold.getWidth()));
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(Ka ka:kas){
			ka.onTower(this);
			ka.draw(batch, parentAlpha);
		}
		super.draw(batch, parentAlpha);
		int bak = number;
		int i = 0,j=0;
		while(bak>0){
			if(i%(max-j)==0){
				j++;
				i=0;
				if(j == max){
					//TODO: collection
					break;
				}
			}
			gold.setPosition(2*j+this.getX()+i*gold.getWidth()+4, this.getY()+dock.getHeight()+dock.getY()+gold.getHeight()*j-8);
			gold.draw(batch);
			bak--;
			i++;
		}
	}
	public Rectangle getRect(){
		rect.x = this.getX()+dock.getX();
		rect.y = this.getY()+dock.getY();
		rect.width = this.dock.getWidth();
		rect.height = this.dock.getHeight();
		return this.rect;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void addNumber(){
		this.number++;
	}
	public void addKa(Ka ka){
		this.kas.add(ka);
	}
	public int getKaNumber(){
		return this.kas.size;
	}
}
