//Raffaele Apetino - 549220

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeList {
	//Implementazione della mia LinkedList Thread Safe
	
	LinkedList<String> dirList;
	ReentrantLock myLock;
	final Condition notEmpity;
	AtomicBoolean hasFinished;
	
	public ThreadSafeList() {
		dirList = new LinkedList<>();
		myLock = new ReentrantLock();
		notEmpity = myLock.newCondition(); //condition variable che mi farà ciclare i cosnumatori se la lista è vuota
		hasFinished = new AtomicBoolean(false);
	}
	
	public String peekDir() {
		return dirList.peekLast();
	}
	
	public String getDir() {
		return dirList.pollLast();
	}
	
	public void addDir(String dir) {
		dirList.addFirst(dir);
	}
	
	public void stop() {
		hasFinished.set(true);
	}
	
	public boolean getStatus() {
		return hasFinished.get();
	}
}
