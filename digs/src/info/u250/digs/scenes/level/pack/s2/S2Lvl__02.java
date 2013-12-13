package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LineLevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

//carry as many of gold in center time
public class S2Lvl__02 extends LineLevelConfig {
	public S2Lvl__02(){
		this.lineHeight = 360;
		this.surface = "201";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.WHITE.get();
		this.topColor = WebColors.BLACK.get();
		this.gold = 100;
		this.npc = 20;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			
			@Override
			public void after(Level level) {
				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
				gold.setColor(Color.YELLOW);
				fillCircle(gold, 0, 0, 200);
				fillCircle(gold, width, 0, 200);
				gold.setColor(Color.CYAN);
				fillCircle(gold, 0, 0, 100);
				fillCircle(gold, width, 0, 100);
				fillRect(gold, 700, 0, 50, lineHeight-50);
				fillRect(gold, 500, 0, 30, lineHeight-50);
				}
			
			}

			@Override
			public void before(Level level) {
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}