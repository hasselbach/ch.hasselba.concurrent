package ch.hasselba.concurrent;

import ch.hasselba.concurrent.intf.INotesRunnable;
import ch.hasselba.concurrent.thread.NotesWorkerThread;
import lotus.domino.NotesException;
import lotus.domino.Session;

public abstract class NotesRunnableTask implements INotesRunnable {

	NotesException exception = null;

	public void run() {
		// catch the session for this working-task
		Session session = ((NotesWorkerThread)Thread.currentThread()).getSession();
		
		// redirect to runNotes
		this.runNotes(session);
	}
	
	public abstract void runNotes(Session session);

	public NotesException getNotesException() {
		return exception;
	}

	public void setNotesException(NotesException ne) {
		exception = ne;
	}


}
