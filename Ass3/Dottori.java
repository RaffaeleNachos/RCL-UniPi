import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Dottori {
	
	ArrayList<Integer> Doctors;
	ReentrantLock mylock;
	final Condition allFree;
	final Condition iFree;
	final Condition oneFree;
	int freeDocs = 10;
	
	public Dottori() {
		Doctors = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Doctors.add(i, 0);
		}
		mylock = new ReentrantLock();
		allFree = mylock.newCondition();
		iFree = mylock.newCondition();
		oneFree = mylock.newCondition();
	}
	
	public int getFreeDocs() {
		int tmp = 0;
		for(int i = 0; i < 10; i++) {
			if(Doctors.get(i)==0) {
				tmp++;
			}
		}
		return tmp;
	}
	
	public boolean isFreeDoc_i(int i) {
		if(Doctors.get(i)==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setAllDocs() {
		for(int i = 0; i < 10; i++) {
			Doctors.set(i, 1);
		}
	}
	
	public void freeAllDocs() {
		for(int i = 0; i < 10; i++) {
			Doctors.set(i, 0);
		}
	}
	
	public void setDoc_i(int i) {
		Doctors.set(i, 1);
	}
	
	public void freeDoc_i(int i) {
		Doctors.set(i, 0);
	}
	
	public int setDoc() {
		for(int i = 0; i < 10; i++) {
			if(Doctors.get(i)==0) {
				Doctors.set(i, 1);
				return i;
			}
		}
		return -1;
	}
	
}
