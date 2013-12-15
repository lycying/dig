package info.u250.digs.gdx_encrypt;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class EncryptOggRule implements  AliasResourceManager.LoadResourceRule{
	@Override
	public boolean match(FileHandle file) {
		boolean result = file.extension().equals("ogg");
		if(result) Engine.getAssetManager().load(file.path().replace("\\", "/"),Sound.class);
		return  result;
	}
}
