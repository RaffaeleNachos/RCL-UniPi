import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int nRossi = 5;
		int nGialli = 10 ;
		int nBianchi = 15;
		
		ArrayList<Paziente> Pazienti = new ArrayList<>();
		Dottori docs = new Dottori();
		
		//creo tutti i pazienti
		int i;
		for(i = 0; i<nRossi; i++) {
			Pazienti.add(new Paziente("Rosso"+i, 2, (int) (Math.random()*5), docs));
		}
		for(i = 0; i<nGialli; i++) {
			Pazienti.add(new Paziente("Giallo"+i, 1, (int) (Math.random()*5), docs));
		}
		for(i = 0; i<nBianchi; i++) {
			Pazienti.add(new Paziente("Bianco"+i, 0, (int) (Math.random()*5), docs));
		}
		
		//faccio partire tutti i thread "insieme"
		for(i = 0; i<Pazienti.size(); i++) {
			Pazienti.get(i).start();
		}
	}
}
