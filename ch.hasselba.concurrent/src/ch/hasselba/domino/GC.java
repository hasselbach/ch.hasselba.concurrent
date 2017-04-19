package ch.hasselba.domino;

import lotus.domino.Base;
import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.View;

/**
 * A garbage collector for recycling of Domino objects
 * including debugging possibility
 * 
 * @author Sven Hasselbach
 */
public class GC {

	/**
	 * flag to disable recycling
	 */
	private static boolean disableRecycling = false;
	
	/**
	 * flag to enable debugging
	 */
	private static boolean debugGC = false;

	private GC() {
		// Hide from Public
	}
	
	/**
	 * gets the current debugging setting
	 * 
	 * @return
	 * 	true if debugging is enabled, else false
	 */
	public static boolean isDisableRecycling() {
		return disableRecycling;
	}

	/**
	 * sets if recycling is disabled or not
	 * 
	 * @param disableRecycling
	 * 	true if recycling is disabled
	 */
	public static void setDisableRecycling(boolean disableRecycling) {
		GC.disableRecycling = disableRecycling;
	}


	/**
	 * a global recycling method for Domino objects
	 * @param obj
	 * 		a valid Domino object
	 * @return
	 * 		null
	 */
	public static <T> T recycle(Base obj) {
		
		logGCCaller(obj);
		
		if (disableRecycling)
			return null;

		try {
			obj.recycle();
		} catch (Exception e) {
			// ignore an error
		}
		obj = null;

		return null;

	}

	/**
	 * logs the current calling method which wants to recycle an object
	 * 
	 * @param toRecycle
	 * 		Domino object to recylce
	 */
	private static void logGCCaller(Base toRecycle) {
		if (debugGC == false)
			return;

		try {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			StackTraceElement stackTraceElement = stackTraceElements[4];
			if (toRecycle == null) {
				System.out.println("[GC] Object is null!  @Line " + stackTraceElements[3].getLineNumber() + " -=> "
						+ stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + " @ Line "
						+ stackTraceElement.getLineNumber());
			} else {
				System.out.println("[GC] " + toRecycle.getClass().getCanonicalName() + " -=> "
						+ stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + " @ Line "
						+ stackTraceElement.getLineNumber());
			}
			if (toRecycle instanceof Database) {
				System.out.println("[GC] Database: " + ((Database) toRecycle).getFilePath());
			}
			if (toRecycle instanceof View) {
				System.out.println("[GC] View: " + ((View) toRecycle).getName());
			}
		} catch (NotesException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}