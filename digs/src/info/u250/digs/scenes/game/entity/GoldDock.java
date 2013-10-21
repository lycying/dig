package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GoldDock extends Group{
	public int number = Digs.RND.nextInt(100)+20;
	private Rectangle rect = new Rectangle();
	Image dock;
	Sprite gold;
	int max ;
	public GoldDock(){
		TextureAtlas atlas = Engine.resource("All",TextureAtlas.class);
		final Image tower = new Image(atlas.findRegion("tower"));
		tower.setY(-400);
		this.setSize(tower.getWidth(),100);
		dock = new Image(atlas.findRegion("dock"));
		dock.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 10,0.5f),
				Actions.moveBy(0, -10,0.5f)
				)));
		
		this.addActor(tower);
		this.addActor(dock);
		this.gold = new Sprite(Engine.resource("All",TextureAtlas.class).findRegion("gold"));
		this.max = (int)((dock.getWidth()-8)/(this.gold.getWidth()));
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
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
}
