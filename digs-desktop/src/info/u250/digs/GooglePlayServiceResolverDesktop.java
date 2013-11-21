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
	public void momentTour1Fail2Times() {
		Gdx.app.log("GPS", "Do momentTour1Fail2Times");
	}

	@Override
	public void momentCompleteTheTour() {
		Gdx.app.log("GPS", "Do momentCompleteTheTour");
	}

	@Override
	public void momentCompleteThePack2() {
		Gdx.app.log("GPS", "Do momentCompleteThePack2");
	}

	@Override
	public void momentLvlInfo(int pack, int level) {
		Gdx.app.log("GPS", "Do momentLvlInfo");
	}

}
