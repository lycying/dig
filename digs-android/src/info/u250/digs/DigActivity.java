package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.load.startup.StartupLoading;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import com.badlogic.gdx.graphics.Color;

public class DigActivity extends AndroidApplication {
	protected RelativeLayout layout;
	protected View loadingView;
	protected View gameView;


	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		layout = new RelativeLayout(this);
		loadingView = LayoutInflater.from(this).inflate(R.layout.loading,null);
		
		ApplicationListener game = new Digs(){
			@Override
			protected StartupLoading getStartupLoading() {
				return new StartupLoading() {
					boolean switchToGdx = false;
					@Override
					protected void inLoadingRender(float delta) {
						if(switchToGdx || percent()>0.05f){
							if(!switchToGdx){
								switchToGdx = true;
								DigActivity.this.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Animation fadeOut = new AlphaAnimation(1, 0);
										fadeOut.setInterpolator(new AccelerateInterpolator());
									    fadeOut.setDuration(200);
									    fadeOut.setAnimationListener(new AnimationListener() {
									        @Override
									        public void onAnimationEnd(Animation animation) {
									        	loadingView.setVisibility(View.GONE);
									        }

											@Override
											public void onAnimationRepeat(
													Animation animation) {
											}

											@Override
											public void onAnimationStart(
													Animation animation) {
											}
									    });
									    loadingView.startAnimation(fadeOut);
									}
								});
							}
							Gdx.gl.glClearColor(1, 1, 1, 1);
							Engine.getSpriteBatch().begin();
							Engine.getDefaultFont().setColor(Color.BLACK);
							Engine.getDefaultFont().draw(Engine.getSpriteBatch(), "Loading "+(int)(100*this.percent())+"%", 100, 200);
							Engine.getSpriteBatch().end();
						}
					}
					@Override
					protected void finishLoadingCleanup() {
						Engine.getDefaultFont().setColor(Color.WHITE);
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
		
		RelativeLayout.LayoutParams adParamsLoading = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
		adParamsLoading.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		adParamsLoading.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(loadingView,adParamsLoading);

		RelativeLayout.LayoutParams adParamsMain = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		// Hook it all up
		setContentView(layout, adParamsMain);
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
