import java.util.concurrent.*;

public class Main {

	public static void main(String[] args) throws Exception{
		LinkedBlockingQueue<Task> externalQueue = new LinkedBlockingQueue<Task>(500);
		int nSportelli = 4;
		int kCoda = 10;
		int taskCounter = 0;
		
		PostOffice posteItaliane = new PostOffice(nSportelli, kCoda);
		while(true){
			if (externalQueue.size()<500) {
				Task task = new Task("Task "+ taskCounter);
				taskCounter++;
				externalQueue.add(task);
			}
			while(posteItaliane.freeQueue()<10 && externalQueue.isEmpty() == false) {
				try{
					posteItaliane.executeTask(externalQueue.poll());
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println("CodaPiena");
				}
			}
		}
		//posteItaliane.closePostOffice();
	}

}
