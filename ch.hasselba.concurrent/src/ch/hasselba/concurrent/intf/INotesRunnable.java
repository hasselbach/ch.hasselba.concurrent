package ch.hasselba.concurrent.intf;

import lotus.domino.NotesException;
import lotus.domino.Session;

public interface INotesRunnable extends Runnable {
	public NotesException getNotesException();
	public void setNotesException( NotesException ne );
	public void runNotes(Session session);
}
