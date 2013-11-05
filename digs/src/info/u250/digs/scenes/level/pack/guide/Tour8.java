package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class Tour8 extends LevelConfig {
	public Tour8(){
		this.surface = "texs/aiji.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_CYAN.get();
		this.topColor = WebColors.DARK_BLUE.get();
		this.lineHeight = 300;
		this.segment = 1;
		
		
		callback = new LevelCallBack() {
			@Override
			public void after(Level level) {
				for(int i=0;i<10;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
				for(int i=0;i<10;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(380 + Digs.RND.nextFloat()*100, 70);
					level.addKa(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.CLEAR);
				Polygon polygon =  PolygonTable.IMG_ISLAND1;
				polygon.setScale(3f,1f);
				polygon.setPosition(350, 50);
				drawPolygon(polygon, terr);
			}

			@Override
			public void before(Level level) {
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setPosition(0,lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}
