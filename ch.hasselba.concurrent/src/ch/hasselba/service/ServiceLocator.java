package ch.hasselba.service;

import ch.hasselba.concurrent.pool.NotesThreadPool;

/**
 * A ServiceLocator implementation for NotesThreadPool
 * 
 * @author Sven Hasselbach
 */
public class ServiceLocator implements IServiceLocator {

	protected NotesThreadPool notesThreadPool;

	@Override
	public void setNotesThreadPool(NotesThreadPool notesThreadPool) {
		this.notesThreadPool = notesThreadPool;
	}

	@Override
	public NotesThreadPool getNotesThreadPool() {
		return notesThreadPool;
	}

}
