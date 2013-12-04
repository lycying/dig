package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.game.entity.TeleportEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V16 extends HookLevelConfig {
	public Pack1V16(){
		this.surface = "qvg/115.jpg";
		this.width = (int)Engine.getWidth() -SCROLL_WIDTH;
		this.height = 1024;
		this.bottomColor = WebColors.FOREST_GREEN.get();
		this.topColor = WebColors.DARK_SEA_GREEN.get();
		this.lineHeight = 260+1024-540;
		this.ascent = 15;
		this.segment = 40;
		this.gold = 150;
		this.npc = 15;
		this.enemy = 5;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/ship8.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				{
					StepladderEntity e = new StepladderEntity(10, 500, 550);
					level.addStepladder(e);
				}
				{
					StepladderEntity e = new StepladderEntity(5, 500, 360);
					level.addStepladder(e);
				}
				{
					StepladderEntity e = new StepladderEntity(15, 800, 160);
					level.addStepladder(e);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(310+Digs.RND.nextFloat()*200, 520);
					level.addNpc(e);
				}
				
				{
					Boss boss = new Boss();
					boss.setBossLandHeight(300);
					boss.init(level);
					boss.setPosition(860, 350);
					level.addBoss(boss);
					for(int i=0;i<enemy;i++){
						EnemyMiya e = new EnemyMiya();
						e.init(level);
						e.setPosition(300+Digs.RND.nextFloat()*400, 320);
						level.addEnemyMiya(e);
					}
				}
				{
					KillCircleEntity e = new KillCircleEntity(680, 900, 30, Color.RED);
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(570, 680, 60, Color.GREEN);
					level.addKillCircle(e);
				}
				{
					TeleportEntity e = new TeleportEntity(300, 500, 700, 500);
					level.addInOutTrans(e);
				}
				{
					TeleportEntity e = new TeleportEntity(200, 300, 800, 300,Color.WHITE,Color.BLUE);
					level.addInOutTrans(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(Color.CLEAR);
					fillRect(terr, 200, 300, 660, 40);
					fillRect(terr, 300, 500, 460, 40);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.TOAST();
					polygon.setScale(4f, 2f);
					polygon.setPosition(10, 30);
					drawPolygon(polygon, gold);
				}

				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.COVERSCENE8();
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(50, 330);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.WHITE);
					Polygon polygon =  PolygonTable.A11949861321737044813PEAR_01();
					polygon.setScale(1, 1);
					polygon.setPosition(520, 630);
					drawPolygon(polygon, gold);
				}
				
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.A1194991237523065600CHESSPIECED();
					polygon.setScale(0.3f, 0.3f);
					polygon.setPosition(680, 930);
					drawPolygon(polygon, gold);
				}
				
				{
					drawPixmapDeco(gold, "stone5", 720, 520);
				}
				
			}
			@Override
			public void before(Level level) {
				{
					TextureAtlas atlas = Engine.resource("All");
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
					pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,1024-540+350)));
					Image shipImage2 = new Image(bgTexture);
					shipImage2.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(-10,2),Actions.rotateTo(10,2))));
					pbg.addActor(new ParallaxLayer(pbg, shipImage2, new Vector2(2.5f,1), new Vector2(1000,20000), new Vector2(900,1024-540+260)));
			
					level.addActor(pbg);
				}
				{
					GoldTowerEntity dock = new GoldTowerEntity();
					dock.setY(lineHeight);
					level.addGoldDock(dock);
				}
				{
					GoldTowerEntity dock = new GoldTowerEntity();
					dock.setPosition(width-dock.getWidth(), lineHeight);
					level.addGoldDock(dock);
				}
				
			}
		};
	}
}