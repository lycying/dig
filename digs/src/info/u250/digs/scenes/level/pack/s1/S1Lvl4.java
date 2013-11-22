package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.TeleportEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class S1Lvl4 extends LevelConfig {
	public S1Lvl4(){
		this.surface = "texs/multi035.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.GRAY.get();
		this.topColor = WebColors.BLACK.get();
		this.lineHeight = 360;
		this.segment = 10;
		this.gold = 200;
		this.npc = 30;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					KillCircleEntity kill = new KillCircleEntity(100, 100, 30, Color.CYAN);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(750, 100, 200, Color.MAGENTA);
					level.addKillCircle(kill);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				{
					TeleportEntity inout = new TeleportEntity(280,150,600,500);
					level.addInOutTrans(inout);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.WARRIOR_BY_ANIMATEDARCTICSTUDIO_D69O6JL();
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(200, 100);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.PEOPLEXXXXX();
					polygon.setScale(1f, 1f);
					polygon.setPosition(700, 50);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.WHITE);
					Polygon polygon =  PolygonTable.WONDER_PART_6();
					polygon.setScale(2f, 1f);
					polygon.setPosition(500, 450);
					drawPolygon(polygon, gold);
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