import java.util.concurrent.*;

/**
 * @author Raffaele Apetino - N.M. 549220
 *
 */

public class Main {

	public static void main(String[] args){
		int nSportelli = 4;
		int kCoda = 10;
		int kCodaEsterna = 50;
		int taskCounter = 0;
		TheTimer myTimer = new TheTimer(100); //tempo di apertura dell'ufficio postale
		LinkedBlockingQueue<Task> externalQueue = new LinkedBlockingQueue<Task>(kCodaEsterna);
		
		PostOffice posteItaliane = new PostOffice(nSportelli, kCoda);
		myTimer.start();
		while(myTimer.isAlive()){
			if (externalQueue.size()<kCodaEsterna) {
				Task task = new Task("Task "+ taskCounter);
				taskCounter++;
				try {
					externalQueue.put(task);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(task.name + " inserito nella coda esterna");
			}
			if(posteItaliane.freeQueue()<10 && externalQueue.isEmpty() == false) {
				try{
					posteItaliane.executeTask(externalQueue.take());
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println("CodaPiena");
				}
			}
		}
		while(externalQueue.isEmpty() == false) {
			if (posteItaliane.freeQueue()<10) {
				try {
					posteItaliane.executeTask(externalQueue.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		posteItaliane.closePostOffice();
	}
}
