package info.u250.digs;

import java.util.Random;

import com.badlogic.gdx.utils.async.AsyncExecutor;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;


public class Digs extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new DigsEngineDrive();
	}

	@Override
	public void dispose() {
		executor.dispose();
		super.dispose();
	}
	static AsyncExecutor executor = new AsyncExecutor(1);
	public static AsyncExecutor getExecutor() {
		return executor;
	}
	public static final Random RND = new Random();
}
