package info.u250.digs;

public interface GooglePlayServiceResolver {
	void gpsLogin();
	void gpsLogout();
	boolean gpsIsLogin();
	void gpsSubmitScore(String id,int score);
	void gpsUnlockAchievement(String id);
	void gpsShowAchievement();
	void gpsShowLeaderboard(String id);
	
	void shareOnGPlusplus();
	void shareTour1Fail2Times();//fail tour1
	void shareCompleteTheTour();//share on g+
	void shareCompleteThePack1();//pack1
	void shareCompleteThePack2();//pack2
		
	void openHelperWebDialog();
	void openUrl(String url);
}
