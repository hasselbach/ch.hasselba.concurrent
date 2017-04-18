package ch.hasselba.concurrent.thread;

import java.util.concurrent.ThreadFactory;

public class NotesThreadFactory implements ThreadFactory {

	private String threadName;
	private String serverName;

	public NotesThreadFactory(String threadName, String serverName) {
		this.threadName = threadName;
		this.serverName = serverName;
	}

	protected String getThreadName() {
		return threadName;
	}

	protected String getServerName() {
		return serverName;
	}

	@Override
	public Thread newThread(Runnable r) {
		return new NotesWorkerThread(r, getThreadName());
	}

}
