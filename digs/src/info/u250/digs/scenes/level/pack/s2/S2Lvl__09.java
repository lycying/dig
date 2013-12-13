package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class S2Lvl__09 extends FaceLevelConfig {
	public S2Lvl__09(){
		this.faces = new Vector2[41];
		for(int i=0;i<faces.length;i++){
			faces[i] = new Vector2();
			faces[i].x = 24*i;
			int dx = i%4;
			if(0==dx){
				faces[i].y = 370+2048-540;
			}else if(1==dx){
				faces[i].y = 365+2048-540;
			}else if(2==dx){
				faces[i].y = 375+2048-540;
			}else if(3==dx){
				faces[i].y = 360+2048-540;
			}
		}
		this.lightLine = 360+2048-540;
		this.lineWidth = 10;
		this.surface = "208";
		this.width = (int)Engine.getWidth() ;
		this.height = 2048;
		this.bottomColor = WebColors.ROYAL_BLUE.get();
		this.topColor = WebColors.BLACK.get();
		
		this.gold = 100;
		this.npc = 1;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				
			}

			@Override
			public void before(Level level) {
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lightLine);
				level.addGoldDock(dock);
			}
		};
	}
}