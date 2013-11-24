package info.u250.digs.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ShortArray;

public abstract class LevelMakeCallBack{
	public abstract void before(Level level);
	public abstract void after(Level level);
	public abstract void mapMaker(Pixmap terr,Pixmap gold);
	public void dispose(){}
	protected void drawPolygon(Polygon polygon,Pixmap pixmap){
		Pixmap.setBlending(Blending.None);
		polygon.setPosition(polygon.getX(),pixmap.getHeight()-polygon.getY()-polygon.getBoundingRectangle().height);
		float[] polygonVertices = polygon.getTransformedVertices();
		ShortArray array = EAR.computeTriangles(polygonVertices);
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
	protected void drawPixmapDeco(Pixmap pixmap,String deco,int x,int y){
		final Pixmap p = new Pixmap(Gdx.files.internal("paint/"+deco+".png"));
		Pixmap.setBlending(Blending.SourceOver);
		pixmap.drawPixmap(p, x, pixmap.getHeight()-y-p.getHeight());
		p.dispose();
	}
	protected void drawPixmapDeco(Pixmap pixmap,String deco,int x,int y,float scale){
		this.drawPixmapDeco(pixmap, deco, x, y, scale,scale);
	}
	protected void drawPixmapDeco(Pixmap pixmap,String deco,int x,int y,float scaleX,float scaleY){
		final Pixmap p = new Pixmap(Gdx.files.internal("paint/"+deco+".png"));
		Pixmap.setBlending(Blending.SourceOver);
		pixmap.drawPixmap(p,0,0,p.getWidth(),p.getHeight(), x, pixmap.getHeight()-y-(int)(p.getHeight()*scaleY),(int)(p.getWidth()*scaleX),(int)(p.getHeight()*scaleY));
		p.dispose();
	}
	protected void fillCircle(Pixmap pixmap,int x,int y,int radius){
		Pixmap.setBlending(Blending.None);
		pixmap.fillCircle(x, pixmap.getHeight()-y, radius);
	}
	protected void fillRect(Pixmap pixmap,int x,int y,int width,int height){
		Pixmap.setBlending(Blending.None);
		pixmap.fillRectangle(x, pixmap.getHeight()-y-height, width,height);
	}
	final static EarClippingTriangulator EAR = new EarClippingTriangulator();
}