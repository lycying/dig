package info.u250.digs.gdx_encrypt;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;

public class EncryptImageFileHandle extends FileHandle{
	FileHandle fileHandle;
	public EncryptImageFileHandle(FileHandle fileHandle){
		this.fileHandle = fileHandle;
	}
	@Override
	public byte[] readBytes() {
		//fuck you !
		if(!this.extension().contains("idt")){
			return super.readBytes();
		}
		
		byte[] bytes =  super.readBytes();
//		String[] splits = this.path().split("levels/");
//		String sid = splits[1].replace(".idt1", "").replace("@", "/");
//		String result = new String(Des.decryptDES(sid, "fuckyoua"));
//		int id = Integer.parseInt(result);
//		int length = bytes.length;
//		for(int i=0;i<Dao.instance.getPet(id).getSeek();i++){
//			byte b = bytes[i];
//			bytes[i]=bytes[length-i-1];
//			bytes[length-i-1] = b;
//		}
		return bytes;
	}
	@Override
	public String path() {
		
		return fileHandle.path();
	}
	@Override
	public String name() {
		
		return fileHandle.name();
	}
	@Override
	public String extension() {
		
		return fileHandle.extension();
	}
	@Override
	public String nameWithoutExtension() {
		
		return fileHandle.nameWithoutExtension();
	}
	@Override
	public FileType type() {
		
		return fileHandle.type();
	}
	@Override
	public File file() {
		
		return fileHandle.file();
	}
	@Override
	public InputStream read() {
		
		return fileHandle.read();
	}
	@Override
	public BufferedInputStream read(int bufferSize) {
		
		return fileHandle.read(bufferSize);
	}
	@Override
	public Reader reader() {
		
		return fileHandle.reader();
	}
	@Override
	public Reader reader(String charset) {
		
		return fileHandle.reader(charset);
	}
	@Override
	public BufferedReader reader(int bufferSize) {
		
		return fileHandle.reader(bufferSize);
	}
	@Override
	public BufferedReader reader(int bufferSize, String charset) {
		
		return fileHandle.reader(bufferSize, charset);
	}
	@Override
	public String readString() {
		
		return fileHandle.readString();
	}
	@Override
	public String readString(String charset) {
		
		return fileHandle.readString(charset);
	}
	@Override
	public int readBytes(byte[] bytes, int offset, int size) {
		
		return fileHandle.readBytes(bytes, offset, size);
	}
	@Override
	public OutputStream write(boolean append) {
		
		return fileHandle.write(append);
	}
	@Override
	public void write(InputStream input, boolean append) {
		
		fileHandle.write(input, append);
	}
	@Override
	public Writer writer(boolean append) {
		
		return fileHandle.writer(append);
	}
	@Override
	public Writer writer(boolean append, String charset) {
		
		return fileHandle.writer(append, charset);
	}
	@Override
	public void writeString(String string, boolean append) {
		
		fileHandle.writeString(string, append);
	}
	@Override
	public void writeString(String string, boolean append, String charset) {
		
		fileHandle.writeString(string, append, charset);
	}
	@Override
	public void writeBytes(byte[] bytes, boolean append) {
		
		fileHandle.writeBytes(bytes, append);
	}
	@Override
	public void writeBytes(byte[] bytes, int offset, int length, boolean append) {
		
		fileHandle.writeBytes(bytes, offset, length, append);
	}
	@Override
	public FileHandle[] list() {
		
		return fileHandle.list();
	}
	@Override
	public FileHandle[] list(String suffix) {
		
		return fileHandle.list(suffix);
	}
	@Override
	public boolean isDirectory() {
		
		return fileHandle.isDirectory();
	}
	@Override
	public FileHandle child(String name) {
		
		return fileHandle.child(name);
	}
	@Override
	public FileHandle parent() {
		
		return fileHandle.parent();
	}
	@Override
	public void mkdirs() {
		
		fileHandle.mkdirs();
	}
	@Override
	public boolean exists() {
		
		return fileHandle.exists();
	}
	@Override
	public boolean delete() {
		
		return fileHandle.delete();
	}
	@Override
	public boolean deleteDirectory() {
		
		return fileHandle.deleteDirectory();
	}
	@Override
	public void copyTo(FileHandle dest) {
		
		fileHandle.copyTo(dest);
	}
	@Override
	public void moveTo(FileHandle dest) {
		
		fileHandle.moveTo(dest);
	}
	@Override
	public long length() {
		
		return fileHandle.length();
	}
	@Override
	public long lastModified() {
		
		return fileHandle.lastModified();
	}
	@Override
	public String toString() {
		
		return fileHandle.toString();
	}
	
}
