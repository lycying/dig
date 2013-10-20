package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldDock;
import info.u250.digs.scenes.game.entity.InOutTrans;
import info.u250.digs.scenes.game.entity.KillCircle;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Polygon;

public class Level4_tour4 extends LevelConfig {
	public Level4_tour4(){
		this.surface = "texs/green058.jpg";
		this.width = (int)Engine.getWidth();
		this.height = 512;
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.KHAKI.get();
		this.lineHeight = 300;
		this.segment = 20;
		
		
		callback = new LevelCallBack() {
			@Override
			public void call(Level level) {
				GoldDock dock = new GoldDock();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				{
				KillCircle ray = new KillCircle(400, 150, 100,Color.WHITE);
				level.addKillCircle(ray);
				}
				
				{
					KillCircle ray = new KillCircle(300, 200, 150,Color.PINK);
					level.addKillCircle(ray);
					}
				{
					KillCircle ray = new KillCircle(350, 180, 50,Color.BLUE);
					level.addKillCircle(ray);
					}
				
				for(int i=0;i<5;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
				
				InOutTrans inout = new InOutTrans(150,250,700,500);
				level.addInOutTrans(inout);
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.WONDER_PART_8;
				polygon.setScale(1f, 1f);
				polygon.setPosition(650, 400);
				drawPolygon(polygon, gold);
			}
		};
	}
}
