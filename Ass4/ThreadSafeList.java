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
		hasFinished = new AtomicBoolean(false); //variabile booleana che avvisa i consumatori che il produttore ha terminato
	}
	
	//returns: restituisce l'ultimo elemento della lista senza rimuoverlo
	public String peekDir() {
		return dirList.peekLast();
	}
	
	//returns: restuisce l'ultimoi elemento della lista e lo rimuove
	public String getDir() {
		return dirList.pollLast();
	}
	
	//effects: aggiunge in testa
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
