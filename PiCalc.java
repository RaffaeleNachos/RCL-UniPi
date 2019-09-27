/**
 * @author Raffaele Apetino - N.M. 549220
 *
 */

public class PiCalc extends Thread{
	
	private double mypi;
	private double accuracy;
	
	public PiCalc(double accuracy) {
		mypi=0.0;
		this.accuracy=accuracy;
	}
	
	public void run() {
		int counter=0;
		double dis=1;
		while(Math.abs(Math.PI - mypi) >= accuracy && this.isInterrupted() == false) {
			if(counter%2==0) {
				mypi=mypi+(4/dis);
			}
			else {
				mypi=mypi-(4/dis);
			}
			counter++;
			System.out.println("Ho Calcolato:" + mypi);
			dis=dis+2;
		}
		System.out.println("PI Finale:" + mypi);
		System.out.println("PI Reale:" + Math.PI);
	}
}
