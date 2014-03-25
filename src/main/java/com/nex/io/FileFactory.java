package com.nex.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileFactory {
	private int buffersize = 1048576;//10 MB
	public String path;
	public String fileName;
	public FileFactory(String path, String fileName) {
		super();
		this.path = path;
		this.fileName = fileName;
	}
	
	public void createFileoutputStream() throws FileNotFoundException {
		
	}
	public void saveFileAsStream(InputStream stream) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(getFullName()));
		createFolderIfNotExists();
		//when buffer is higher then available size, then change buffersize to filesize
		int available = stream.available();
		if(buffersize > available) {
			buffersize = available;
		}
		byte[] buffer = new byte[buffersize];
		while(stream.read(buffer) >= 0) {
			fos.write(buffer);
			buffer = new byte[buffersize];//clear buffer
		}
		fos.flush();
		fos.close();
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
