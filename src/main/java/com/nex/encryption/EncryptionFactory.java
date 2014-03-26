package com.nex.encryption;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nex.annotation.Logger;
@Logger
public class EncryptionFactory implements Runnable {

	private int duration;
	private boolean running = false;
	private Map<String, Encryption> encryptions = new HashMap<String, Encryption>();
	private Thread thread;
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public EncryptionFactory() {
		restart();
	}
	
	public synchronized Encryption createNewEncryption() throws Exception {
		return new Encryption();
	}

	public synchronized Encryption get(String code) {
		return encryptions.get(code);
	}
	
	public synchronized void put(String code, Encryption e) {
		encryptions.put(code, e);
	}
	
	public synchronized void remove(String code) {
		encryptions.remove(code);
	}
	
	public int getDuration() {
		return duration;
	}
	
	@Override
	public void run() {
		if(running) throw new RuntimeException("EncryptionFactory can be started only by System");
		this.running = true;
		try {
			while(this.running) {
				this.clean();
				Thread.yield();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			log.error("", e);
		}
		this.running = false;
		restart();
	}
	
	private void clean() {
		synchronized (this.encryptions) {
			Set<String> removed = new HashSet<String>();
			for(Map.Entry<String, Encryption> encryption: this.encryptions.entrySet()) {
				Encryption e = encryption.getValue();
				long created = e.getCreated();
				long current = System.currentTimeMillis();
				long result = (current - created) / 1000;//to minutes
				if(result >= duration * 60) {
					removed.add(encryption.getKey());
				}
			}
			for(String code: removed) {
				this.remove(code);
			}
		}
	}
	
	private void restart() {
		if(this.thread != null){
			this.thread.interrupt();
		}
		this.thread = new Thread(this, "encryption-thread");
		this.thread.start();
	}
	
}
