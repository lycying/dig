package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.HintOnScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;
/*
 * You can find 10 ka in this tour
 */
public class Tour7 extends HookLevelConfig {
	public Tour7(){
		this.surface = "qvg/006.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_CYAN.get();
		this.topColor = WebColors.DARK_BLUE.get();
		this.lineHeight = 300;
		this.segment = 1;
		this.gold = 0;
		this.ka = 10;
		this.time = 3*60;
		this.npc = 10;
		
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
				for(int i=0;i<ka;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(380 + Digs.RND.nextFloat()*100, 70);
					level.addKa(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.CLEAR);
				Polygon polygon =  PolygonTable.IMG_ISLAND1();
				polygon.setScale(3f,1f);
				polygon.setPosition(350, 50);
				drawPolygon(polygon, terr);
				drawPixmapDeco(terr, "tree3", 720, lineHeight-2,0.6f);
				drawPixmapDeco(terr, "tree3", 800, lineHeight-2);
				drawPixmapDeco(terr, "tree3", 860, lineHeight-2,0.4f);
			}

			@Override
			public void before(Level level) {
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setPosition(0,lineHeight);
				level.addGoldDock(dock);
				
				{
					HintOnScreen hint = new HintOnScreen("seem to have met before","hint2",Color.WHITE,200);
					hint.pack();
					hint.setPosition(400, 70);
					level.addActor(hint);
					}
			}
		};
	}
}
