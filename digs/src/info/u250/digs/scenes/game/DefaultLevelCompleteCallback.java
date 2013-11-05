package info.u250.digs.scenes.game;

import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;

public class DefaultLevelCompleteCallback extends LevelCompleteCallback {
	/* if we arrived the win status */
	int goldNumber = 0;
	boolean win = false;
	@Override
	public boolean tick(GameScene gameScene, Level level,LevelConfig config) {
		if(win)return true;
		
		goldNumber = 0;
		for(GoldTowerEntity dock:level.getDocks()){
			goldNumber+=dock.getNumber();
		}
		if(goldNumber>=config.aim){
			gameScene.win(config, goldNumber, 50, 30, 56);//TODO
			win = true;
			return true;
		}
		
		return false;
	}

}
