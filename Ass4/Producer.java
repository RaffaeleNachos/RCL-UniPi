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
		myList.addDir("end");
		myList.stop();
	}
	
	public void recursiveDir(String path) {
		File file = new File(path);
        File[] list = file.listFiles();
        if (list == null) return;
        for (File f : list) {
            if (f.isDirectory()) {
            	myList.addDir(f.getAbsolutePath());
                System.out.println("Ho aggiunto la directory:" + f.getAbsoluteFile());
                recursiveDir(f.getAbsolutePath());
            }
        }
	}

}
