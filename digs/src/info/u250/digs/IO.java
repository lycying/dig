package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.digs.gdx_encrypt.Base64;

public class IO {
	//*******************************For SOUND**********************
	private static final String SOUND_HANDEL = Base64.encode("info.u250.dig._sound".getBytes());
	private static final String ON = Base64.encode("on".getBytes());
	private static final String OFF = Base64.encode("off".getBytes());
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
	private static final String COIN_HANDEL = Base64.encode("info.u250.dig._coin".getBytes());
	
	public static int getCoin(){
		return Engine.getPreferences().getInteger(COIN_HANDEL);
	}
	public static void saveCoin(int coin){
		Engine.getPreferences().putInteger(COIN_HANDEL, coin);
		Engine.getPreferences().flush();
	}
	//*******************************For COIN***********************
	
	
	//*******************************For Level**********************
	private static final String LEVEL_HANDEL = Base64.encode("info.u250.dig._level".getBytes());
	
	public static int getLevel(){
		return Engine.getPreferences().getInteger(LEVEL_HANDEL);
	}
	public static void saveLevel(int level){
		Engine.getPreferences().putInteger(LEVEL_HANDEL, level);
		Engine.getPreferences().flush();
	}
	//*******************************For Level**********************
}
