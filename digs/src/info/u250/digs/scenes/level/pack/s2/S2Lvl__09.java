package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LineLevelConfig;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.ui.HintOnScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class S2Lvl__09 extends LineLevelConfig {
	public S2Lvl__09(){
		this.lineHeight = 360+2048-540;
		this.surface = "208";
		this.width = (int)Engine.getWidth()-SCROLL_WIDTH ;
		this.height = 2048;
		this.bottomColor = WebColors.ROYAL_BLUE.get();
		this.topColor = WebColors.BLACK.get();
		
		this.gold = 1000;
		this.npc = 1;
		this.enemy = 10;
		this.ka = 10;
		this.time = 6000;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					StepladderEntity e = new StepladderEntity(110, 800, 0);
					level.addStepladder(e);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				for(int i=0;i<enemy;i++){
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(560+Digs.RND.nextFloat()*80, 1125);
					level.addEnemyMiya(e);
				}
				for(int i=0;i<enemy;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(560+Digs.RND.nextFloat()*80, 825);
					level.addKa(e);
				}
				{
					Boss boss = new Boss();
					boss.init(level);
					boss.setPosition(600, 400);
					level.addBoss(boss);
				}
				{
					HintOnScreen hint = new HintOnScreen( Engine.getLanguagesManager().getString("java.s2_09.hint1"), "hint1", Color.WHITE, 400);
					hint.pack();
					hint.setPosition(200, 1400);
					level.addActor(hint);
				}
				{
					HintOnScreen hint = new HintOnScreen(Engine.getLanguagesManager().getString("java.s2_09.hint2"), "hint1", Color.WHITE, 300);
					hint.setPosition(200, 1100);
					level.addActor(hint);
				}
				{
					HintOnScreen hint = new HintOnScreen(Engine.getLanguagesManager().getString("java.s2_09.hint3"), "hint1", Color.WHITE, 300);
					hint.setPosition(200, 800);
					level.addActor(hint);
				}
				{
					HintOnScreen hint = new HintOnScreen(Engine.getLanguagesManager().getString("java.s2_09.hint4"), "dialog-bg", Color.RED, 300);
					hint.setPosition(200, 500);
					level.addActor(hint);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(Color.CLEAR);
					fillRect(terr, 550, 800, 100, 50);
					fillRect(terr, 550, 1100, 100, 50);
				}
				{
					gold.setColor(Color.YELLOW);
					fillCircle(gold, 400, 200, 200);
				}
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
