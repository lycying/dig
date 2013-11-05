package info.u250.digs.scenes.game;

import info.u250.digs.scenes.GameScene;

public abstract class LevelCompleteCallback {
	public abstract boolean tick(GameScene gameScene,Level level,LevelConfig config);
}
