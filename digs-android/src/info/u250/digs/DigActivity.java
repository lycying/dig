package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;

public class DigActivity extends AndroidApplication {

	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		ApplicationListener game = new Digs();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = true;
		config.resolutionStrategy = new RatioResolutionStrategy(Engine.getWidth(), Engine.getHeight());
		initialize(game, config);
	}
}
