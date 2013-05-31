package info.u250.digs.scenes.game.entity.ai;

import info.u250.digs.scenes.game.entity.BaseEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AiHeal extends Image {
	Vector2 target_direction = new Vector2();
	private BaseEntity target;
		
	float speed = 200;
	public AiHeal(TextureRegion bombTextureRegion,BaseEntity bomber,BaseEntity target){
		super(bombTextureRegion);
		this.target = target;
		this.setPosition(bomber.getX()+bomber.getWidth()/2, bomber.getY()+bomber.getHeight()/2);
		this.setColor(Color.GREEN);
		this.setSize(4, 4);
	}
	
	@Override
	public void act(float delta) {
		target_direction.set(target.getX()+target.getDrawable().getWidth()/2,target.getY()+target.getDrawable().getHeight()/2).sub(this.getX(),this.getY()).nor();
		translate(delta*speed*target_direction.x, delta*speed*target_direction.y);
		if(target.getDrawable().getBoundingRectangle().contains(getX(), getY())){
			this.remove();
		}
		super.act(delta);
	}
}
