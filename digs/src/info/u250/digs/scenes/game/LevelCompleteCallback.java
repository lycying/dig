package info.u250.digs.scenes.game;

import com.badlogic.gdx.scenes.scene2d.Actor;


public abstract class LevelCompleteCallback {
	public abstract Actor infoBorad(Level level);
	public abstract boolean isWin();
	public abstract boolean isFail();
	public abstract void failLevel(Level level);
	public abstract boolean tick(Level level);
}
