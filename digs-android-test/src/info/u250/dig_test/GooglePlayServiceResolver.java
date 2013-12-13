package info.u250.dig_test;

public interface GooglePlayServiceResolver {
	void gpsLogin();
	void gpsLogout();
	boolean gpsIsLogin();
	void gpsSubmitScore(String id,int score);
	void gpsUnlockAchievement(String id);
	void gpsShowAchievement();
	void gpsShowLeaderboard(String id);
	void shareOnGPlusplus();
	
	void momentTour1Fail2Times();//fail tour1
	void momentCompleteTheTour();//share on g+
	void momentCompleteThePack2();//pack2
	
	void momentLvlInfo(final int pack,final int level);//share the info on g+ of every level
}
