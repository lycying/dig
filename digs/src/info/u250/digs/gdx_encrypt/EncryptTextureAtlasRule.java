package info.u250.digs.gdx_encrypt;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class EncryptTextureAtlasRule implements  AliasResourceManager.LoadResourceRule{
	@Override
	public boolean match(FileHandle file) {
		boolean result = file.extension().equals("idx") ;
		if(result) Engine.getAssetManager().load(file.path().replace("\\", "/"),TextureAtlas.class);
		return  result;
	}
}
