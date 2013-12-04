package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.callback.DefaultLevelCompleteCallback;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public abstract class LevelConfig {
	//the scroll with that is the space 
	public static final int SCROLL_WIDTH = 30;
	//for level information auto make
	public int pack = 0;
	public int idx  = 0;
	public String idxName  = "";
	//auto information auto make end
	
	public int gold = -1;
	public int ka = -1;
	public int npc = -1;
	public int enemy = -1;
	//the second : time up !!!
	public int time = -1; 
	public String surface = "qvg/000.png";
	public Color bottomColor = null;
	public Color topColor = null;
	
	public int width = -1;
	public int height = -1;
	
	public LevelCompleteCallback levelCompleteCallback = new DefaultLevelCompleteCallback();
	public LevelMakeCallBack levelMakeCallback = new LevelMakeCallBack() {
		Random random = new Random();
		@Override
		public void after(Level level) {
			for(int i=0;i<1;i++){
				Boss e = new Boss();
				e.setBossLandHeight(50);
				e.init(level);
				e.setPosition(500+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
				level.addBoss(e);
			}
			for(int i=0;i<20;i++){
				Npc e = new Npc();
				e.init(level);
				e.setPosition(400+random.nextFloat()*50, Engine.getHeight() + random.nextFloat()*300);
				level.addNpc(e);
			}
			
		}
		
		@Override
		public void mapMaker(Pixmap terr, Pixmap gold) {
		}

		@Override
		public void before(Level level) {
			GoldTowerEntity dock = new GoldTowerEntity();
			dock.setY(200);
			level.addGoldDock(dock);
		}
	};
}
