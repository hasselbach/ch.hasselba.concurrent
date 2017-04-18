package ch.hasselba.concurrent;

import ch.hasselba.concurrent.intf.INotesCallable;
import ch.hasselba.concurrent.thread.NotesWorkerThread;
import lotus.domino.NotesException;
import lotus.domino.Session;

public abstract class NotesCallableTask<T> implements INotesCallable<T> {

	public NotesException exception = null;

	@Override
	public T call() throws Exception {
		// catch the session for this working-task
		Session session = ((NotesWorkerThread) Thread.currentThread()).getSession();
		exception = null;

		// redirect to runNotes
		return this.callNotes(session);
	}

	@Override
	public NotesException getNotesException() {
		return exception;
	}

	@Override
	public void setNotesException(NotesException ne) {
		exception = ne;
	}

	@Override
	public abstract T callNotes(Session session) throws Exception;

}
