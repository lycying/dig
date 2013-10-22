package info.u250.digs.scenes.level;

import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.game.LevelConfig;

public class LevelIdx {
	public static LevelConfig getLevelConfig(int level){
		LevelConfig config = null;
		switch(level){
		case 1:
			config = new Level1_tour1();
			break;
		case 2:
			config = new Level2_tour2();
			break;
		case 3:
			config = new Level3_tour3();
			break;
		case 4:
			config = new Level4_tour4();
			break;
		case 5:
			config = new Level5_tour5();
			break;
		case 6:
			config = new Level6_tour6();
			break;
		default:
			config = new LevelConfig();
			config.bottomColor = WebColors.BLACK.get();
			config.topColor = WebColors.CADET_BLUE.get();
			config.lineHeight = 380;
			break;
		}
		return config;
	}
}
