package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.StartUpScene;

public class DigsEngineDrive implements EngineDrive {

	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{"data/"}, 960, 540);
		opt.useGL20 = true;
		opt.autoResume = true;
		opt.catchBackKey = true;
		opt.debug = true;
		return opt;
	}

	StartUpScene startUpScene = null;
	GameScene gameScene = null;
	@Override
	public void onLoadedResourcesCompleted() {
		startUpScene = new StartUpScene();
		//gameScene = new GameScene();
		
		Engine.setMainScene(startUpScene);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("All", "data/all.atlas");
		reg.font("Font", "data/fnt/foot.fnt");
		
		reg.music("MusicBackground", "data/music/bg.mp3");
		reg.music("MusicBattle", "data/music/battle.mp3");
		reg.sound("SoundClick", "data/sounds/click.ogg");
	}

}
