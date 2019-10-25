import java.io.IOException;

public class MainClass {

	public static void main(String[] args) {
		//Testato su ambiente MacOS
		//MainClass numeroClienti numeroTransazioni
		//numeroClienti: (int) numero di clienti da creare nel file json
		//numeroTransazioni: (int) numero di transazioni da creare per ogni cliente
		
		jsonCreator test = new jsonCreator(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		try {
			test.createFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//a questo punto il file è creato e posso instanziare i contatori e il threadPool
		Contatori counter = new Contatori();
		ThePool pool = new ThePool();
		//faccio partire il produttore che si occuperà di parsare il json e di iviare ai thread l'array di transazioni
		Reader prod = new Reader(counter, pool);
		prod.start();
	}
}
