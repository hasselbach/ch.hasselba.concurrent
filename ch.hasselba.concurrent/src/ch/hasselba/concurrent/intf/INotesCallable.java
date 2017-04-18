package ch.hasselba.concurrent.intf;

import java.util.concurrent.Callable;

import lotus.domino.NotesException;
import lotus.domino.Session;

public interface INotesCallable<T> extends Callable<T> {
	public NotesException getNotesException();
	public void setNotesException( NotesException ne );
	public T callNotes(Session session) throws Exception;
}
