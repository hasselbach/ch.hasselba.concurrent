package ch.hasselba.concurrent;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Future with a reference to it's callable
 * 
 * @author Sven
 *
 * @param <T>
 */
public class FutureTaskWithRef<T> extends FutureTask<T> {

	private WeakReference<Callable<T>> callable = null;

	public FutureTaskWithRef(Callable<T> callable) {
		super(callable);
		this.setCallable(callable);
	}

	public Callable<T> getCallable() {
		return callable.get();
	}

	public void setCallable(Callable<T> callable) {
		this.callable = new WeakReference<Callable<T>>(callable);
	}

}
