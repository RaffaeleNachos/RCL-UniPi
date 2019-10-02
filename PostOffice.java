import java.util.concurrent.*;

public class PostOffice{
	
	private ThreadPoolExecutor executor;
	private ArrayBlockingQueue<Runnable> myQueue;
	
	public PostOffice(int nOffWorker, int dimQueue) {
		myQueue = new ArrayBlockingQueue<Runnable>(dimQueue);
		executor = new ThreadPoolExecutor(nOffWorker, nOffWorker, 0L, TimeUnit.MILLISECONDS, myQueue);
		System.out.println(executor.getPoolSize());
	}
	
	
	public void executeTask (Task task) {
		executor.execute(task);
	}
	
	public void closePostOffice() {
		executor.shutdown();
	}
	
	public int freeQueue() {
		return myQueue.size();
	}
}
