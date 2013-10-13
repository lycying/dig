package info.u250.digs.scenes.game;

import java.util.Random;

import net.shad.s3rend.gfx.pixmap.filter.Glow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

final class LevelMaker {
	static final Vector2 tmpV = new Vector2();
	static final Random RND = new Random();
	static final int RND_Height  = 50;
	static final CatmullRomSpline<Vector2> spline = new CatmullRomSpline<Vector2>();
	
	public static final Pixmap[] gen(LevelConfig config){
		Pixmap[] maps = new Pixmap[2];
		
		final int width = config.width;
		final int height = config.height;
		final int segment = config.segment;
		final int lineHeight = config.lineHeight;
		
		final Pixmap bgPix = new Pixmap(Gdx.files.internal(config.surface));
		final Pixmap gPix1 = new Pixmap(Gdx.files.internal("data/grass1.png"));
		final Pixmap gPix2 = new Pixmap(Gdx.files.internal("data/grass2.png"));
		
		final  Pixmap terMap = new Pixmap(width, height, Format.RGBA8888);
		//round one , to make a full pixmap
		for(int i=0;i*bgPix.getWidth()< width;i++){
			for(int j=0;j*bgPix.getHeight()<height;j++){
				terMap.drawPixmap(bgPix, 
						i*bgPix.getWidth(),
						j*bgPix.getHeight(), 
						0, 0, bgPix.getWidth(), bgPix.getHeight());
			}
		}
		
		Vector2[] controlPoints = new Vector2[segment];
		for(int i=0;i<segment;i++){
			controlPoints[i] = new Vector2(i,(RND.nextBoolean()?1:-1)*RND.nextInt(RND_Height)+lineHeight);
		}
		spline.set(controlPoints, false);

		for(int i=0;i<width;i+=10){
			spline.valueAt(tmpV, i/(float)width);
			Pixmap.setBlending(Blending.SourceOver);
			terMap.drawPixmap(RND.nextBoolean()?gPix2:gPix1, i, terMap.getHeight()-(int)tmpV.y - (int)(10+20*RND.nextFloat()));
		}
		for(int i=0;i<width;i++){
			spline.valueAt(tmpV, i/(float)width);
			Pixmap.setBlending(Blending.None);
			terMap.setColor(Color.CLEAR);
			terMap.fillRectangle(i , 0 , 1, terMap.getHeight()-(int)tmpV.y);
		}
		
		bgPix.dispose();
		gPix2.dispose();
		gPix1.dispose();
		
		Glow.generate(terMap, Color.WHITE, 0.2f, 1.0f-400f/512f, 0.5f, 0.6f, 10, 10);
		
		/////////////////////////////////////////////////////////////////////////////////////
		Pixmap gdMap =  new Pixmap(width, lineHeight, Format.RGBA8888);
		Pixmap.setBlending(Blending.SourceOver);
		gdMap.setColor(Color.YELLOW);
		for(int i=0;i<10;i++){
			int radius = RND.nextInt(30)+5;
//			Pixmap tempPxp = new Pixmap(radius*2,radius*2,Format.RGBA8888);
//			tempPxp.setColor(Color.YELLOW);
//			tempPxp.fillCircle(radius, radius, radius);
//			Glow.generate(tempPxp,Color.WHITE, 0.5f, 0.5f, 0.25f, 0.25f, 10, 10);
//			Noise.generate(tempPxp,64, 64, 64);
//			gdMap.drawPixmap(tempPxp, (int)(100+RND.nextFloat()*1800),gdMap.getHeight()-(int)(50+RND.nextFloat()*250));
//			tempPxp.dispose();
			gdMap.fillCircle((int)(100+RND.nextFloat()*1800),gdMap.getHeight()-(int)(50+RND.nextFloat()*250), radius);
		}

		maps[0] = terMap;
		maps[1] = gdMap;
		return maps;
	}
}
