
public class MainClass {
	
	public static void main(String[] args) {
		ThreadSafeList theList = new ThreadSafeList();
		Producer prod = new Producer(args[0], theList);
		prod.start();
		for (int i = 0; i<Integer.parseInt(args[1]); i++) {
			Consumer cons = new Consumer(theList);
			cons.start();
		}
	}

}
