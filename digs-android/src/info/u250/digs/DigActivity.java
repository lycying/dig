package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.load.startup.StartupLoading;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;

public class DigActivity extends AndroidApplication implements GooglePlayServiceResolver{
	protected RelativeLayout layout;
	protected View loadingView;
	protected View gameView;
	protected AdmobAdControlImpl admob;
	
	GooglePlayServiceResolverAndroidImpl googlePlayServiceResolverAndroidImpl;

	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		layout = new RelativeLayout(this);
		loadingView = LayoutInflater.from(this).inflate(R.layout.loading,null);
		admob = new AdmobAdControlImpl(this);
		ApplicationListener game = new Digs(this,admob){
			@Override
			protected StartupLoading getStartupLoading() {
				return new StartupLoading() {
					Runnable feedback = new Runnable() {
						boolean showBar = false;
						@Override
						public void run() {
							if(!showBar && percent()>0){
								 loadingView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
								 showBar = true;
							} 
							TextView text = (TextView) loadingView.findViewById(R.id.loading_text);
							text.setText((int)(100*percent())+"%");
							((ProgressBar)loadingView.findViewById(R.id.u250_bar)).setProgress((int)(100*percent()));
						}
					};
					@Override
					protected void inLoadingRender(float delta) {
						DigActivity.this.runOnUiThread(feedback);
					}
					@Override
					protected void finishLoadingCleanup() {
						DigActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Animation fadeOut = new AlphaAnimation(1, 0);
								fadeOut.setInterpolator(new AccelerateInterpolator());
								fadeOut.setStartOffset(1000);
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
				};
			}
		};
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
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
		
		googlePlayServiceResolverAndroidImpl = new GooglePlayServiceResolverAndroidImpl(this);
		if(!googlePlayServiceResolverAndroidImpl.getGameHelper().getGamesClient().isConnected()){
			googlePlayServiceResolverAndroidImpl.getGameHelper().getGamesClient().connect();
		}
		admob.attach();
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

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		googlePlayServiceResolverAndroidImpl.getGameHelper().onActivityResult(request, response, data);
	}
	protected String getStringResourceByName(String aString) {
		try{
			String packageName = getPackageName();
			int resId = getResources().getIdentifier(aString, "string", packageName);
			return getString(resId);
		}catch(Exception ex){
			ex.printStackTrace();
			return "aaa";
		}
	}
	
	@Override
	public void gpsLogin() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.gpsIsLogin();
			}
		});
	}

	@Override
	public void gpsLogout() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.gpsLogout();
			}
		});
	}

	@Override
	public boolean gpsIsLogin() {
		return googlePlayServiceResolverAndroidImpl.gpsIsLogin();
	}

	@Override
	public void gpsSubmitScore(final String id, final int score) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.gpsSubmitScore(getStringResourceByName(id), score);
			}
		});
	}

	@Override
	public void gpsUnlockAchievement(final String id) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.gpsUnlockAchievement(getStringResourceByName(id));
			}
		});
	}

	@Override
	public void gpsShowAchievement() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.gpsShowAchievement();
			}
		});
	}

	@Override
	public void gpsShowLeaderboard(final String id) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.gpsShowLeaderboard(getStringResourceByName(id));
			}
		});
	}

	@Override
	public void shareOnGPlusplus() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.shareOnGPlusplus();
			}
		});
	}

	@Override
	public void shareTour1Fail2Times() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.shareTour1Fail2Times();
			}
		});
	}

	@Override
	public void shareCompleteTheTour() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.shareCompleteTheTour();
			}
		});
	}
	@Override
	public void shareCompleteThePack1() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.shareCompleteThePack1();
			}
		});
	}

	@Override
	public void shareCompleteThePack2() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.shareCompleteThePack2();
			}
		});
	}

	@Override
	public void openHelperWebDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.openHelperWebDialog();
			}
		});
	}

	public void openUrl(final String url) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				googlePlayServiceResolverAndroidImpl.openUrl(url);
			}
		});
	}


	
}
