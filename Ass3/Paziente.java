
public class Paziente extends Thread {
	
	String name;
	int priority;
	int index;
	int k;
	Dottori doctrs;
	
	public Paziente(String name, int priority, int k, Dottori doctrs) {
		this.name=name;
		this.priority=priority;
		this.k=k;
		if(this.priority==1) {
			this.index = (int) (Math.random()*10);
		}
		this.doctrs = doctrs;
	}
	
	public void run() {
		for (int i = k; i >= 0; i--) {
			if(priority==2) { //se sono un paziente con codice rosso
				doctrs.mylock.lock();
				while(doctrs.getFreeDocs()!=10) {
					try {
						doctrs.allFree.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					doctrs.setAllDocs();
					doctrs.mylock.unlock();
					System.out.println("Paziente " + name + " / sto per essere gestito. Thread: " + Thread.currentThread().getName());
					Thread.sleep((long)(Math.random()*5000));
					doctrs.mylock.lock();
					doctrs.freeAllDocs();
					if (doctrs.mylock.hasWaiters(doctrs.allFree)) doctrs.allFree.signal();
					else if (doctrs.mylock.hasWaiters(doctrs.iFree)) doctrs.iFree.signalAll();
					else if (doctrs.mylock.hasWaiters(doctrs.oneFree)) doctrs.oneFree.signalAll();
					doctrs.mylock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(priority==1) { //se sono un paziente con codice giallo
				doctrs.mylock.lock();
				while(doctrs.isFreeDoc_i(index)==false) {
					try {
						doctrs.iFree.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					doctrs.setDoc_i(index);
					doctrs.mylock.unlock();
					System.out.println("Paziente " + name + " / Indice scelto: " + index + " sto per essere gestito. Thread: " + Thread.currentThread().getName());
					Thread.sleep((long)(Math.random()*5000));
					doctrs.mylock.lock();
					doctrs.freeDoc_i(index);
					if (doctrs.mylock.hasWaiters(doctrs.allFree)) doctrs.allFree.signal();
					else if (doctrs.mylock.hasWaiters(doctrs.iFree)) doctrs.iFree.signalAll();
					else if (doctrs.mylock.hasWaiters(doctrs.oneFree)) doctrs.oneFree.signalAll();
					doctrs.mylock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(priority==0) { //se sono un paziente con codice bianco
				doctrs.mylock.lock();
				while(doctrs.getFreeDocs()==0) {
					try {
						doctrs.oneFree.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					index=doctrs.setDoc();
					doctrs.mylock.unlock();
					System.out.println("Paziente " + name + " / Indice scelto: " + index + " sto per essere gestito. Thread: " + Thread.currentThread().getName());
					Thread.sleep((long)(Math.random()*5000));
					doctrs.mylock.lock();
					doctrs.freeDoc_i(index);
					if (doctrs.mylock.hasWaiters(doctrs.allFree)) doctrs.allFree.signal();
					else if (doctrs.mylock.hasWaiters(doctrs.iFree)) doctrs.iFree.signalAll();
					else if (doctrs.mylock.hasWaiters(doctrs.oneFree)) doctrs.oneFree.signalAll();
					doctrs.mylock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep((long)(Math.random()*20000)); //tempo di attesa tra l'uscita e l'entrata
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
