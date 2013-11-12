package info.u250.digs;

import info.u250.c2d.engine.CoreProvider.TransitionType;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.digs.scenes.AboutScene;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.StartUpScene;

public class DigsEngineDrive implements EngineDrive {

//	static final String FLAT = "texs/RockLayered.jpg";
	static final String FLAT2 = "texs/S.png";
	static public final int LINGO_SOUND = 43;
	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{
				"data/",FLAT2}, 960, 540);
		opt.configFile = "info.u250.digs.cfg";
		opt.useGL20 = true;
		opt.autoResume = true;
		opt.catchBackKey = true;
		opt.debug = false;
		return opt;
	}

	StartUpScene startUpScene = null;
	LevelScene levelScene = null;
	AboutScene aboutScene = null;
	GameScene gameScene = null;
	
	
	@Override
	public void onLoadedResourcesCompleted() {
		startUpScene = new StartUpScene(this);
		gameScene = new GameScene(this);
		levelScene = new LevelScene(this);
		aboutScene = new AboutScene(this);
		
		Engine.setMainScene(startUpScene);
	}

	@Override
	public void dispose() {
		if(null != aboutScene)aboutScene.dispose();
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("All", "data/all.atlas");
		reg.font("Font", "data/fnt/foot.fnt");
		reg.font("BigFont", "data/fnt/big.fnt");
		reg.font("MenuFont", "data/fnt/menu.fnt");
		
		reg.particleEffect("Effect", "data/p.p");
		
//		reg.texture("Texture", FLAT);
		reg.texture("Texture2", FLAT2);
		
		reg.music("MusicBackground", "data/music/bg.ogg");
		reg.music("MusicBattle", "data/music/battle.ogg");
		reg.music("MusicCont", "data/music/cont.ogg");
		reg.music("MusicTimer", "data/music/timer.ogg");
		
		reg.sound("SoundChoosePack1", "data/sounds/choose-pack1.ogg");
		reg.sound("SoundChoosePack2", "data/sounds/choose-pack2.ogg");
		reg.sound("SoundChooseGuide", "data/sounds/choose-guide.ogg");
		reg.sound("SoundFunc", "data/sounds/func.ogg");
		reg.sound("SoundClick", "data/sounds/click.ogg");
		reg.sound("SoundMeet", "data/sounds/meetf.ogg");
		reg.sound("SoundBossXO", "data/sounds/boss-xo.ogg");
		reg.sound("SoundBossBreak", "data/sounds/boss-break.ogg");
		reg.sound("SoundDockKa", "data/sounds/ka_dock.ogg");
		reg.sound("SoundDig", "data/sounds/dig.ogg");
		reg.sound("SoundDie", "data/sounds/die.ogg");
		reg.sound("SoundHurt", "data/sounds/hurt.ogg");
		reg.sound("SoundTrans", "data/sounds/trans.ogg");
		reg.sound("SoundCoin", "data/sounds/coin.ogg");
		reg.sound("SoundShot", "data/sounds/shot.ogg");
		reg.sound("SoundWin", "data/sounds/win.ogg");
		reg.sound("SoundFail", "data/sounds/fail.ogg");
		
		reg.sound("SoundNewContrib", "data/sounds/newcon.ogg");
		
		reg.sound("SoundLvl5Bang", "data/sounds/lvl5-bang.ogg");
	
		for(int soundIdx=1;soundIdx<=LINGO_SOUND;soundIdx++){
			reg.sound("SoundEnv"+soundIdx, "data/sounds/env/lingo"+soundIdx+".ogg");
		}
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
	public void setToAboutScene(){
		Engine.setMainScene(aboutScene,TransitionType.Fade,200);
	}

	public GameScene getGameScene() {
		return gameScene;
	}

}
