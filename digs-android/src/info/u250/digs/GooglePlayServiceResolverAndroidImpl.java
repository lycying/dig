package info.u250.digs;

import info.u250.digs.GameHelper.GameHelperListener;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;

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
	public void shareOnGPlusplus() {
		Intent shareIntent = new PlusShare.Builder(activity)
        .setType("text/plain")
        .setText("I found a very good game for android, come to download it.")
        .setContentUrl(Uri.parse("http://goo.gl/XZOMHz"))
        .getIntent();
		activity.startActivityForResult(shareIntent, 0);
	}
	
	@Override
	public void onSignInFailed() {
		//TODO
	}

	@Override
	public void onSignInSucceeded() {
		//TODO
	}

	@Override
	public void momentTour1Fail2Times() {
		if(this.gpsIsLogin()){
			ItemScope target = new ItemScope.Builder()
		    .setId("myuniqueidforthissong")
		    .setName("When Johnny Comes Marching Home")
		    .setDescription("A song about missing one's family members fighting in the American Civil War")
		    .setImage("http://example.com/images/albumThumb.png")
		    .setType("http://schema.org/MusicRecording")
		    .build();

			Moment moment = new Moment.Builder()
		    .setType("http://schemas.google.com/ListenActivity")
		    .setTarget(target)
		    .build();
			gameHelper.getPlusClient().writeMoment(moment);
		}
	}

	@Override
	public void momentCompleteTheTour() {
		if(this.gpsIsLogin()){
			ItemScope target = new ItemScope.Builder()
		    .setId("myuniqueidforthissong")
		    .setName("When Johnny Comes Marching Home")
		    .setDescription("A song about missing one's family members fighting in the American Civil War")
		    .setImage("http://example.com/images/albumThumb.png")
		    .setType("http://schema.org/MusicRecording")
		    .build();

			Moment moment = new Moment.Builder()
		    .setType("http://schemas.google.com/ListenActivity")
		    .setTarget(target)
		    .build();
			gameHelper.getPlusClient().writeMoment(moment);
		}
	}

	@Override
	public void momentCompleteThePack2() {
		if(this.gpsIsLogin()){
			ItemScope target = new ItemScope.Builder()
		    .setId("myuniqueidforthissong")
		    .setName("When Johnny Comes Marching Home")
		    .setDescription("A song about missing one's family members fighting in the American Civil War")
		    .setImage("http://example.com/images/albumThumb.png")
		    .setType("http://schema.org/MusicRecording")
		    .build();

			Moment moment = new Moment.Builder()
		    .setType("http://schemas.google.com/ListenActivity")
		    .setTarget(target)
		    .build();
			gameHelper.getPlusClient().writeMoment(moment);
		}
	}

	@Override
	public void momentLvlInfo(final int pack,final int level) {
		if(this.gpsIsLogin()){
			ItemScope target = new ItemScope.Builder()
		    .setId("myuniqueidforthissong")
		    .setName("When Johnny Comes Marching Home")
		    .setDescription("A song about missing one's family members fighting in the American Civil War")
		    .setImage("http://example.com/images/albumThumb.png")
		    .setType("http://schema.org/MusicRecording")
		    .build();

			Moment moment = new Moment.Builder()
		    .setType("http://schemas.google.com/ListenActivity")
		    .setTarget(target)
		    .build();
			gameHelper.getPlusClient().writeMoment(moment);
		}
	}

	

}
