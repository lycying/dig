package info.u250.digs;

import info.u250.c2d.engine.CoreProvider.TransitionType;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.StartUpScene;

public class DigsEngineDrive implements EngineDrive {

	static final String FLAT = "texs/RockLayered.jpg";
	static final String FLAT2 = "texs/S.png";
	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{"data/",FLAT,FLAT2}, 960, 540);
		opt.configFile = "info.u250.digs.cfg";
		opt.useGL20 = true;
		opt.autoResume = true;
		opt.catchBackKey = true;
		opt.debug = true;
		return opt;
	}

	StartUpScene startUpScene = null;
	LevelScene levelScene = null;
	GameScene gameScene = null;
	
	
	@Override
	public void onLoadedResourcesCompleted() {
		startUpScene = new StartUpScene(this);
		gameScene = new GameScene(this);
		levelScene = new LevelScene(this);
		
		Engine.setMainScene(startUpScene);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("All", "data/all.atlas");
		reg.textureAtlas("Texs", "data/texs.atlas");
		reg.font("Font", "data/fnt/foot.fnt");
		reg.font("MenuFont", "data/fnt/menu.fnt");
		reg.particleEffect("Effect", "data/p.p");
		reg.texture("Texture", FLAT);
		reg.texture("Texture2", FLAT2);
		
		reg.music("MusicBackground", "data/music/bg.ogg");
		reg.music("MusicBattle", "data/music/battle.ogg");
		reg.sound("SoundClick", "data/sounds/click.ogg");
		reg.sound("SoundDie", "data/sounds/die.ogg");
		reg.sound("SoundHurt", "data/sounds/hurt.ogg");
		reg.sound("SoundTrans", "data/sounds/trans.ogg");
		reg.sound("SoundCoin", "data/sounds/coin.ogg");
		reg.sound("SoundWin", "data/sounds/win.ogg");
		
		reg.sound("SoundLvl5Bang", "data/sounds/lvl5-bang.ogg");
		
		//sound for NPC say
		reg.sound("SoundEnv1", "data/sounds/env/wolf-hit-3.ogg");
		reg.sound("SoundEnv2", "data/sounds/env/megaSplat001.ogg");
		reg.sound("SoundEnv3", "data/sounds/env/r8_1002.ogg");
		reg.sound("SoundEnv4", "data/sounds/env/ready.ogg");
		reg.sound("SoundEnv5", "data/sounds/env/rock_pickup3.ogg");
		reg.sound("SoundEnv6", "data/sounds/env/rsbang.ogg");
		reg.sound("SoundEnv7", "data/sounds/env/death_30.ogg");
		reg.sound("SoundEnv8", "data/sounds/env/oneup.ogg");
		reg.sound("SoundEnv9", "data/sounds/env/sad_01.ogg");
		reg.sound("SoundEnv10", "data/sounds/env/scream_01.ogg");
		reg.sound("SoundEnv11", "data/sounds/env/stouch.ogg");
		reg.sound("SoundEnv12", "data/sounds/env/tutor_talk_aha_01.ogg");
		reg.sound("SoundEnv13", "data/sounds/env/vo_bomb_6.ogg");
		reg.sound("SoundEnv14", "data/sounds/env/whoa.ogg");
		reg.sound("SoundEnv15", "data/sounds/env/monster_mumble_resultscreen_happy_01.ogg");
		reg.sound("SoundEnv16", "data/sounds/env/monster_mumble_tutorial_02.ogg");
		reg.sound("SoundEnv17", "data/sounds/env/vo_combo_13.ogg");
		reg.sound("SoundEnv18", "data/sounds/env/klocki_normal_01.wav");
		reg.sound("SoundEnv19", "data/sounds/env/kulka_wybuchowaRemote_02.wav");
		reg.sound("SoundEnv20", "data/sounds/env/kulki_push_02.wav");
	}

	public void setToStartUpScene(){
		Engine.setMainScene(startUpScene,TransitionType.Fade,200);
	}
	public void setToGameScene(){
		Engine.setMainScene(gameScene,TransitionType.Fade,200);
	}
	public void setToLevelScene(){
		Engine.setMainScene(levelScene,TransitionType.Fade,200);
	}

	public GameScene getGameScene() {
		return gameScene;
	}

}
