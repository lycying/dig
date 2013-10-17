package info.u250.digs.scenes.game;

import info.u250.digs.Digs;
import net.shad.s3rend.gfx.pixmap.filter.Glow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

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
		
		Vector2[] controlPoints = new Vector2[segment];
		for(int i=0;i<segment;i++){
			controlPoints[i] = new Vector2(i,(Digs.RND.nextBoolean()?1:-1)*Digs.RND.nextInt(RND_Height)+lineHeight);
		}
		spline.set(controlPoints, false);

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
		
		bgPix.dispose();
		gPix2.dispose();
		gPix1.dispose();
		
		Glow.generate(terMap, Color.WHITE, 0.2f, 1.0f-400f/512f, 0.5f, 0.6f, 10, 10);
		
		/////////////////////////////////////////////////////////////////////////////////////
		Pixmap gdMap =  new Pixmap(width, lineHeight, Format.RGBA8888);
		Pixmap.setBlending(Blending.None);
		
//		for(int i=0;i<10;i++){
//			int radius = RND.nextInt(30)+5;
//			int x = (int)(100+RND.nextFloat()*1800);
//			int y = gdMap.getHeight()-(int)(50+RND.nextFloat()*250);
//			while(x-radius<0 || x+radius>width || y-radius<0 || y+radius>lineHeight){
//				radius = RND.nextInt(30)+5;
//				x = (int)(100+RND.nextFloat()*1800);
//				y = gdMap.getHeight()-(int)(50+RND.nextFloat()*250);
//			}
//			gdMap.fillCircle(x,y, radius);
//		}
		gdMap.setColor(Color.YELLOW);
		Polygon polygon =  new Polygon(new float[]{48.0f,11.0f,58.0f,15.0f,70.0f,24.0f,92.0f,49.0f,112.0f,61.0f,116.0f,67.0f,113.0f,77.0f,113.0f,89.0f,116.0f,101.0f,122.0f,108.0f,123.0f,114.0f,120.0f,117.0f,110.0f,118.0f,79.0f,111.0f,57.0f,113.0f,18.0f,112.0f,10.0f,110.0f,10.0f,107.0f,30.0f,104.0f,31.0f,101.0f,25.0f,98.0f,11.0f,84.0f,7.0f,77.0f,6.0f,67.0f,38.0f,21.0f,});
		polygon.setScale(2, 2);
		polygon.translate(200, 50);
		drawPolygon(polygon, gdMap);
		
		gdMap.setColor(Color.CYAN);
		Polygon bomb =  new Polygon(new float[]{92.0f,1.0f,96.0f,1.0f,118.0f,23.0f,124.0f,34.0f,113.0f,46.0f,103.0f,52.0f,95.0f,45.0f,91.0f,46.0f,69.0f,68.0f,63.0f,79.0f,67.0f,84.0f,70.0f,95.0f,68.0f,99.0f,49.0f,115.0f,35.0f,122.0f,25.0f,124.0f,13.0f,122.0f,6.0f,116.0f,2.0f,107.0f,2.0f,100.0f,7.0f,84.0f,18.0f,67.0f,29.0f,57.0f,35.0f,56.0f,47.0f,63.0f,52.0f,62.0f,58.0f,58.0f,80.0f,30.0f,75.0f,25.0f,74.0f,19.0f,85.0f,7.0f,});
		bomb.translate(400, 50);
		bomb.rotate(50);
		drawPolygon(bomb, gdMap);
		
		maps[0] = terMap;
		maps[1] = gdMap;
		return maps;
	}
	static void drawPolygon(Polygon polygon,Pixmap pixmap){
		float[] polygonVertices = polygon.getTransformedVertices();
		ShortArray array = ear.computeTriangles(polygonVertices);
		for(int i=0;i<array.size;i+=3){
			int x1 = (int)polygonVertices[2*array.get(i)];
			int y1 = (int)polygonVertices[2*array.get(i)+1];
			int x2 = (int)polygonVertices[2*array.get(i+1)];
			int y2 = (int)polygonVertices[2*array.get(i+1)+1];
			int x3 = (int)polygonVertices[2*array.get(i+2)];
			int y3 = (int)polygonVertices[2*array.get(i+2)+1];
			pixmap.fillTriangle(x1, y1, x2, y2, x3, y3);
		}
	}
	static EarClippingTriangulator ear = new EarClippingTriangulator();
}

