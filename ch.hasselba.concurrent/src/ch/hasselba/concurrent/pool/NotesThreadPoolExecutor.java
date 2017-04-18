package ch.hasselba.concurrent.pool;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ch.hasselba.concurrent.queue.ScalingQueue;

public class NotesThreadPoolExecutor extends ThreadPoolExecutor {

	public NotesThreadPoolExecutor(int nThreads, int maxThreads, ThreadFactory threadFactory) {
		super(nThreads, maxThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
	}

	public NotesThreadPoolExecutor(int nThreads, int maxThreads, long keepAliveTime, TimeUnit timeUnit,
			ScalingQueue queue, ThreadFactory threadFactory) {
		super(nThreads, maxThreads, keepAliveTime, timeUnit, queue, threadFactory);
	}

	/**
	 * NotesThreadPoolExecutor ohne ThreadFactory
	 * 
	 * Ist für Aufrufe aus Lotus Notes Agents heraus gedacht, da sich die Agent
	 * Threads sonst *verhaken* und die Agenten nie beendet werden.
	 * 
	 * @param nThreads
	 * @param maxThreads
	 * @param keepAliveTime
	 * @param timeUnit
	 * @param queue
	 * @param logger
	 */
	public NotesThreadPoolExecutor(int nThreads, int maxThreads, long keepAliveTime, TimeUnit timeUnit,
			ScalingQueue queue) {
		super(nThreads, maxThreads, keepAliveTime, timeUnit, queue);
	}

	@Override
	public void execute(Runnable command) {
		super.execute(command);
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return super.shutdownNow();
	}

	@Override
	protected void finalize() {
		super.finalize();
	}

	@Override
	public long getCompletedTaskCount() {
		return super.getCompletedTaskCount();
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
	}

}
