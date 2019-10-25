import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Consumer implements Runnable{
	
	JSONArray arr;
	Contatori counter;
	
	public Consumer(JSONArray arr, Contatori counter) {
		this.arr=arr;
		this.counter=counter;
	}
	
	public void run() {
		//itero sull'array di transazioni
		Iterator<JSONObject> iterator = arr.iterator();
		while (iterator.hasNext()) {
			String causale = (String) iterator.next().get("causale");
			System.out.println(Thread.currentThread().getName() + " " + causale);
			switch(causale) {
			case "Bonifico" : 
				counter.addBonifico();
				break;
			case "Accredito" :
				counter.addAccredito();
				break;
			case "Bollettino" : 
				counter.addBollettino();
				break;
			case "F24" : 
				counter.addF24();
				break;
			case "PagoBancomat" :
				counter.addPagoBancomat();
				break;
			}
		}
	}

}
