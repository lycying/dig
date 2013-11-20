package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class S1Lvl4 extends LevelConfig {
	public S1Lvl4(){
		this.surface = "vg/s1v4.svg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_SLATE_GRAY.get();
		this.topColor = WebColors.DARK_GREEN.get();
		this.lineHeight = 300;
		this.segment = 1;
		this.gold = 200;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				
				
				for(int i=0;i<10;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight()+ 400 + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.DIALOG_OK;
				polygon.setScale(1f,1f);
				polygon.setPosition(750, 200);
				drawPolygon(polygon, gold);
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
