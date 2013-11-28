package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.game.entity.TeleportEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class S1Lvl6 extends LevelConfig {
	public S1Lvl6(){
		this.surface = "qvg/105.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = 1024;
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.INDIGO.get();
		this.lineHeight = 360+1024-540;
		this.segment = 30;
		this.gold = 200;
		this.npc = 15;
		this.ka = 5;
		this.ascent = 30;
		this.enemy = 8;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/yh.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				{
					StepladderEntity ladder = new StepladderEntity(30, 200,150);
					level.addStepladder(ladder);
				}
				{
					StepladderEntity ladder = new StepladderEntity(20, 400,150);
					level.addStepladder(ladder);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				for(int i=0;i<ka;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(700+Digs.RND.nextFloat()*200, 560);
					level.addKa(e);
				}
				for(int i=0;i<enemy;i++){
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(800+Digs.RND.nextFloat()*50, 180);
					level.addEnemyMiya(e);
				}
				{
					TeleportEntity inout = new TeleportEntity(350, 40, 750, 540);
					level.addInOutTrans(inout);
				}
		
				Boss boss = new Boss();
				boss.setBossLandHeight(300);
				boss.init(level);
				boss.setPosition(800, 400);
				level.addBoss(boss);
				
				
				{
					KillCircleEntity e = new KillCircleEntity(20, 580, 60, WebColors.INDIGO.get());
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(-20, 400, 100, WebColors.INDIGO.get());
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(260, 260, 50, WebColors.INDIGO.get());
					level.addKillCircle(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(WebColors.INDIGO.get());
					fillRect(terr, 680, 530, 240, 80);
					terr.setColor(Color.CLEAR);
					fillRect(terr, 700, 550, 200, 40);
					terr.setColor(WebColors.INDIGO.get());
					fillRect(terr, 780, 130, 240, 90);
					terr.setColor(Color.CLEAR);
					fillRect(terr, 800, 150, 200, 50);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.IMG_ISLAND2();
					polygon.setPosition(60, 30);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.ROO_ONLY_GOLD();
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(600, 150);
					drawPolygon(polygon, gold);
				}
				{
					drawPixmapDeco(gold, "stone5", -20, 00,0.7f);
					drawPixmapDeco(gold, "stone5", -20, 100,0.6f);
					drawPixmapDeco(gold, "stone5", -20, 200,0.5f);
					drawPixmapDeco(gold, "stone5", -20, 280,0.4f);
					drawPixmapDeco(gold, "stone2", 80, -30);
					
					drawPixmapDeco(gold, "stone5", 900, 00,0.7f);
					drawPixmapDeco(gold, "stone5", 900, 100,0.6f);
					drawPixmapDeco(gold, "stone5", 900, 200,0.5f);
					drawPixmapDeco(gold, "stone5", 900, 280,0.4f);
					drawPixmapDeco(gold, "stone2", 800, -30);
					drawPixmapDeco(gold, "deco1", 850, 50,1f);
					drawPixmapDeco(gold, "deco1", 120, 0,1f);
					drawPixmapDeco(gold, "deco1", 90, 0,1f);
				}
				
			}
			@Override
			public void before(Level level) {
				{
					Image bgImage = new Image(bgTexture);
					bgImage.setSize(512, 512);
					bgImage.setPosition(150, 80+1024-540);
					bgImage.setOrigin(bgImage.getWidth()/2, bgImage.getHeight()/2);
					bgImage.addAction(Actions.forever(Actions.rotateBy(180,20)));
					level.addActor(bgImage);
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