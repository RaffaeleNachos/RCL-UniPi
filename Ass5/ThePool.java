//Raffaele Apetino 549220

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThePool {
	
	public ThreadPoolExecutor executor;
	private LinkedBlockingQueue<Runnable> myQueue;
	
	public ThePool(int numThread) {
		myQueue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(numThread, numThread, 60L, TimeUnit.MILLISECONDS, myQueue);
	}
	
	public void executeTask(Consumer task) {
		executor.execute(task);
	}
	
	public void closePool() {
		executor.shutdown(); 
	}

	public boolean empityQueue() {
		return myQueue.isEmpty();
	}

}
