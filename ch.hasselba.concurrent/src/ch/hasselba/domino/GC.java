package ch.hasselba.domino;

import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.View;

public class GC {

	private static boolean disableRecycling = false;
	private static boolean debugGC = false;

	public static boolean isDisableRecycling() {
		return disableRecycling;
	}

	public static void setDisableRecycling(boolean disableRecycling) {
		GC.disableRecycling = disableRecycling;
	}

	private GC() {
		// Hide from Public
	}

	public static <T> T recycle(lotus.domino.Base obj) {
		logGCCaller(obj);
		if (disableRecycling)
			return null;

		try {
			obj.recycle();
		} catch (Exception e) {
		}
		obj = null;

		return null;

	}

	private static void logGCCaller(lotus.domino.Base toRecycle) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}