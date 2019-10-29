//Raffaele Apetino 549220

import java.io.IOException;

public class MainClass {

	public static void main(String[] args) {
		//Testato su ambiente MacOS.
		//Libreria JSON utilizzata: json-simple-1.1.1.jar
		//MainClass numeroClienti numeroTransazioni numeroThread
		//numeroClienti: (int) numero di clienti da creare nel file json
		//numeroTransazioni: (int) numero di transazioni da creare per ogni cliente
		//numeroThread: (int) numero di thread da attivare
		
		if(Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[2]) > 0){
			jsonCreator test = new jsonCreator(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
			try {
				test.createFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//a questo punto il file è creato e posso instanziare i contatori e il threadPool
			Contatori counter = new Contatori();
			ThePool pool = new ThePool(Integer.parseInt(args[2]));
			//faccio partire il produttore che si occuperà di parsare il json e di iviare ai thread l'array di transazioni
			Reader prod = new Reader(counter, pool);
			prod.start();
		} else {
			System.out.println("Inserire tre valori maggiori di zero");
		}
	}
}
