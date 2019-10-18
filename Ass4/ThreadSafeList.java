import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeList {
	
	LinkedList<String> dirList;
	ReentrantLock myLock;
	final Condition notEmpity;
	AtomicBoolean hasFinished;
	
	public ThreadSafeList() {
		dirList = new LinkedList<>();
		myLock = new ReentrantLock();
		notEmpity = myLock.newCondition();
		hasFinished = new AtomicBoolean(false);
	}
	
	public String peekDir() {
		String tmp;
		myLock.lock();
		tmp = dirList.peekLast();
		myLock.unlock();
		return tmp;
	}
	
	public String getDir() {
		String tmp;
		myLock.lock();
		tmp = dirList.pollLast();
		myLock.unlock();
		return tmp;
	}
	
	public void addDir(String dir) {
		myLock.lock();
		dirList.addFirst(dir);
		notEmpity.signalAll();
		myLock.unlock();
	}
	
	public void stop() {
		myLock.lock();
		hasFinished.set(true);
		notEmpity.signalAll();
		myLock.unlock();
	}
	
	public boolean getStatus() {
		return hasFinished.get();
	}
}
