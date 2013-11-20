package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class S1Lvl2 extends LevelConfig {
	public S1Lvl2(){
		this.surface = "vg/s1v2.svg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.ALICE_BLUE.get();
		this.lineHeight = 250;
		this.segment = 2;
		this.gold = 200;
		
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				KillCircleEntity ray = new KillCircleEntity(540, 360, 150,Color.RED);
				level.addKillCircle(ray);
				
				for(int i=0;i<5;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(400, 400);
					level.addNpc(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.WONDER_PART_8;
				polygon.setScale(0.4f, 0.4f);
				polygon.setPosition(50, 50);
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