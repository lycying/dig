package info.u250.digs.gdx_encrypt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class EncryptImageInternalFileHandleResolver implements FileHandleResolver {
	@Override
	public FileHandle resolve (String fileName) {
		return new EncryptImageFileHandle(Gdx.files.internal(fileName));
	}
}
