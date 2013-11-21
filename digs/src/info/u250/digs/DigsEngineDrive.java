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

	static final String FLAT = "texs/S.png";
	
	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{
				"data/",FLAT}, 960, 540);
		opt.configFile = "info.u250.digs.cfg";
		opt.useGL20 = true;
		opt.autoResume = true;
		opt.catchBackKey = true;
		opt.debug = false;
		return opt;
	}

	//the startup scene 
	StartUpScene startUpScene = null;
	//the level scene to choose level
	LevelScene levelScene = null;
	//the about scene to show who make this game
	AboutScene aboutScene = null;
	//the really game scene 
	GameScene gameScene = null;
	
	
	@Override
	public void onLoadedResourcesCompleted() {
		startUpScene = new StartUpScene(this);
		gameScene = new GameScene(this);
		levelScene = new LevelScene(this);
		aboutScene = new AboutScene(this);
		
		Engine.setMainScene(startUpScene);
		
		Digs.delayPlayActorSound();
	}

	@Override
	public void dispose() {
		if(null != aboutScene)aboutScene.dispose();
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("All", "data/all.atlas");
		
		//fonts, Font is the smallest 
		reg.font("Font", "data/fnt/foot.fnt");
		reg.font("BigFont", "data/fnt/big.fnt");
		reg.font("MenuFont", "data/fnt/menu.fnt");
		
		//the particle Effect
		reg.particleEffect("Effect", "data/all.pp");
		
		//the front layer used to good look
		reg.texture("Texture2", FLAT);
		
		reg.music("MusicBackground", "data/music/bg.ogg");
		reg.music("MusicBattle", "data/music/battle.ogg");
		reg.music("MusicCont", "data/music/cont.ogg");
		
		//when a level has time limit,the last 10 second will play this sound until zero
		reg.music("MusicTimer", "data/music/timer.ogg");
		
		//used for level pack choose
		reg.sound("SoundChoosePack1", "data/sounds/choose-pack1.ogg");
		reg.sound("SoundChoosePack2", "data/sounds/choose-pack2.ogg");
		reg.sound("SoundChooseGuide", "data/sounds/choose-guide.ogg");
		
		//sound for click the function pane , they have the same sound effect
		reg.sound("SoundFunc", "data/sounds/func.ogg");
		//the common click sound
		reg.sound("SoundClick", "data/sounds/click.ogg");
		//the sound when meet a dog friend
		reg.sound("SoundMeet", "data/sounds/meetf.ogg");
		//sound for ka land to home
		reg.sound("SoundDockKa", "data/sounds/ka_dock.ogg");
		
		//the boss left and right his flag
		reg.sound("SoundBossXO", "data/sounds/boss-xo.ogg");
		//the boss break the ground
		reg.sound("SoundBossBreak", "data/sounds/boss-break.ogg");
		
		//dig sound
		reg.sound("SoundDig", "data/sounds/dig.ogg");
		//common die sound , pilipapa
		reg.sound("SoundDie", "data/sounds/die.ogg");
		//die when contact the gas
		reg.sound("SoundHurt", "data/sounds/hurt.ogg");
		//move on transfer sound
		reg.sound("SoundTrans", "data/sounds/trans.ogg");
		//put the coin on the platform
		reg.sound("SoundCoin", "data/sounds/coin.ogg");
		//enemy shot shot shot sound
		reg.sound("SoundShot", "data/sounds/shot.ogg");
		
		//the win sound
		reg.sound("SoundWin", "data/sounds/win.ogg");
		//fail sound
		reg.sound("SoundFail", "data/sounds/fail.ogg");
		//start a new level 
		reg.sound("SoundNew", "data/sounds/new.ogg");
		//about screen , type text sound
		reg.sound("SoundNewContrib", "data/sounds/newcon.ogg");
		//pack0.lvl5 used
		reg.sound("SoundLvl5Bang", "data/sounds/lvl5-bang.ogg");
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
