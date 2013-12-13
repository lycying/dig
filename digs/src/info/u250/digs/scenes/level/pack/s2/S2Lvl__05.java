package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

//carry as many of gold in center time
public class S2Lvl__05 extends FaceLevelConfig {
	public S2Lvl__05(){
		this.faces = new Vector2[41];
		for(int i=0;i<faces.length;i++){
			faces[i] = new Vector2();
			faces[i].x = 24*i;
			int dx = i%4;
			if(0==dx){
				faces[i].y = 370;
			}else if(1==dx){
				faces[i].y = 365;
			}else if(2==dx){
				faces[i].y = 375;
			}else if(3==dx){
				faces[i].y = 360;
			}
		}
		this.lightLine = 360;
		this.lineWidth = 5;
		this.surface = "204";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_KHAKI.get();
		this.topColor = WebColors.BLACK.get();
		
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
				{
					KillCircleEntity e = new KillCircleEntity(420, 200, 20, Color.RED);
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(400, 120, 40, Color.RED);
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(390, 0, 60, Color.RED);
					level.addKillCircle(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(Color.CLEAR);
					final Polygon polygon = PolygonTable.A11949912622133772893CHESSPIECES();
					polygon.setPosition(400, 100);
					polygon.setScale(0.4f, 0.4f);
					drawPolygon(polygon, terr);
					gold.setColor(Color.YELLOW);
					polygon.setPosition(300, 100);
					polygon.setScale(0.2f, 0.2f);
					drawPolygon(polygon, gold);
				}
				{
					final Polygon polygon = PolygonTable.A11949912452083159395CHESSPIECE();
					gold.setColor(Color.CYAN);
					polygon.setPosition(600, 100);
					polygon.setScale(0.6f, 0.6f);
					drawPolygon(polygon, gold);
					polygon.setPosition(100, 100);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					fillRect(gold, 870, 100, 100, 100);
					drawPixmapDeco(gold, "stone4", -50, 0);
					drawPixmapDeco(gold, "stone3", 870, 150);
				}
			}

			@Override
			public void before(Level level) {
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"snow");
				p.setColor(Color.RED);
				p.setPosition(0, Engine.getHeight());
				level.addActor(p);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lightLine);
				level.addGoldDock(dock);
			}
		};
	}
}