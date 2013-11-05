package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.ui.HintOnScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class Tour6 extends LevelConfig {
	public Tour6(){
		this.surface = "texs/purpl192.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_SLATE_GRAY.get();
		this.topColor = WebColors.DARK_GREEN.get();
		this.lineHeight = 300;
		this.segment = 1;
		this.aim = 10;
		
		callback = new LevelCallBack() {
			@Override
			public void after(Level level) {
				{
					StepladderEntity ladder = new StepladderEntity(20, 200,50);
					level.addStepladder(ladder);
				}
				{
					StepladderEntity ladder = new StepladderEntity(12, 300,150);
					level.addStepladder(ladder);
				}
				{
					StepladderEntity ladder = new StepladderEntity(4, 400, 250);
					level.addStepladder(ladder);
				}
				
				KillCircleEntity kill = new KillCircleEntity(500, 200, 100, Color.WHITE);
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
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				
				{
					HintOnScreen hint = new HintOnScreen("Who am i...","level-item-bg-4",Color.BLACK,150);
					hint.pack();
					hint.setPosition(100, 300);
					level.addActor(hint);
				}
			}
		};
	}
}
