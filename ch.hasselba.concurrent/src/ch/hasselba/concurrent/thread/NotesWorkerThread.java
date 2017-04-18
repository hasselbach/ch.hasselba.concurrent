package ch.hasselba.concurrent.thread;

import ch.hasselba.domino.GC;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;

public class NotesWorkerThread extends NotesThread {

	private Session session = null;

	public NotesWorkerThread(Runnable r, String threadName) {
		super(r, threadName);
		while (true) {
			try {
				if (!(Thread.currentThread() instanceof NotesThread))
					NotesThread.sinitThread();

				this.session = NotesFactory.createSession();
				break;
			} catch (NotesException ne) {
				ne.printStackTrace();
				System.out.println("Fehler bei Notes Initialisierung");
			}
		}
	}

	@Override
	public synchronized void start() {
		super.start();

	}

	@Override
	public void finalize() {
		super.finalize();
	}

	@Override
	public void initThread() {
		// first call super to let notes.jar do its job
		super.initThread();
	}

	@Override
	public void termThread() {
		session = GC.recycle(session);
		super.termThread();
	}

	public void reInit() {
		session = GC.recycle(session);
		NotesThread.stermThread();
		NotesThread.sinitThread();
	}

	public Session getSession() {
		return session;
	}

	/**
	 * get the Notes Session of the current thread only the current thread can
	 * set a session
	 * 
	 * @param session
	 */
	@SuppressWarnings("unused")
	private void setSession(Session session) {
		this.session = session;
	}
}
