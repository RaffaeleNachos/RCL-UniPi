import java.io.File;

public class Consumer extends Thread{
	
	ThreadSafeList myList;
	
	public Consumer(ThreadSafeList myList) {
		this.myList = myList;		
	}
	
	public void run() {
		while (myList.getStatus()==false) {
			myList.myLock.lock();
			while(myList.dirList.isEmpty()) {
				try {
					myList.notEmpity.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (myList.peekDir().equals("end")) {
				System.out.println("ho trovato end esco " + Thread.currentThread().getName());
				return;
			}
			File file = new File(myList.getDir());
			myList.myLock.unlock();
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
