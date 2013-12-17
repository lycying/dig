package info.u250.digs;

import com.badlogic.gdx.Gdx;

public class GooglePlayServiceResolverDesktop implements GooglePlayServiceResolver {
	private boolean login = false;
	@Override
	public void gpsLogin() {
		login = true;
		Gdx.app.log("GPS", "Do login");
	}

	@Override
	public void gpsLogout() {
		login = false;
		Gdx.app.log("GPS", "Do logout");
	}

	@Override
	public boolean gpsIsLogin() {
		return login;
	}

	@Override
	public void gpsSubmitScore(String id, int score) {
		Gdx.app.log("GPS", "Do submit score :" + id + "/" + score);
	}

	@Override
	public void gpsUnlockAchievement(String id) {
		Gdx.app.log("GPS", "Do unlock achievement :" + id );
	}

	@Override
	public void gpsShowAchievement() {
		Gdx.app.log("GPS", "Do show achievement" );
	}

	@Override
	public void gpsShowLeaderboard(String id) {
		Gdx.app.log("GPS", "Do show leaderboard :" + id );
	}

	@Override
	public void shareOnGPlusplus() {
		Gdx.app.log("GPS", "Do Open g+ dialog");
	}

	@Override
	public void shareTour1Fail2Times() {
		Gdx.app.log("GPS", "Do momentTour1Fail2Times");
	}

	@Override
	public void shareCompleteTheTour() {
		Gdx.app.log("GPS", "Do momentCompleteTheTour");
	}

	@Override
	public void shareCompleteThePack2() {
		Gdx.app.log("GPS", "Do momentCompleteThePack2");
	}


	@Override
	public void openHelperWebDialog() {
		
	}

	@Override
	public void shareCompleteThePack1() {
		Gdx.app.log("GPS", "Do momentCompleteThePack1");
	}

	@Override
	public void openUrl(String url) {
		Gdx.app.log("GPS", "Do openURL:"+url);
	}

}
