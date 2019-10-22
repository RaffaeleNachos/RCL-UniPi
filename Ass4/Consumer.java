//Raffaele Apetino - 549220

import java.io.File;

public class Consumer extends Thread{
	
	ThreadSafeList myList;
	
	public Consumer(ThreadSafeList myList) {
		this.myList = myList;		
	}
	
	public void run() {
		while (myList.getStatus()==false) { //finchè il produttore non ha terminato
			myList.myLock.lock();
			while(myList.dirList.isEmpty()) { //se la lista è vuota aspetto
				try {
					myList.notEmpity.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (myList.peekDir().equals("end")) { 
				//se l'elemento della lista è la stringa "end" non ci sono più directory da gestire quindi termino
				System.out.println("ho trovato end esco " + Thread.currentThread().getName());
				myList.myLock.unlock();
				return;
			}
			//altrimenti prendo la directory e rilascio la lock
			File file = new File(myList.getDir());
			myList.myLock.unlock();
			//gestisco il print degli elementi all'interno della dir.
	        File[] list = file.listFiles();
	        for (File f : list) {
	            if (f.isFile()) {
	                System.out.println("File: " + f.getName() + " / Gestito da thread: " + Thread.currentThread().getName());
	            }
	        }
		}
		System.out.println(Thread.currentThread().getName() + " ho terminato");
	}

}
