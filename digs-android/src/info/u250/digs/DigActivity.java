package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.load.startup.StartupLoading;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;

public class DigActivity extends AndroidApplication {
	protected RelativeLayout layout;
	protected LoadingProgressAndroid loadingView;
	protected View gameView;


	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		layout = new RelativeLayout(this);
		loadingView = new LoadingProgressAndroid(this);
		
		ApplicationListener game = new Digs(){
			@Override
			protected StartupLoading getStartupLoading() {
				return new StartupLoading() {
					@Override
					protected void inLoadingRender(float delta) {
						DigActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadingView.percent(percent());
							}
						});
					}
					
					@Override
					protected void finishLoadingCleanup() {
						DigActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadingView.hideLoading();
							}
						});
					}
				};
			}
		};
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = true;
//		config.resolutionStrategy = new FixedResolutionStrategy((int)Engine.getWidth(), (int)Engine.getHeight());
		config.resolutionStrategy = new RatioResolutionStrategy(Engine.getWidth(), Engine.getHeight());
		gameView = initializeForView(game, config);
		
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		layout.addView(gameView, adParams);

		RelativeLayout.LayoutParams adParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		// Hook it all up
		setContentView(layout, adParams2);
		loadingView.showLoading();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				Gdx.input.getInputProcessor().keyDown(Keys.BACK);
			} catch (Exception ex) {
			}
			return true;
		}
		return false;
	}
}
