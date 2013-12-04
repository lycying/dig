package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
//import info.u250.svg.SVGParse;
//import info.u250.svg.elements.SVGRootElement;
//import info.u250.svg.glutils.SVGData;
import net.shad.s3rend.gfx.pixmap.filter.Glow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

final class LevelPixmapMaker {
	static final Vector2 tmpV = new Vector2();
	static final CatmullRomSpline<Vector2> spline = new CatmullRomSpline<Vector2>();
	
	public static final Pixmap[] gen(final LevelConfig config){
		Pixmap[] maps = new Pixmap[2];		
		final int width = config.width;
		final int height = config.height;
		
		final Pixmap bgPix = new Pixmap(Gdx.files.internal(config.surface));
		final Pixmap gPix1 = new Pixmap(Gdx.files.internal("paint/grass1.png"));
		final Pixmap gPix2 = new Pixmap(Gdx.files.internal("paint/grass2.png"));
		
		final Pixmap terMap = new Pixmap(width, height, Format.RGBA8888);
		final Pixmap gdMap =  new Pixmap(width, height, Format.RGBA8888);
		
		if(config instanceof HookLevelConfig){
			final HookLevelConfig configReal = HookLevelConfig.class.cast(config);
			final int lineHeight = configReal.lineHeight;
			final int segment = configReal.segment;
			
			Pixmap.setBlending(Blending.SourceOver);
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
			controlPoints[0] = new Vector2(0,lineHeight);
			for(int i=1;i<segment;i++){
				controlPoints[i] = new Vector2(i,(Digs.RND.nextBoolean()?1:-1)*Digs.RND.nextInt(configReal.ascent)+lineHeight);
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
			if(height<=Engine.getHeight()){
				Glow.generate(terMap, Color.WHITE, 0.2f, 1.0f-lineHeight/512f, 0.5f, 0.6f, 10, 10);
			}
		}else if(config instanceof  LineLevelConfig){
			final LineLevelConfig configReal = LineLevelConfig.class.cast(config);
			final int lineHeight = configReal.lineHeight;
			Pixmap.setBlending(Blending.SourceOver);
			//round one , to make a full pixmap
			for(int i=0;i*bgPix.getWidth()< width;i++){
				for(int j=0;j*bgPix.getHeight()<height;j++){
					terMap.drawPixmap(bgPix, 
							i*bgPix.getWidth(),
							j*bgPix.getHeight(), 
							0, 0, bgPix.getWidth(), bgPix.getHeight());
				}
			}
			//draw directly 
			for(int i=0;i<width;i+=20){
				Pixmap.setBlending(Blending.SourceOver);
				terMap.drawPixmap(Digs.RND.nextBoolean()?gPix2:gPix1, i, terMap.getHeight()-(int)lineHeight - (int)(10+20*Digs.RND.nextFloat()));
			}
			Pixmap.setBlending(Blending.None);
			terMap.setColor(Color.CLEAR);
			terMap.fillRectangle(0 , 0 , width, terMap.getHeight()-(int)lineHeight);
			if(height<=Engine.getHeight()){
				Glow.generate(terMap, Color.WHITE, 0.2f, 1.0f-lineHeight/512f, 0.5f, 0.6f, 10, 10);
			}
		}else if(config instanceof  FaceLevelConfig){
			final FaceLevelConfig configReal = FaceLevelConfig.class.cast(config);
			final Vector2[] faces = configReal.faces;
			
			Pixmap.setBlending(Blending.SourceOver);
			//round one , to make a full pixmap
			for(int i=0;i*bgPix.getWidth()< width;i++){
				for(int j=0;j*bgPix.getHeight()<height;j++){
					terMap.drawPixmap(bgPix, 
							i*bgPix.getWidth(),
							j*bgPix.getHeight(), 
							0, 0, bgPix.getWidth(), bgPix.getHeight());
				}
			}
			Color grassColor = Color.valueOf("00a100");
			for(int fi=1;fi<faces.length;fi++){
				final Vector2 faceIsub = faces[fi-1];
				final Vector2 faceI = faces[fi];
				//draw directly 
				for(int i=(int)faceIsub.x;i<faceI.x-100;i+=20){
					Pixmap.setBlending(Blending.SourceOver);
					terMap.drawPixmap(Digs.RND.nextBoolean()?gPix2:gPix1, i, terMap.getHeight()- (int)faceIsub.y - (int)(10+20*Digs.RND.nextFloat()));
				}
				Pixmap.setBlending(Blending.None);
				terMap.setColor(grassColor);
				if(faceI.y>faceIsub.y){
					terMap.fillRectangle( (int)faceI.x , 0 , 20 , terMap.getHeight()-(int)faceIsub.y+20);
				}else{
					terMap.fillRectangle( (int)faceI.x-20 , 0 , 20 , terMap.getHeight()-(int)faceI.y+20);
				}
				terMap.fillRectangle( (int)faceIsub.x , terMap.getHeight()-(int)faceIsub.y , (int)(faceI.x-faceIsub.x) ,20);
				terMap.setColor(Color.CLEAR);
				terMap.fillRectangle( (int)faceIsub.x , 0 , (int)(faceI.x-faceIsub.x) , terMap.getHeight()-(int)faceIsub.y);
			}
			if(height<=Engine.getHeight()){
				Glow.generate(terMap, Color.WHITE, 0.2f, 1.0f-configReal.lightLine/512f, 0.5f, 0.6f, 10, 10);
			}
		}
		
		
		bgPix.dispose();
		gPix2.dispose();
		gPix1.dispose();

		/////////////////////////////////////////////////////////////////////////////////////
		
		if(null!=config.levelMakeCallback){
			config.levelMakeCallback.mapMaker(terMap, gdMap);
		}
		
		maps[0] = terMap;
		maps[1] = gdMap;
		return maps;
	}
}

