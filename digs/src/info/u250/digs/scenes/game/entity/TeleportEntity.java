package info.u250.digs.scenes.game.entity;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TeleportEntity extends Actor{
	private static final float WWWWW = 10;
	Animation in ;
	Animation out ;
	boolean clear = false;
	private Rectangle rect = new Rectangle();
	Path<Vector2> path = null;
//	float len = 0;
	Vector2 startPoint = new Vector2();
	Vector2 endPoint = new Vector2();
	float percentStep = 0;
	public TeleportEntity(float inx,float iny,float outx,float outy){
		this(inx, iny, outx, outy, new Color(1, 1, 1, 0.5f), new Color(0, 1, 0, 0.5f));
	}
	public TeleportEntity(float inx,float iny,float outx,float outy,Color color1,Color color2){
		startPoint.set(inx+20,iny+20);
		endPoint.set(outx+20,outy+20);
		TextureAtlas atlas = Engine.resource("All",TextureAtlas.class);
		in = new Animation(0.1f,atlas.findRegion("in1"),
				atlas.findRegion("in2"),
				atlas.findRegion("in3"),
				atlas.findRegion("in4"),
				atlas.findRegion("in5"),
				atlas.findRegion("in6"));
		out = new Animation(0.1f,atlas.findRegion("out1"),
				atlas.findRegion("out2"),
				atlas.findRegion("out3"),
				atlas.findRegion("out4"),
				atlas.findRegion("out5"),
				atlas.findRegion("out6"));
		
		
		Vector2[] vpath = new Vector2[3];
		vpath[0] = new Vector2(startPoint);//the start point
		vpath[2] = new Vector2(endPoint);//the last point
//		vpath[1] = new Vector2((vpath[0].x+vpath[2].x)/2,(vpath[0].y+vpath[2].y)/2);
		vpath[1] = new Vector2(
				(vpath[0].x+vpath[2].x)/2+Digs.RND.nextFloat()*(inx-outx)/2*(Digs.RND.nextBoolean()?1:-1),
				(vpath[0].y+vpath[2].y)/2+Digs.RND.nextFloat()*(iny-outy)/2*(Digs.RND.nextBoolean()?1:-1));
		
//		vpath[2] = new Vector2(
//				(vpath[0].x+vpath[3].x)/3*2+Digs.RND.nextFloat()*(inx-outx)/4*(Digs.RND.nextBoolean()?1:-1),
//				(vpath[0].y+vpath[3].y)/3*2+Digs.RND.nextFloat()*(iny-outy)/4*(Digs.RND.nextBoolean()?1:-1));
		
		path = new Bezier<Vector2>(vpath);
//		path = new BSpline<Vector2>(vpath,3,true);
//		last.set(outx, outy);
		
		this.color1.set(color1);
		this.color2.set(color2);
		this.color1.a = 0.5f;
		this.color2.a = 0.5f;
		
		percentStep = WWWWW/new Vector2(inx,iny).dst(outx, outy);
		
		rect.x = inx+8;
		rect.y = iny+8;
		rect.width = 24;
		rect.height = 24;
		
		this.setSize(Math.max(inx, outx)+40, Math.max(iny, outy)+40);
		
	}
	Vector2 tmpV1 = new Vector2(Integer.MAX_VALUE,0);
	Vector2 tmpV2 = new Vector2();
//	Vector2 last = new Vector2();
	int index =0 ;
	final Color color1 = new Color(1, 1, 1, 0.5f);
	final Color color2 = new Color(0, 1, 0, 0.5f);
	void renderPath(float delta){
		index = 0;
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glLineWidth(5);
		Engine.getShapeRenderer().begin(ShapeType.Line);
		
//		for(float percent=0;percent<1f;percent+=percentStep){
//			if(index%2==0){
//				Engine.getShapeRenderer().setColor(color1);
//			}else{
//				Engine.getShapeRenderer().setColor(color2);
//			}
//			tmpV2.set(startX + (endX - startX) * percent,startY + (endY - startY) * percent);
//			localToStageCoordinates(tmpV2);
//			if(tmpV1.x != Integer.MAX_VALUE){
//				Engine.getShapeRenderer().line(tmpV1, tmpV2);
//			}
//			tmpV1.set(tmpV2);
//			index++;
//		}
		
//		for(float t = 0;t<this.getWidth();t+=6+(stateTime%1)*6,index++){
		index = 0;
		for(float percent=(stateTime/10f)%percentStep;percent<1f;percent+=percentStep){
			if(index%2==0){
				Engine.getShapeRenderer().setColor(color1);
			}else{
				Engine.getShapeRenderer().setColor(color2);
			}
			
			path.valueAt(tmpV2, percent);
//			tmpV2.add(((stateTime/8)%percentStep)*(endPoint.x-startPoint.x),((stateTime/8)%percentStep)*(endPoint.y-startPoint.y));
//			if(tmpV2.x>this.getWidth()||tmpV2.y>this.getHeight())
			localToStageCoordinates(tmpV2);
			if(tmpV1.x != Integer.MAX_VALUE){
				Engine.getShapeRenderer().line(tmpV1, tmpV2);
			}else{
				tmpV1.set(startPoint);
				localToStageCoordinates(tmpV1);
				Engine.getShapeRenderer().line(tmpV1, tmpV2);
			}
			tmpV1.set(tmpV2);
			index++;
		}
		tmpV2.set(endPoint);
		localToStageCoordinates(tmpV2);
		Engine.getShapeRenderer().line(tmpV1, tmpV2);
//		last.set(this.getWidth()+8, this.getHeight()+20);
		Engine.getShapeRenderer().end();
		Gdx.gl.glLineWidth(1);
		tmpV1.set(Integer.MAX_VALUE,0);
	}
	float stateTime = 0;
	@Override
	public void act(float delta) {
		stateTime += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		renderPath(Engine.getDeltaTime());
		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(in.getKeyFrame(stateTime, true), this.getX()+startPoint.x-20,this.getY()+startPoint.y-20);
		batch.draw(out.getKeyFrame(stateTime, true),this.getX()+endPoint.x-20,this.getY()+endPoint.y-20);
	}
	public Rectangle getRect(){
		return this.rect;
	}
	public float getTransX(){
		return endPoint.x+this.getX()-20;
	}
	public float getTransY(){
		return this.getY()+endPoint.y-20;
	}
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	
}
