package ch.hasselba.service;

import ch.hasselba.concurrent.pool.NotesThreadPool;

/**
 * ServiceLocator Interface
 * 
 * @author Sven Hasselbach
 */
public interface IServiceLocator {

	/**
	 * sets the NotesThreadPool
	 * 
	 * @param notesThreadPool
	 */
	public void setNotesThreadPool(NotesThreadPool notesThreadPool);

	/**
	 * gets the NotesThreadPool
	 * 
	 * @return
	 * 	the NotesThreadPool
	 */
	public NotesThreadPool getNotesThreadPool();

}
