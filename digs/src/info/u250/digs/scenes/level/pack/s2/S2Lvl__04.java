package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.TeleportEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//carry as many of gold in center time
public class S2Lvl__04 extends FaceLevelConfig {
	public S2Lvl__04(){
		this.faces = new Vector2[]{
				new Vector2(0,400),
				new Vector2(100,380),
				new Vector2(200,360),
				new Vector2(300,340),
				new Vector2(400,300),
				new Vector2(500,200),
				new Vector2(600,300),
				new Vector2(700,340),
				new Vector2(800,360),
				new Vector2(900,380),
				new Vector2(960,380),
		};
		this.lightLine = 360;
		this.surface = "203";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.SEASHELL.get();
		this.topColor = WebColors.INDIGO.get();
		
		this.gold = 100;
		this.npc = 20;
		this.ka = 20;
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
				for(int i=0;i<ka;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(900, Engine.getHeight() + Digs.RND.nextFloat()*900);
					level.addKa(e);
				}
				{
					TeleportEntity e = new TeleportEntity(30, 30, 500, 300,Color.ORANGE,Color.WHITE);
					level.addInOutTrans(e);
				}
				{
					TeleportEntity e = new TeleportEntity( 500, 200,130, 30,Color.ORANGE,Color.WHITE);
					level.addInOutTrans(e);
				}
				{
					TeleportEntity e = new TeleportEntity( 150, 200,400, 200,Color.BLUE,Color.WHITE);
					level.addInOutTrans(e);
				}
				{
					TeleportEntity e = new TeleportEntity( 900, 200,550, 200,Color.BLUE,Color.WHITE);
					level.addInOutTrans(e);
				}
				{
					TeleportEntity e = new TeleportEntity( 250, 300,400, 300,Color.MAGENTA,Color.WHITE);
					level.addInOutTrans(e);
				}
				{
					TeleportEntity e = new TeleportEntity( 800, 300,550, 300,Color.MAGENTA,Color.WHITE);
					level.addInOutTrans(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
				gold.setColor(Color.YELLOW);
				fillRect(gold, 500, 60, 100, 60);
				}
			
			}

			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(0,1000), new Vector2(300,350)));
				
				level.addActor(pbg);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lightLine);
				level.addGoldDock(dock);
			}
		};
	}
}