package info.u250.digs.scenes.level.pack.guide;

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
import info.u250.digs.scenes.ui.HintOnScreen;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Polygon;

public class Tour4 extends LevelConfig {
	public Tour4(){
		this.surface = "texs/green058.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.KHAKI.get();
		this.lineHeight = 300;
		this.segment = 20;
		this.aim = 5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					HintOnScreen hint = new HintOnScreen("If you must wear your tech, try not to look like an idiot ,follow me.","hint3",Color.BLACK,250);
					hint.pack();
					hint.setPosition(130, 150);
					hint.setColor(new Color(1,1,1,0.6f));
					level.addActor(hint);
				}
				{
				KillCircleEntity ray = new KillCircleEntity(400, 150, 100,Color.BLUE);
				level.addKillCircle(ray);
				}
				
				{
					KillCircleEntity ray = new KillCircleEntity(300, 200, 150,Color.WHITE);
					level.addKillCircle(ray);
					}
				{
					KillCircleEntity ray = new KillCircleEntity(350, 180, 50,Color.PINK);
					level.addKillCircle(ray);
					}
				
				for(int i=0;i<5;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
				
				TeleportEntity inout = new TeleportEntity(150,250,700,500);
				level.addInOutTrans(inout);
				
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"smoke");
				p.setPosition(450, 250);
				level.addActor(p);
				
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.BLACK_CAT;
				polygon.setScale(0.3f, 0.3f);
				polygon.setPosition(750, 400);
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
