package ch.hasselba.concurrent;

import lotus.domino.Session;

public abstract class NotesDBCallableTask<T> extends NotesCallableTask<T> implements Cloneable {

	/**
	 * WARNING!
	 * 
	 * The clone() method of the class is used in NotesThreadPool. To avoid
	 * problems with referenced objects, please check
	 * 
	 * http://stackoverflow.com/questions/869033/how-do-i-copy-an-object-in-java
	 * 
	 * Keep in mind that JVM when called for cloning, do following things: 1) If
	 * the class has only primitive data type members then a completely new copy
	 * of the object will be created and the reference to the new object copy
	 * will be returned.
	 * 
	 * 2) If the class contains members of any class type then only the object
	 * references to those members are copied and hence the member references in
	 * both the original object as well as the cloned object refer to the same
	 * object.
	 * 
	 */
	private DBConfig dbConfig = null;

	public DBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDBConfig(final DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public NotesDBCallableTask() {
		super();
	}

	public NotesDBCallableTask(DBConfig dbConfig) {
		super();
		this.dbConfig = dbConfig;
	}


	public abstract T callNotes(Session session, final DBConfig dbConfig) throws Exception;

	@SuppressWarnings("unchecked")
	public NotesDBCallableTask<T> clone() throws CloneNotSupportedException {
		return (NotesDBCallableTask<T>) super.clone();
	}

	public T callNotes(Session session) throws Exception {
			return callNotes(session, getDbConfig());
	}

}
