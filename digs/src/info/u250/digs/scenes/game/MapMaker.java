package info.u250.digs.scenes.game;

import info.u250.c2d.graphic.pixmap.PixmapHelper;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class MapMaker {
	public static Pixmap genMap(TerrainConfig config){		
//		final int width = Math.round(Engine.getWidth());
//		final int height = Math.round(Engine.getHeight());
		final int width = config.width;
		final int height = 512;
		
		final Pixmap backgroundPixmap = new Pixmap(Gdx.files.internal(config.surfaceFile));
		
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
		int controlPointsNum = 15;
		final int baseHeight = 450;
		final int ramdomHeight = 50;
		Vector2[] controlPoints = new Vector2[controlPointsNum];
		for(int i=0;i<controlPointsNum;i++){
			controlPoints[i] = new Vector2(i,(random.nextBoolean()?1:-1)*random.nextInt(ramdomHeight)+baseHeight);
		}
		spline.set(controlPoints, false);
		for(int i=0;i<width;i++){
			spline.valueAt(tmpV, i/(float)width);
			Pixmap.setBlending(Blending.None);
			map.setColor(PixmapHelper.TransparentColor);
			map.fillRectangle(i , 0 , 1, map.getHeight()-(int)tmpV.y);
			
//			Pixmap.setBlending(Blending.SourceOver);
//			map.setColor(Color.LIGHT_GRAY);
//			map.fillCircle(i, map.getHeight()-(int)tmpV.y, 5);
		}
		
//		final Pixmap test = new Pixmap(Gdx.files.internal("data/test.png"));
//		Pixmap.setBlending(Blending.SourceOver);
//		map.drawPixmap(test, 100, 100);
		
		backgroundPixmap.dispose();
		
		return map;
	}
	static final Vector2 tmpV = new Vector2();
}
