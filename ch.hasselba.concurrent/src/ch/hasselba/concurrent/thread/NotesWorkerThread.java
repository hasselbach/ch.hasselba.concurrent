package ch.hasselba.concurrent.thread;

import ch.hasselba.domino.GC;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;
/**
 * A Notes Worker Thread with a valid NotesSession
 * 
 * @author Sven Hasselbach
 */
public class NotesWorkerThread extends NotesThread {

	private Session session = null;

	/**
	 * initialises the NotesThread and generates a Session
	 * 
	 * @param r
	 * 	A runnable to execute 
	 * @param threadName
	 * 	a name for the current thread
	 */
	public NotesWorkerThread(Runnable r, String threadName) {
		super(r, threadName);
		
		// wait & retry until we have a valid NotesSession
		while (true) {
			try {
				if (!(Thread.currentThread() instanceof NotesThread))
					NotesThread.sinitThread();

				this.session = NotesFactory.createSession();
				break;
			} catch (NotesException ne) {
				ne.printStackTrace();
			}
		}
	}

	/**
	 * terminates the NotesThread and recycles the Session instance
	 */
	@Override
	public void termThread() {
		session = GC.recycle(session);
		super.termThread();
	}

	/**
	 * reinitialises the NotesThread
	 */
	public void reInit() {
		session = GC.recycle(session);
		NotesThread.stermThread();
		NotesThread.sinitThread();
	}

	/**
	 * gets the current Notes Session of the thread
	 * 
	 * @return
	 * 	the Session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * sets the Notes Session of the current thread
	 * only the current thread can set a session
	 * 
	 * @param session
	 * 		NotesSession to use
	 */
	@SuppressWarnings("unused")
	private void setSession(Session session) {
		this.session = session;
	}
}
