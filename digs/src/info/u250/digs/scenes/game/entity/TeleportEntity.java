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
	Animation in ;
	Animation out ;
	boolean clear = false;
	private Rectangle rect = new Rectangle();
	Path<Vector2> path = null;
	float len = 0;
	public TeleportEntity(float inx,float iny,float outx,float outy){
		this(inx, iny, outx, outy, new Color(1, 1, 1, 0.5f), new Color(0, 1, 0, 0.5f));
	}
	public TeleportEntity(float inx,float iny,float outx,float outy,Color color1,Color color2){
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
		this.setPosition(inx, iny);
		this.setSize(Math.abs(outx-inx), Math.abs(iny-outy));
		
		rect.x = inx+8;
		rect.y = iny+8;
		rect.width = 24;
		rect.height = 24;
		
		len = outx-inx-37+3;
		Vector2[] vpath = new Vector2[4];
		vpath[0] = new Vector2(30,20);
		vpath[3] = new Vector2(this.getWidth(),this.getHeight());
		vpath[1] = new Vector2(
				(vpath[0].x+vpath[3].x)/3+Digs.RND.nextFloat()*100*(Digs.RND.nextBoolean()?1:-1),
				(vpath[0].y+vpath[3].y)/3+Digs.RND.nextFloat()*100*(Digs.RND.nextBoolean()?1:-1));
		vpath[2] = new Vector2(
				(vpath[0].x+vpath[3].x)/3*2+Digs.RND.nextFloat()*100*(Digs.RND.nextBoolean()?1:-1),
				(vpath[0].y+vpath[3].y)/3*2+Digs.RND.nextFloat()*100*(Digs.RND.nextBoolean()?1:-1));
		
		path = new Bezier<Vector2>(vpath);
		last.set(this.getWidth()+8, this.getHeight()+20);
		
		this.color1.set(color1);
		this.color2.set(color2);
	}
	Vector2 tmpV1 = new Vector2(Integer.MAX_VALUE,0);
	Vector2 tmpV2 = new Vector2();
	Vector2 last = new Vector2();
	int index =0 ;
	final Color color1 = new Color(1, 1, 1, 0.5f);
	final Color color2 = new Color(0, 1, 0, 0.5f);
	void renderPath(float delta){
		index = 0;
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glLineWidth(5);
		Engine.getShapeRenderer().begin(ShapeType.Line);
		for(float t = 0;t<this.getWidth();t+=6+(stateTime%1)*6,index++){
			if(index%2==0){
				Engine.getShapeRenderer().setColor(color1);
			}else{
				Engine.getShapeRenderer().setColor(color2);
			}
			
			path.valueAt(tmpV2, t/len);
			if(tmpV2.x>this.getWidth()||tmpV2.y>this.getHeight())break;
			localToStageCoordinates(tmpV2);
			if(tmpV1.x != Integer.MAX_VALUE){
				Engine.getShapeRenderer().line(tmpV1, tmpV2);
			}
			tmpV1.set(tmpV2);
		}
		localToStageCoordinates(last);
		Engine.getShapeRenderer().line(tmpV1, last);
		last.set(this.getWidth()+8, this.getHeight()+20);
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
		batch.draw(in.getKeyFrame(stateTime, true), getX(),getY());
		batch.draw(out.getKeyFrame(stateTime, true),this.getX()+this.getWidth(),this.getY()+this.getHeight());
	}
	public Rectangle getRect(){
		return this.rect;
	}
	public float getTransX(){
		return this.getX()+this.getWidth();
	}
	public float getTransY(){
		return this.getY()+this.getHeight();
	}
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	
}
