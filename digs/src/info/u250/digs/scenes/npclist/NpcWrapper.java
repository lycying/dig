package info.u250.digs.scenes.npclist;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import info.u250.digs.scenes.game.entity.BaseEntity;

public class NpcWrapper {
	public NpcWrapper(BaseEntity e){
		this.e = e;
		this.e.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				NpcWrapper.this.e.setNextAnimationForShow();
				super.clicked(event, x, y);
			}
		});
	}
	public BaseEntity e;
	public String title;
	public String desc;
	
	public String troopIcon ;
	
	public boolean lock = true;
}
