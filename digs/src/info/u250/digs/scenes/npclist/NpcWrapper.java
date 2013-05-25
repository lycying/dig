package info.u250.digs.scenes.npclist;

import info.u250.digs.scenes.game.entity.BaseEntity;

public class NpcWrapper {
	public NpcWrapper(BaseEntity e){
		this.e = e;
	}
	public BaseEntity e;
	public String title;
	public String desc;
	
	public String troopIcon ;
	
	public boolean lock = true;
}
