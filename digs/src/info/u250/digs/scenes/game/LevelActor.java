package info.u250.digs.scenes.game;

import info.u250.digs.PixmapHelper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelActor extends Actor{
	PixmapHelper px1,px2;
	public LevelActor(PixmapHelper px1,PixmapHelper px2){
		this.px1 = px1;
		this.px2 = px2;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		px1.sprite.draw(batch);
		px2.sprite.draw(batch);
		super.draw(batch, parentAlpha);
	}
}
