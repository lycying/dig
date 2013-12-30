package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.digs.GameHelper.GameHelperListener;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;

public class GooglePlayServiceResolverAndroidImpl implements GooglePlayServiceResolver,GameHelperListener {
	private GameHelper gameHelper;
	Activity activity;
	
//	boolean isAvia(){
//		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity) == ConnectionResult.SUCCESS;
//	}
	
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
		if(this.gpsIsLogin()){
			Intent shareIntent = new PlusShare.Builder(activity)
			.setType("image/png")
	        .setText(activity.getString(R.string.share_on_g_plus))
	        .setContentUrl(Uri.parse("http://goo.gl/ttVpxX"))
	        .setContentDeepLinkId("id",
	                    "Fool Dig",
	                    activity.getString(R.string.share_desc),
	                    Uri.parse(activity.getString(R.string.icon_url)))
	        .getIntent();
			activity.startActivityForResult(shareIntent, 0);
		}else{
			this.gpsIsLogin();
		}
	}
	
	@Override
	public void onSignInFailed() {}

	@Override
	public void onSignInSucceeded() {}

	@Override
	public void shareTour1Fail2Times() {
		if(this.gpsIsLogin()){
			Intent shareIntent = new PlusShare.Builder(activity)
			.setType("image/png")
	        .setText(activity.getString(R.string.share_momentTour1Fail2Times))
	        .setContentUrl(Uri.parse("http://goo.gl/ttVpxX"))
	        .setContentDeepLinkId("id",
	                    "Fool Dig",
	                    activity.getString(R.string.share_desc),
	                    Uri.parse(activity.getString(R.string.icon_url)))
	        .getIntent();
			activity.startActivityForResult(shareIntent, 0);
		}else{
			this.gpsIsLogin();
		}
	}

	@Override
	public void shareCompleteTheTour() {
		if(this.gpsIsLogin()){
			Intent shareIntent = new PlusShare.Builder(activity)
			.setType("image/png")
	        .setText(activity.getString(R.string.share_momentCompleteTheTour))
	        .setContentUrl(Uri.parse("http://goo.gl/ttVpxX"))
	        .setContentDeepLinkId("id",
	                    "Fool Dig",
	                    activity.getString(R.string.share_desc),
	                    Uri.parse(activity.getString(R.string.icon_url)))
	        .getIntent();
			activity.startActivityForResult(shareIntent, 0);
		}else{
			this.gpsIsLogin();
		}
	}
	@Override
	public void shareCompleteThePack1() {
		if(this.gpsIsLogin()){
			Intent shareIntent = new PlusShare.Builder(activity)
			.setType("image/png")
	        .setText(activity.getString(R.string.momentCompleteThePack1))
	        .setContentUrl(Uri.parse("http://goo.gl/ttVpxX"))
	        .setContentDeepLinkId("id",
	                    "Fool Dig",
	                    activity.getString(R.string.share_desc),
	                    Uri.parse(activity.getString(R.string.icon_url)))
	        .getIntent();
			activity.startActivityForResult(shareIntent, 0);
		}else{
			this.gpsIsLogin();
		}
	}

	@Override
	public void shareCompleteThePack2() {
		if(this.gpsIsLogin()){
			Intent shareIntent = new PlusShare.Builder(activity)
			.setType("image/png")
	        .setText(activity.getString(R.string.momentCompleteThePack2))
	        .setContentUrl(Uri.parse("http://goo.gl/ttVpxX"))
	        .setContentDeepLinkId("id",
	                    "Fool Dig",
	                    activity.getString(R.string.share_desc),
	                    Uri.parse(activity.getString(R.string.icon_url)))
	        .getIntent();
			activity.startActivityForResult(shareIntent, 0);
		}else{
			this.gpsIsLogin();
		}
	}


	@Override
	public void openHelperWebDialog() {
		final WebDialog dlg = new WebDialog(activity,"https://showcase-demos.googlecode.com/svn/trunk/fooldig-help/index.html"){
			@Override
			protected void initWebView(WebView webView) {
				WebSettings webSettings = webView.getSettings();
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
				mTitle.setText("Help");
			}
		};
		dlg.show();

	}

	@Override
	public void openUrl(String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		activity.startActivity(browserIntent);
	}

	
	

}
