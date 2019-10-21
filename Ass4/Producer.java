//Raffaele Apetino - 549220

import java.io.File;

public class Producer extends Thread{
	
	String myPath;
	ThreadSafeList myList; 
	
	public Producer(String myPath, ThreadSafeList myList) {
		this.myList = myList;
		this.myPath = myPath;
	}
	
	public void run() {
		recursiveDir(myPath);
		//quando ho terminato la visita ricorsiva inserisco "end" nella lista
		myList.myLock.lock();
		myList.addDir("end");
		myList.stop(); //setta la variabile hasFinished a "true"
		myList.notEmpity.signalAll();
		myList.myLock.unlock();
	}
	
	public void recursiveDir(String path) {
		File file = new File(path);
        File[] list = file.listFiles();
        if (list != null) {
        	for (File f : list) {
        		if (f.isDirectory()) {
        			myList.myLock.lock();
        			myList.addDir(f.getAbsolutePath());
        			myList.notEmpity.signalAll(); //risveglio i consumatori
        			myList.myLock.unlock();
        			System.out.println("Ho aggiunto la directory:" + f.getAbsoluteFile());
        			recursiveDir(f.getAbsolutePath());
        		}
        	}
        }
	}

}
