package info.u250.digs.scenes.start;

import info.u250.digs.Digs;
import info.u250.digs.scenes.StartUpScene;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.Level.FingerMode;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Finger extends Image{
	static int BASE_LINE = 0;
	StartUpScene start;
	public Finger(TextureRegion region,StartUpScene start){
		super(region);
		this.start = start;
		this.setY(BASE_LINE);
	}
	float timeDelta = 0;
	float timeDelta2 = 0;
	
	Vector2 speed = new Vector2(400,200);
	Vector2 direction = new Vector2(1,1).nor();
	float SPEED = 200;
	@Override
	public void act(float delta) {
		super.act(delta);
		timeDelta += delta;
		timeDelta2 += delta;
		
		
		if(timeDelta>0.2f){
			timeDelta = 0;
			direction.set(Digs.RND.nextFloat()*Digs.RND.nextFloat(),Digs.RND.nextFloat()).nor();
			start.water.getWater().splash(this.getX(), 20+100*Digs.RND.nextFloat());
		}
		
		if(getX()+30>start.level.getWidth()){
			speed.x = -SPEED*2 ;
		}
		if(getY()-BASE_LINE>170){
			speed.y = -SPEED ;
		}
		if(getX()+30<0){
			speed.x = SPEED *2;
		}
		if(getY()<BASE_LINE){
			speed.y = SPEED ;
		}
		
		
		
		if(timeDelta2>30f){
			timeDelta2 = 0;
			this.addAction(Actions.fadeIn(0.3f));
		}else if(timeDelta2>10){
			this.addAction(Actions.fadeOut(0.3f));
			return;
		}else if(timeDelta2>9.5f){
			start.level.fillTerrain(getX()+16,getY()-BASE_LINE+50, Level.RADIUS,FingerMode.Fill);
		}else{
			start.level.fillTerrain(getX()+16,getY()-BASE_LINE+50, Level.RADIUS,FingerMode.Clear);
		}
		this.moveBy(direction.x*speed.x*delta,direction.y*speed.y*delta);
	}
}
