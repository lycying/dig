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

public class S1Lvl1 extends LevelConfig {
	public S1Lvl1(){
		this.surface = "texs/solitary-moss.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.GRAY.get();
		this.topColor = WebColors.BLACK.get();
		this.lineHeight = 360;
		this.segment = 10;
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
				Polygon polygon =  PolygonTable.FROG;
				polygon.setScale(0.4f, 0.4f);
				polygon.setPosition(400, 100);
				drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.BABELFISH;
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(800, 200);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.IMG_ISLAND8;
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(600, 150);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(WebColors.DARK_SALMON.get());
					Polygon polygon =  PolygonTable.CLIFFA_2;
					polygon.setScale(2f, 2f);
					polygon.setPosition(0, 0);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(WebColors.DARK_GRAY.get());
					Polygon polygon =  PolygonTable.WONDER_PART_2;
					polygon.setScale(3f, 1f);
					polygon.setPosition(0, 0);
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