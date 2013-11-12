package info.u250.digs.scenes.game;


public abstract class LevelCompleteCallback {
	public abstract boolean isWin();
	public abstract boolean isFail();
	public abstract void failLevel(Level level);
	public abstract boolean tick(Level level);
}
