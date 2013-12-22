package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//carry as many of gold in center time
public class S2Lvl__01 extends FaceLevelConfig {
	public S2Lvl__01(){
		this.faces = new Vector2[]{
				new Vector2(0,400),
				new Vector2(50,380),
				new Vector2(100,360),
				new Vector2(200,320),
				new Vector2(350,300),
				new Vector2(500,230),
				new Vector2(660,200),
				new Vector2(710,260),
				new Vector2(780,210),
				new Vector2(880,430),
				new Vector2(960,430)
		};
		this.lightLine = 300;
		this.surface = "200";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_TURQUOISE.get();
		this.topColor = WebColors.DARK_BLUE.get();
		
		this.gold = 100;
		this.npc = 50;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					StepladderEntity e = new StepladderEntity(10, 530, 100);
					level.addStepladder(e);
				}
				{
					StepladderEntity e = new StepladderEntity(20, 630, 100);
					level.addStepladder(e);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200, height + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
				{
					Boss boss = new Boss();
					boss.setPosition(900, 440);
					boss.setBossLandHeight(380);
					boss.init(level);
					level.addBoss(boss);
				}
				{
					Boss boss = new Boss();
					boss.setPosition(940, 440);
					boss.setBossLandHeight(380);
					boss.init(level);
					level.addBoss(boss);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.STONE();
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(300, 10);
					drawPolygon(polygon, gold);
					}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.STONE();
					polygon.setScale(0.2f, 0.2f);
					polygon.setPosition(800, 10);
					drawPolygon(polygon, gold);
					}
			}

			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(0,1000), new Vector2(300,350)));
				level.addActor(pbg);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(380);
				level.addGoldDock(dock);
			}
		};
	}
}