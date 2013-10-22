package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldDock;
import info.u250.digs.scenes.game.entity.KillCircle;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.Stepladder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

class Level6_tour6 extends LevelConfig {
	public Level6_tour6(){
		this.surface = "texs/purpl192.jpg";
		this.width = (int)Engine.getWidth();
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_SLATE_GRAY.get();
		this.topColor = WebColors.DARK_GREEN.get();
		this.lineHeight = 300;
		this.segment = 1;
		
		
		callback = new LevelCallBack() {
			@Override
			public void after(Level level) {
				{
					Stepladder ladder = new Stepladder(20, 200,50);
					level.addStepladder(ladder);
				}
				{
					Stepladder ladder = new Stepladder(12, 300,150);
					level.addStepladder(ladder);
				}
				
				KillCircle kill = new KillCircle(500, 200, 100, Color.WHITE);
				level.addKillCircle(kill);
				
				for(int i=0;i<10;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.DIALOG_OK;
				polygon.setScale(1f,1f);
				polygon.setPosition(750, 100);
				drawPolygon(polygon, gold);
			}

			@Override
			public void before(Level level) {
				GoldDock dock = new GoldDock();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}
