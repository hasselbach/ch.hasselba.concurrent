package ch.hasselba.concurrent.pool;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import ch.hasselba.concurrent.DBConfig;
import ch.hasselba.concurrent.FutureTaskWithRef;
import ch.hasselba.concurrent.NotesDBCallableTask;
import ch.hasselba.concurrent.intf.INotesCallable;
import ch.hasselba.concurrent.intf.INotesRunnable;
import ch.hasselba.concurrent.queue.ForceQueuePolicy;
import ch.hasselba.concurrent.queue.ScalingQueue;
import lotus.domino.NotesException;

public class NotesThreadPool {

	private NotesThreadPoolExecutor executor;
	private ThreadFactory threadFactory;

	public NotesThreadPool(int nThreads, int maxThreads, String name, ThreadFactory threadFactory) {

		this.threadFactory = threadFactory;

		ScalingQueue queue = new ScalingQueue();

		executor = new NotesThreadPoolExecutor(nThreads, maxThreads, 30, TimeUnit.SECONDS, queue, threadFactory);
		executor.prestartAllCoreThreads();
		executor.setRejectedExecutionHandler(new ForceQueuePolicy());

		queue.setThreadPoolExecutor(executor);

	}

	/**
	 * NotesThreadPool without ThreadFactory
	 * 
	 * Ist für Aufrufe aus Lotus Notes Agents heraus gedacht, da sich die Agent
	 * Threads sonst *verhaken* und die Agenten nie beendet werden.
	 * 
	 * @param nThreads
	 * 	
	 * @param maxThreads
	 * @param name
	 */
	public NotesThreadPool(int nThreads, int maxThreads, String name) {

		this.threadFactory = null;

		ScalingQueue queue = new ScalingQueue();

		executor = new NotesThreadPoolExecutor(nThreads, maxThreads, 30, TimeUnit.SECONDS, queue);
		executor.prestartAllCoreThreads();
		executor.setRejectedExecutionHandler(new ForceQueuePolicy());

		queue.setThreadPoolExecutor(executor);

	}

	/**
	 * Function to submit a NotesRunnableTask object to the ExecutorService
	 * which runs all the Lotus Notes stuff
	 * 
	 * @param
	 */
	public void doSomeNotesWork(INotesRunnable r) {
		executor.execute(r);
	}

	/**
	 * Function to submit a RunnableTask object to the ExecutorService which
	 * runs some stuff
	 * 
	 * @param archiveDb
	 * 
	 * @param
	 */
	public void doSomeWorkAndWait(INotesRunnable r) {
		executor.submit(r);
	}

	/**
	 * Function to submit a RunnableTask object to the ExecutorService which
	 * runs some stuff
	 * 
	 * @param
	 */
	public void doSomeWork(INotesRunnable r) {

		executor.execute(r);
	}

	/**
	 * Function to submit a NotesCallableTask object to the ExecutorService
	 * which runs all the Lotus Notes stuff
	 * 
	 * @param
	 */
	public void doSomeNotesWorkAndWait(INotesCallable<?> c) {
		executor.submit(c);
	}

	/**
	 * Function to submit a NotesCallableTask object to the ExecutorService
	 * which runs all the Lotus Notes stuff
	 * 
	 * @param
	 */
	public <T> Future<T> doSomeNotesWorkReturnFuture(INotesCallable<T> c) {
		Future<T> callable = executor.submit(c);
		return callable;
	}

	/**
	 * Function to submit a NotesCallableTask object to the ExecutorService
	 * which runs all the Lotus Notes stuff
	 * 
	 * @param
	 * @throws NotesException
	 */
	public <T> Collection<T> doSomeNotesWorkOnAllDatabasesAndReturnResult(NotesDBCallableTask<T> c,
			Set<DBConfig> dbsConfig) throws NotesException {

		Collection<Future<T>> futures = new ConcurrentLinkedQueue<Future<T>>();
		boolean isFirstCallable = false;
		NotesDBCallableTask<T> clonedCallable = null;

		for (DBConfig dbConfig : dbsConfig) {

			if (isFirstCallable == false) {
				clonedCallable = c;
				isFirstCallable = true;
			} else {
				try {
					clonedCallable = c.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}

			clonedCallable.setDBConfig(dbConfig);
			Future<T> future = this.doSomeNotesWorkReturnFuture(clonedCallable);
			futures.add(future);
		}

		Collection<T> result = new LinkedList<T>();
		try {

			for (Future<T> future : futures) {
				// check if we have a Future with a reference to it's callable
				if (future instanceof FutureTaskWithRef) {
					FutureTaskWithRef<T> futureWithRef = (FutureTaskWithRef<T>) future;
					Callable<T> callable = futureWithRef.getCallable();
					if (callable instanceof INotesCallable) {
						// do we have a NotesException? Let's throw it!
						if (((INotesCallable<T>) callable).getNotesException() != null)
							throw ((INotesCallable<T>) callable).getNotesException();
					}
				}

				T tmpResult = future.get();

				if (tmpResult != null)
					result.add(tmpResult);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception ne) {
			ne.printStackTrace();
		}

		return result;
	}

	/**
	 * Function to submit a NotesCallableTask object to the ExecutorService
	 * which runs all the Lotus Notes stuff
	 * 
	 * @param
	 */
	public <T> Collection<T> doMoreNotesWorkOnAllDatabasesAndReturnResult(Collection<NotesDBCallableTask<T>> cs,
			Set<DBConfig> dbsConfig) {

		Collection<Future<T>> futures = new ConcurrentLinkedQueue<Future<T>>();

		for (NotesDBCallableTask<T> c : cs) {

			boolean isFirstCallable = false;
			NotesDBCallableTask<T> clonedCallable = null;

			for (DBConfig dbConfig : dbsConfig) {

				if (isFirstCallable == false) {
					clonedCallable = c;
					isFirstCallable = true;
				} else {
					try {
						clonedCallable = c.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}

				clonedCallable.setDBConfig(dbConfig);
				Future<T> future = this.doSomeNotesWorkReturnFuture(clonedCallable);
				futures.add(future);
			}
		}
		Collection<T> result = new LinkedList<T>();
		try {

			for (Future<T> future : futures) {
				T tmpResult = future.get();
				if (tmpResult != null)
					result.add(tmpResult);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}
		return result;
	}

	/**
	 * make the executor accept no new threads and finish all existing threads
	 * in the queue
	 * 
	 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html#shutdown--
	 */
	public void shutDown() {

		// make the executor accept no new threads and let them finish alone
		executor.shutdown();
		// wait 60 seconds to await termination of all threads
		try {
			boolean terminatedBeforeTimeout = executor.awaitTermination(60, TimeUnit.SECONDS);
			if (!terminatedBeforeTimeout) {
				executor.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!executor.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}

		} catch (InterruptedException e1) {

			executor.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}

	}

	public ThreadFactory getThreadFactory() {
		return threadFactory;
	}

}
