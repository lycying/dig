package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;


public class Digs extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new DigsEngineDrive();
	}

	
}
