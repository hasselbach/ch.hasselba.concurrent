package ch.hasselba.service;

import ch.hasselba.concurrent.pool.NotesThreadPool;

public interface IServiceLocator {

	public void setNotesThreadPool(NotesThreadPool notesThreadPool);

	public NotesThreadPool getNotesThreadPool();

}
