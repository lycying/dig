package info.u250.digs.gdx_encrypt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

public class QvgEncryptPixmap extends Pixmap{
	public QvgEncryptPixmap(String file){
		this(new EncryptPixmapKalasx(Gdx.files.internal(Wahaha.wahaha(file)+".dc")));
	}
	private QvgEncryptPixmap(EncryptPixmapKalasx kalasx) {
		super(kalasx.bytes, 0, kalasx.bytes.length);
	}
	private static class EncryptPixmapKalasx{
		byte[] bytes;
		public EncryptPixmapKalasx(FileHandle file){
			bytes = file.readBytes();
			int length = bytes.length;
			int seek = length%100+100;
			for(int i=0;i<seek;i++){
				byte b = bytes[i];
				bytes[i]=bytes[length-i-1];
				bytes[length-i-1] = b;
			}
		}
	} 
}
