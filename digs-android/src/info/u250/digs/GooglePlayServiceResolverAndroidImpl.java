package info.u250.digs;

import info.u250.digs.GameHelper.GameHelperListener;
import android.app.Activity;

public class GooglePlayServiceResolverAndroidImpl implements GooglePlayServiceResolver,GameHelperListener {
	private GameHelper gameHelper;
	private Activity activity;
	public GooglePlayServiceResolverAndroidImpl(final Activity activity){
		this.activity = activity;
		
		gameHelper = new GameHelper(activity);
		gameHelper.enableDebugLog(true, "GPGS");
		gameHelper.setup(this);
	}

	@Override
	public void gpsLogin() {
		gameHelper.beginUserInitiatedSignIn();
	}

	@Override
	public void gpsLogout() {
		gameHelper.signOut();
	}

	@Override
	public boolean gpsIsLogin() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void gpsSubmitScore(String id, int score) {
		if(gpsIsLogin()){
			gameHelper.getGamesClient().submitScore(id, score);
		}
	}

	@Override
	public void gpsUnlockAchievement(String id) {
		if(gpsIsLogin()){
			gameHelper.getGamesClient().unlockAchievement(id);
		}
	}

	@Override
	public void gpsShowAchievement() {
		if(gpsIsLogin()){
			activity.startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);
		}else{
			gpsLogin();
		}
	}

	@Override
	public void gpsShowLeaderboard(String id) {
		if(gpsIsLogin()){
			activity.startActivityForResult(gameHelper.getGamesClient().getLeaderboardIntent(id), 100);
		}else{
			gpsLogin();
		}
	}

	
	
	public GameHelper getGameHelper() {
		return gameHelper;
	}

	@Override
	public void onSignInFailed() {
		//TODO
	}

	@Override
	public void onSignInSucceeded() {
		//TODO
	}

}
