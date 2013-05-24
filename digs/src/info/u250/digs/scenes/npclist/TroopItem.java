package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.NpcListScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class TroopItem extends Group {
//	Path<Vector2> path;
//	Sprite dot;
//	float t;
//	float speed = 1.3f;
//	final Vector2 tmpV = new Vector2();
	
	final NpcListScene container;
	int number = 0;
	final Label lblNumber ;
	
	public TroopItem(final NpcListScene npcListScene){
		this.container = npcListScene;
		int w = 85;
		int h = 85;
		this.setSize(w,h);
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		this.addActor(new Image(atlas.findRegion("troop-bg")));
		lblNumber = new Label("0",new LabelStyle(font,Color.BLACK));
		Image lock = new Image(atlas.findRegion("troop-lock"));
		this.addActor(lblNumber);
		lock.setPosition(35, 10);
		this.addActor(lock);
//		dot = new Sprite(atlas.findRegion("color"));
//		
//		Vector2 cp[] = new Vector2[]{
//				new Vector2(0, 0), new Vector2(w * 0.25f, h * 0.5f), new Vector2(0, h), new Vector2(w*0.5f, h*0.75f),
//				new Vector2(w, h), new Vector2(w * 0.75f, h * 0.5f), new Vector2(w, 0), new Vector2(w*0.5f, h*0.25f)
//			};
//		path = new BSpline<Vector2>(cp, 3, true);
		
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				container.addActor(container.troopNumberSetter);
				container.troopNumberSetter.show(TroopItem.this);
				super.clicked(event, x, y);
			}
		});
	}
//	@Override
//	public void draw(SpriteBatch batch, float parentAlpha) {
//		super.draw(batch, parentAlpha);
//		t += speed * Gdx.graphics.getDeltaTime();
//		while (t >= 1f) {
//			t -= 1f;
//		}
//		path.valueAt(tmpV, t);
//		dot.setPosition(getX()+tmpV.x, getY()+tmpV.y);
//		dot.draw(batch);
//	}
	
	public void setTroopNumber(int number){
		this.number = number;
		this.lblNumber.setText(number+"");
	}
	public int getTroopNumber(){
		return this.number;
	}
}
