//Raffaele Apetino - 549220

public class MainClass {
	
	public static void main(String[] args) {
		//Testato su ambiente MacOS
		//MainClass path numeroConsumatori
		//path: (String) stringa del path da controllare (es: /Users/home/utente/desktop)
		//numeroConsumatori: (int) numero di consumatori da instanziare
		String userPath = args[0];
		Integer numCons = Integer.parseInt(args[1]);
		ThreadSafeList theList = new ThreadSafeList();
		Producer prod = new Producer(userPath, theList);
		prod.start();
		if(numCons>0) {
			for (int i = 0; i<numCons; i++) {
				Consumer cons = new Consumer(theList);
				cons.start();
			}
		}
		else {
			System.out.println("Inserire un numero positivo e maggiore di zero");
		}
	}

}
