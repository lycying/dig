package info.u250.digs.scenes.game;

import info.u250.c2d.graphic.pixmap.PixmapHelper;

import java.util.Random;

import net.shad.s3rend.gfx.pixmap.filter.Glow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class MapMaker {
	public static Pixmap genMap(TerrainConfig config){	
		final Random r = new Random();
		final int width = config.width;
		final int height = 512;
		
		final Pixmap backgroundPixmap = new Pixmap(Gdx.files.internal(config.surfaceFile));
		final Pixmap grassPixmap = new Pixmap(Gdx.files.internal("data/grass.png"));
		final Pixmap grassPixmap2 = new Pixmap(Gdx.files.internal("data/grass2.png"));
		
		final  Pixmap map = new Pixmap(width, height, Format.RGBA8888);
		//round one , to make a full pixmap
		for(int i=0;i*backgroundPixmap.getWidth()< width;i++){
			for(int j=0;j*backgroundPixmap.getHeight()<height;j++){
				map.drawPixmap(
						backgroundPixmap, 
						i*backgroundPixmap.getWidth(),
						j*backgroundPixmap.getHeight(), 
						0, 0, backgroundPixmap.getWidth(), backgroundPixmap.getHeight());
			}
		}
		

		
		Random random = new Random();
		final CatmullRomSpline<Vector2> spline = new CatmullRomSpline<Vector2>();
		int controlPointsNum = config.segment;
		final int baseHeight = config.baseHeight;
		final int ramdomHeight = 50;
		Vector2[] controlPoints = new Vector2[controlPointsNum];
		for(int i=0;i<controlPointsNum;i++){
			controlPoints[i] = new Vector2(i,(random.nextBoolean()?1:-1)*random.nextInt(ramdomHeight)+baseHeight);
		}
		spline.set(controlPoints, false);

		for(int i=0;i<width;i+=10){
			spline.valueAt(tmpV, i/(float)width);
			Pixmap.setBlending(Blending.SourceOver);
			map.drawPixmap(r.nextBoolean()?grassPixmap2:grassPixmap, i, map.getHeight()-(int)tmpV.y - (int)(10+20*r.nextFloat()));
		}
		for(int i=0;i<width;i++){
			spline.valueAt(tmpV, i/(float)width);
			Pixmap.setBlending(Blending.None);
			map.setColor(PixmapHelper.TransparentColor);
			map.fillRectangle(i , 0 , 1, map.getHeight()-(int)tmpV.y);
		}
		
		backgroundPixmap.dispose();
		grassPixmap2.dispose();
		grassPixmap.dispose();
		
//		Gradient.generate(map,Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, 1.0f);
		Glow.generate(map, Color.WHITE, 0.2f, 1.0f-400f/512f, 0.5f, 0.6f, 10, 10);
		
		return map;
	}
	static final Vector2 tmpV = new Vector2();
}
