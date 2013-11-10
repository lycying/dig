package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.dialog.FailDailog;
import info.u250.digs.scenes.game.dialog.WinDialog;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DefaultLevelCompleteCallback extends LevelCompleteCallback {
	/* if we arrived the win status */
	int count = 0;
	boolean win = false;
	boolean fail = false;
	@Override
	public boolean tick(GameScene gameScene, Level level,LevelConfig config) {
		if(null == gameScene)return false;
		if(null == level)return false;
		
		if(win || fail)return true;
		count = level.getNpcs().size;
		if(count <= 0){
			Engine.getSoundManager().playSound("SoundFail");
			FailDailog failDialog = new FailDailog(gameScene);
			failDialog.show();
			gameScene.addActor(failDialog);
			fail = true;
			
			count = 0;
			count+=level.getNpcs().size;
			count+=level.getKas().size;
			count+=level.getBosses().size;
			count+=level.getEnemyMyiyas().size;
			if(count>0){
				float delay = 0.8f/count;
				count=0;
				for(final Npc e:level.getNpcs()){
					e.addAction(Actions.delay(delay*count++,Actions.run(new Runnable() {
						@Override
						public void run() {
							e.die();
							e.remove();
						}
					})));
				}
				for(final EnemyMiya e:level.getEnemyMyiyas()){
					e.addAction(Actions.delay(delay*count++,Actions.run(new Runnable() {
						@Override
						public void run() {
							e.die();
							e.remove();
						}
					})));
				}
				for(final Boss e:level.getBosses()){
					e.addAction(Actions.delay(delay*count++,Actions.run(new Runnable() {
						@Override
						public void run() {
							e.die();
							e.remove();
						}
					})));
				}
				for(final Ka e:level.getKas()){
					e.addAction(Actions.delay(delay*count++,Actions.run(new Runnable() {
						@Override
						public void run() {
							e.die();
							e.remove();
						}
					})));
				}
				
			}
			return true;
		}
		
		
		count = 0;
		for(GoldTowerEntity dock:level.getDocks()){
			count+=dock.getNumber();
		}
		if(count>=config.aim){
			WinDialog winDialog = new WinDialog(gameScene);
			gameScene.addActor(winDialog);
			winDialog.show(config, count, 50, 30, 56);
			Engine.getMusicManager().stopMusic("MusicBattle");
			Engine.getSoundManager().playSound("SoundWin");
			win = true;
			return true;
		}
		return false;
	}
	@Override
	public boolean isWin() {
		return win;
	}
	@Override
	public boolean isFail() {
		return fail;
	}

}
