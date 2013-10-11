package info.u250.digs.scenes.game;

import java.util.Random;

import net.shad.s3rend.gfx.pixmap.filter.Glow;
import net.shad.s3rend.gfx.pixmap.filter.Noise;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class GoldPixmap extends Pixmap {

	public GoldPixmap(int width, int height,int number) {
		super(width, height, Format.RGBA8888);
		
		
		Pixmap.setBlending(Blending.SourceOver);
		Random r = new Random();
		for(int i=0;i<number;i++){
			int radius = r.nextInt(30)+5;
			Pixmap map = new Pixmap(radius*2,radius*2,Format.RGBA8888);
			map.setColor(Color.YELLOW);
			map.fillCircle(radius, radius, radius);
			new Glow().generate(map);
			new Noise().generate(map);
			this.drawPixmap(map, (int)(100+r.nextFloat()*1800),this.getHeight()-(int)(50+r.nextFloat()*250));
			map.dispose();
		}
		
	}

}
