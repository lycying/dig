package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.NpcListScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;


public class TroopItem extends Group {
	
	final NpcListScene container;
	int number = 0;
	final Label lblNumber ;
	final Image lock;
	final Image icon;
	final NpcWrapper eWrapper;
	
	ParticleEffectActor p ;
	
	public TroopItem(final NpcWrapper eWrapper,final NpcListScene npcListScene){
		this.eWrapper = eWrapper;
		ParticleEffect e = new ParticleEffect();
		e.load(Gdx.files.internal("data/e.p"), Gdx.files.internal("data/"));
		p = new ParticleEffectActor(e);
		p.setX(30);
		p.setY(30);
		this.container = npcListScene;
		int w = 85;
		int h = 85;
		this.setSize(w,h);
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		Image backgroundImage = new Image(atlas.findRegion("troop-bg"));
//		backgroundImage.addAction(Actions.forever(Actions.sequence(
//				Actions.rotateBy(360,0.5f),
//				Actions.rotateBy(-360,1f)
//				)));
		this.addActor(backgroundImage);
		lblNumber = new Label("0",new LabelStyle(font,Color.BLACK));
		lblNumber.setAlignment(Align.center);
		lblNumber.setWidth(w);
		lblNumber.getStyle().background = new NinePatchDrawable(atlas.createPatch("ui-label-bg"));
		
		lock = new Image(atlas.findRegion("troop-lock"));
		lock.setPosition(35, 10);
		
		if(null!= eWrapper){
			icon = new Image(atlas.findRegion(eWrapper.troopIcon));
		}else{
			icon = new Image();
		}
		icon.setSize(w, h);
		
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				container.troopSetter.show(TroopItem.this);
//				setTroopNumber(++number);
//				container.countTroopsCost();
				container.troopSetterContainer.addActor(container.troopSetter);
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}
		});
		
		reShow(eWrapper);
	}
	public void reShow(final NpcWrapper eWrapper){
		lock.remove();
		icon.remove();
		lblNumber.remove();
		//lblNumber.setColor(new Color(0,125f/255f,110f/255f,0.8f));
		if(null == eWrapper || eWrapper.lock){
			this.addActor(lock);
			this.setTouchable(Touchable.disabled);
		}else{
			this.addActor(p);
			this.addActor(icon);
			this.addActor(lblNumber);
			this.setTouchable(Touchable.enabled);
		}
	}
	
	public void setTroopNumber(int number){
		this.number = number;
		this.lblNumber.setText(number+"");
		this.container.countTroopsCost();
	}
	public int getTroopNumber(){
		return this.number;
	}
	public int getTroopCost(){
		if(null == eWrapper) return 0;
		return number * eWrapper.e.cost;
	}
}
