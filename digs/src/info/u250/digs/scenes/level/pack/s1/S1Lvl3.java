package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class S1Lvl3 extends LevelConfig {
	public S1Lvl3(){
		this.surface = "texs/DSRT.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.GRAY.get();
		this.topColor = WebColors.BLACK.get();
		this.lineHeight = 360;
		this.segment = 10;
		this.gold = 0;
		this.npc = 2;
		this.ka = 30;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					KillCircleEntity kill = new KillCircleEntity(100, 100, 30, Color.CYAN);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(750, 100, 35, Color.MAGENTA);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(220, 100, 40, Color.GREEN);
					level.addKillCircle(kill);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				for(int i=0;i<ka/2;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(180, 160);
					level.addKa(e);
				}
				for(int i=0;i<ka/2;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(720, 160);
					level.addKa(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(Color.CLEAR);
					Polygon polygon =  PolygonTable.SPRITZ_128;
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(150, 150);
					drawPolygon(polygon, terr);
				}
				{
					terr.setColor(Color.CLEAR);
					Polygon polygon =  PolygonTable.SQUIDGE_128;
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(700, 150);
					drawPolygon(polygon, terr);
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