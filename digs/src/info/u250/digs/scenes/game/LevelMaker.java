package info.u250.digs.scenes.game;

import info.u250.digs.Digs;
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
		
		if(segment>1){
			Vector2[] controlPoints = new Vector2[segment];
			controlPoints[0] = new Vector2(0,lineHeight);
			for(int i=1;i<segment;i++){
				controlPoints[i] = new Vector2(i,(Digs.RND.nextBoolean()?1:-1)*Digs.RND.nextInt(RND_Height)+lineHeight);
			}
			spline.set(controlPoints, true);

			for(int i=0;i<width;i+=10){
				spline.valueAt(tmpV, i/(float)width);
				Pixmap.setBlending(Blending.SourceOver);
				terMap.drawPixmap(Digs.RND.nextBoolean()?gPix2:gPix1, i, terMap.getHeight()-(int)tmpV.y - (int)(10+20*Digs.RND.nextFloat()));
			}
			for(int i=0;i<width;i++){
				spline.valueAt(tmpV, i/(float)width);
				Pixmap.setBlending(Blending.None);
				terMap.setColor(Color.CLEAR);
				terMap.fillRectangle(i , 0 , 1, terMap.getHeight()-(int)tmpV.y);
			}
		}else{//draw directly 
			for(int i=0;i<width;i+=20){
				Pixmap.setBlending(Blending.SourceOver);
				terMap.drawPixmap(Digs.RND.nextBoolean()?gPix2:gPix1, i, terMap.getHeight()-(int)lineHeight - (int)(10+20*Digs.RND.nextFloat()));
			}
			Pixmap.setBlending(Blending.None);
			terMap.setColor(Color.CLEAR);
			terMap.fillRectangle(0 , 0 , width, terMap.getHeight()-(int)lineHeight);
		}
		
		
		bgPix.dispose();
		gPix2.dispose();
		gPix1.dispose();
		
		Glow.generate(terMap, Color.WHITE, 0.2f, 1.0f-400f/512f, 0.5f, 0.6f, 10, 10);
		
		/////////////////////////////////////////////////////////////////////////////////////
		Pixmap gdMap =  new Pixmap(width, height, Format.RGBA8888);
		Pixmap.setBlending(Blending.None);
		if(null!=config.callback){
			config.callback.mapMaker(terMap, gdMap);
		}
		
		maps[0] = terMap;
		maps[1] = gdMap;
		return maps;
	}
}

