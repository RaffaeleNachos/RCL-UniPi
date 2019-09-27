import java.util.Locale;
import java.util.Scanner; 

/**
 * @author Raffaele Apetino - N.M. 549220
 *
 */

public class Main {

	public static void main(String[] args) {
		//Scanner che prende in input da terminale prima accuratezza poi time-out
		Scanner input = new Scanner(System.in).useLocale(Locale.US);
		System.out.println("Inserisci Accuratezza:");
		double accuracy = input.nextDouble();
		System.out.println("Inserisci Time-Out:");
		long time = input.nextLong();
		input.close();
		
		//creo nuova istanza per il calcolo del pi, piCalc estende Thread
		PiCalc newpi = new PiCalc(accuracy);
		newpi.start();
		try {
			newpi.join(time); //metto il thread main in stop
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (newpi.isAlive()) newpi.interrupt();
	}
}
