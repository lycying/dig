package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.dialog.WinDialog;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;

public class DefaultLevelCompleteCallback extends LevelCompleteCallback {
	/* if we arrived the win status */
	int goldNumber = 0;
	boolean win = false;
	boolean fail = false;
	@Override
	public boolean tick(GameScene gameScene, Level level,LevelConfig config) {
		if(win || fail)return true;
		
		goldNumber = 0;
		for(GoldTowerEntity dock:level.getDocks()){
			goldNumber+=dock.getNumber();
		}
		if(goldNumber>=config.aim){
			WinDialog winDialog = new WinDialog(gameScene);
			gameScene.addActor(winDialog);
			winDialog.show(config, goldNumber, 50, 30, 56);
			Engine.getMusicManager().stopMusic("MusicBattle");
			Engine.getSoundManager().playSound("SoundWin");
			Engine.doPause();
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
