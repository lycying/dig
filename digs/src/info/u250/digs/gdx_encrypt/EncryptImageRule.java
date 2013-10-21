package info.u250.digs.gdx_encrypt;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.resources.AliasResourceManager;

public class EncryptImageRule implements  AliasResourceManager.LoadResourceRule{
	@Override
	public boolean match(FileHandle file) {
		boolean result = 
				file.extension().contains("idt") ;
		if(result) Engine.getAssetManager().load(file.path().replace("\\", "/"),Texture.class);
		return  result;
	}
}
