package info.u250.digs.scenes.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonBinary;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineActor extends Actor{
	float time;
	SkeletonRenderer renderer;

	SkeletonData skeletonData;
	Skeleton skeleton;
	Animation animation;
	Array<Event> events = new Array<Event>();
	public SpineActor(final String name,TextureAtlas atlas,String defaultAnimation,final float scale){
		renderer = new SkeletonRenderer();
		SkeletonBinary binary = new SkeletonBinary(atlas);
		 binary.setScale(scale);
		skeletonData = binary.readSkeletonData(Gdx.files.internal(name + ".skel"));
		
		animation = skeletonData.findAnimation(defaultAnimation);

		skeleton = new Skeleton(skeletonData);
		if (name.equals("goblins")) skeleton.setSkin("goblin");
		skeleton.setToSetupPose();
		skeleton = new Skeleton(skeleton);
		skeleton.updateWorldTransform();
	}
	@Override
	public void act(float delta) {
		skeleton.setX(this.getX());
		skeleton.setY(this.getY());
		float lastTime = time;
		time += delta;
		events.clear();
		animation.apply(skeleton, lastTime, time, true, events);
		if (events.size > 0) System.out.println(events);
		skeleton.updateWorldTransform();
		skeleton.update(Gdx.graphics.getDeltaTime());
		super.act(delta);
	}
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		this.skeleton.getColor().set(color);
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		renderer.draw(batch, skeleton);
	}
}
