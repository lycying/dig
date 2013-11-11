package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.dialog.FailDailog;
import info.u250.digs.scenes.game.dialog.WinDialog;
import info.u250.digs.scenes.game.entity.AbstractMoveable;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;

import com.badlogic.gdx.scenes.scene2d.Actor;
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
				for(final Actor actor:level.getChildren()){
					if(actor instanceof AbstractMoveable){
						actor.addAction(Actions.delay(delay*count++,Actions.run(new Runnable() {
							@Override
							public void run() {
								AbstractMoveable.class.cast(actor).die();
							}
						})));
					}
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
