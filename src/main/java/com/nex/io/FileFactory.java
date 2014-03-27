package com.nex.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileFactory {
	private static final int buffersize = 1048576;//10 MB
	public String path;
	public String fileName;
	public FileFactory(String path, String fileName) {
		super();
		this.path = path;
		this.fileName = fileName;
	}

	public void saveFileAsStream(InputStream stream) throws IOException {
		createFolderIfNotExists();
		FileOutputStream fos = new FileOutputStream(new File(getFullName()));
		//when buffer is higher then available size, then change buffersize to filesize
		stream(fos, stream);
	}
	
	public InputStream getSaveFileInputStream() throws IOException {
		return new FileInputStream(new File(getFullName()));
	}
	
	public void stream(OutputStream os) throws IOException {
		InputStream fis = getSaveFileInputStream();
		stream(os, fis);
		fis.close();
	}
	public void stream(OutputStream os, InputStream stream) throws IOException {
		int total = stream.available();
		int available = total;
		int readed = 0;
		byte[] buffer = new byte[getBufferSize(available)];
		while((readed += stream.read(buffer)) >= 0) {
			os.write(buffer);
			available = total - readed;
			if(available <=0) {
				break;
			}
			buffer = new byte[getBufferSize(available)];//clear buffer
		}
		os.flush();
		os.close();
	}
	
	private int getBufferSize(int available) {
		if(buffersize > available) {
			return available;
		}
		return buffersize;
	}
	
	public int length() throws IOException {
		InputStream is = getSaveFileInputStream();
		try {
			return is.available();
		} finally {
			is.close();
		}
	}
	
	public Boolean exist() {
		File f = new File(getFullName());
		return f.exists();
	}
	
	public void deleteFile() {
		File f = new File(getFullName());
		f.delete();
	}
	
	private String getFullName() {
		return this.path + this.fileName;
	}
	private void createFolderIfNotExists() {
		File f = new File(this.path);
		if(!f.exists()) {
			f.mkdirs();
		}
	}
}
