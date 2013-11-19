package info.u250.digs;

public interface GooglePlayServiceResolver {
	void gpsLogin();
	void gpsLogout();
	boolean gpsIsLogin();
	void gpsSubmitScore(String id,int score);
	void gpsUnlockAchievement(String id);
	void gpsShowAchievement();
	void gpsShowLeaderboard(String id);
}
