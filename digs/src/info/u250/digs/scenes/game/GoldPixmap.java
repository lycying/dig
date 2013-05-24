package info.u250.digs.scenes.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class GoldPixmap extends Pixmap {

	public GoldPixmap(int width, int height,int number) {
		super(width, height, Format.RGBA8888);
		
		
		this.setColor(Color.YELLOW);
		
		Random r = new Random();
		for(int i=0;i<number;i++){
			this.fillCircle(
					(int)(100+r.nextFloat()*1800),
					this.getHeight()-
					(int)(50+r.nextFloat()*250), 
					r.nextInt(30)+5);
		}
		
	}

}
