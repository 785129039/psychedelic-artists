package com.nex.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileFactory {

	public String path;
	public String fileName;
	public FileFactory(String path, String fileName) {
		super();
		this.path = path;
		this.fileName = fileName;
	}
	
	public void saveFile(byte[] bytes) throws IOException {
			createFolderIfNotExists();
			FileOutputStream fos = new FileOutputStream(new File(getFullName()));
			fos.write(bytes);
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
