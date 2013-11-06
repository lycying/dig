package info.u250.digs.scenes.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ShortArray;

public abstract class LevelMakeCallBack{
	public abstract void before(Level level);
	public abstract void after(Level level);
	public abstract void mapMaker(Pixmap terr,Pixmap gold);
	protected void drawPolygon(Polygon polygon,Pixmap pixmap){
		polygon.setPosition(polygon.getX(),pixmap.getHeight()-polygon.getY()-polygon.getBoundingRectangle().height);
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
	EarClippingTriangulator ear = new EarClippingTriangulator();
}