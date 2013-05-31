package info.u250.digs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.NpcListScene;
import info.u250.digs.scenes.StartUpScene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class DigsEngineDrive implements EngineDrive {

	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{"data/","texs/Stone.jpg"}, 960, 540);
		opt.useGL20 = true;
		opt.autoResume = true;
		opt.catchBackKey = true;
		opt.debug = true;
		return opt;
	}

	StartUpScene startUpScene = null;
	NpcListScene npcListScene = null;
	GameScene gameScene = null;
	
	
	@Override
	public void onLoadedResourcesCompleted() {
		startUpScene = new StartUpScene(this);
		npcListScene = new NpcListScene(this);
		gameScene = new GameScene(this);
		
		Engine.setMainScene(startUpScene);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("All", "data/all.atlas");
		reg.font("Font", "data/fnt/foot.fnt");
		reg.texture("Texture", "texs/Stone.jpg");
		
		reg.music("MusicBackground", "data/music/bg.mp3");
		reg.music("MusicBattle", "data/music/battle.mp3");
		reg.sound("SoundClick", "data/sounds/click.ogg");
		
		TextureAtlas atlas = Engine.resource("All");
		Array<AtlasRegion> appendErArray = new Array<TextureAtlas.AtlasRegion>();
		for(AtlasRegion region:atlas.getRegions()){
			if(region.name.contains("right")){
				AtlasRegion newItem = new AtlasRegion(region);
				newItem.name = region.name.replace("right", "left");
				newItem.flip(true, false);
				appendErArray.add(newItem);
			}
		}
		for(AtlasRegion region:appendErArray){
			atlas.getRegions().add(region);
			System.out.println("create left region :"+region.name);
		}
	}

	public StartUpScene getStartUpScene() {
		return startUpScene;
	}

	public NpcListScene getNpcListScene() {
		return npcListScene;
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	
}
