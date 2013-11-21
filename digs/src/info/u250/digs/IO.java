package info.u250.digs;

import info.u250.c2d.engine.Engine;

public class IO {
	//*******************************For SOUND**********************
	private static final String SOUND_HANDEL = "info.u250.dig._sound";
	private static final String ON = "on";
	private static final String OFF ="off";
	//off == false
	//other == true
	public static boolean soundOn(){
		return !OFF.equals(Engine.getPreferences().getString(SOUND_HANDEL));
	}
	public static void enableSound(){
		Engine.getPreferences().putString(SOUND_HANDEL, ON);
		Engine.getPreferences().flush();
		Engine.getSoundManager().setVolume(1);
		Engine.getMusicManager().setVolume(1);
	}
	public static void disableSound(){
		Engine.getPreferences().putString(SOUND_HANDEL, OFF);
		Engine.getPreferences().flush();
		Engine.getSoundManager().setVolume(0);
		Engine.getMusicManager().setVolume(0);
	}
	//*******************************For SOUND**********************
	
	
	//*******************************For COIN***********************
	private static final String COIN_HANDEL = "info.u250.dig._coin";
	
	public static int getCoin(){
		return Engine.getPreferences().getInteger(COIN_HANDEL);
	}
	public static void saveCoin(int coin){
		Engine.getPreferences().putInteger(COIN_HANDEL, coin);
		Engine.getPreferences().flush();
	}
	//*******************************For COIN***********************
	
	
	//*******************************For Level**********************
	private static final String PACK_SCROLL_HANDEL = "info.u250.dig._pack.scroll";
	
	public static int getPackScroll(){
		return Engine.getPreferences().getInteger(PACK_SCROLL_HANDEL);
	}
	public static void savePackScroll(int pack){
		Engine.getPreferences().putInteger(PACK_SCROLL_HANDEL, pack);
		Engine.getPreferences().flush();
	}
	
	private static final String PACK_HANDEL = "info.u250.dig._pack";
	
	public static int getPack(){
		int pack = Engine.getPreferences().getInteger(PACK_HANDEL);
		return pack;
	}
	public static void savePack(int pack){
		Engine.getPreferences().putInteger(PACK_HANDEL, pack);
		Engine.getPreferences().flush();
	}
	
	private static final String LEVEL_HANDEL = "info.u250.dig._level";
	public static int getLevel(){
		return Engine.getPreferences().getInteger(LEVEL_HANDEL);
	}
	public static void saveLevel(int level){
		Engine.getPreferences().putInteger(LEVEL_HANDEL, level);
		Engine.getPreferences().flush();
	}
	//*******************************For Level**********************
	public static void showLeaderboard(int pack,int idx){
		String handel = "leaderboard_pack"+pack+"_lvl"+idx;
		int pre_score = Engine.getPreferences().getInteger(handel);
		if(0!=pre_score){
			Digs.getGPSR().gpsSubmitScore(handel, pre_score);
		}
		Digs.getGPSR().gpsShowLeaderboard(handel);
	}
	public static void  win(String handel,int score,boolean desc){
		int pre_score = Engine.getPreferences().getInteger(handel);
		if(desc){
			if(pre_score<score){
				Engine.getPreferences().putInteger(handel, score);
				Engine.getPreferences().flush();
				Digs.getGPSR().gpsSubmitScore(handel, score);
			}
		}else{
			if(pre_score == 0 || pre_score>score){
				Engine.getPreferences().putInteger(handel, score);
				Engine.getPreferences().flush();
				Digs.getGPSR().gpsSubmitScore(handel, score);
			}
		}
	}
}
