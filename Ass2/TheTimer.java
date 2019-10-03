/**
 * @author Raffaele Apetino - N.M. 549220
 *
 */

public class TheTimer extends Thread{
	long time;
	
	public TheTimer(long time) {
		this.time = time;
	}
	
	public void run() {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
