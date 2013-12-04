package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.HintOnScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;
/*
 * show that the enemy will kill you , but it can not be killed!
 */
public class Tour8 extends HookLevelConfig {
	public Tour8(){
		this.surface = "qvg/007.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.LIGHT_CORAL.get();
		this.topColor = WebColors.AQUA.get();
		this.lineHeight = 300;
		this.segment = 1;
		this.gold = 10;
		this.time = 3*60;
		this.npc = 10;
		this.enemy = 5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
				for(int i=0;i<enemy;i++){
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(430 + Digs.RND.nextFloat()*100, 100);
					level.addEnemyMiya(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.CLEAR);
					Polygon polygon =  PolygonTable.IMG_ISLAND2();
					polygon.setScale(3f,1f);
					polygon.setPosition(350, 50);
					drawPolygon(polygon, terr);
				}
				
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.CAKE_7();
					polygon.setScale(0.5f,0.5f);
					polygon.setPosition(700, 100);
					drawPolygon(polygon, gold);
					}
				drawPixmapDeco(terr, "tree4", 800, lineHeight-2);
				
			}

			@Override
			public void before(Level level) {
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setPosition(0,lineHeight);
				level.addGoldDock(dock);
				
				{
					HintOnScreen hint = new HintOnScreen("never try to kill a enemy , you cann't","hint2",Color.WHITE,120);
					hint.pack();
					hint.setPosition(400, 60);
					level.addActor(hint);
					}
			}
		};
	}
}
