package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class Tour7 extends LevelConfig {
	public Tour7(){
		this.surface = "texs/baobao2.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight()+400;
		this.bottomColor = WebColors.LIME_GREEN.get();
		this.topColor = WebColors.BLACK.get();
		this.lineHeight = 300+400;
		this.segment = 1;
		
		
		callback = new LevelCallBack() {
			@Override
			public void after(Level level) {
				{
					StepladderEntity ladder = new StepladderEntity(40, 200,50);
					level.addStepladder(ladder);
				}
				
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
