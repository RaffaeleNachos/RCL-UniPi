import java.util.HashMap;
import java.util.concurrent.*;

public class Contatori {
	//struttura dati che si occupa della sincronizzazioni sulle variabili
	
	int nBonifico;
	int nAccredito;
	int nBollettino;
	int nF24;
	int nPagoBancomat;
	
	public Contatori() {
		nBonifico = 0;
		nAccredito = 0;
		nBollettino = 0;
		nF24 = 0;
		nPagoBancomat = 0;
	}

	public synchronized void addBonifico() {
		nBonifico++;
	}
	 
	public synchronized void addAccredito() {
		nAccredito++;
	}
	
	public synchronized void addBollettino() {
		nBollettino++;
	}
	
	public synchronized void addF24() {
		nF24++;
	}
	
	public synchronized void addPagoBancomat() {
		nPagoBancomat++;
	}
	
	public HashMap<String,Integer> getResults() {
		HashMap<String,Integer> hMap = new HashMap<>();
		hMap.put("Bonifico", nBonifico);
		hMap.put("Accredito", nAccredito);
		hMap.put("Bollettino", nBollettino);
		hMap.put("F24", nF24);
		hMap.put("PagoBancomat", nPagoBancomat);
		//System.out.print("Transazioni calcolate totali: ");
		//System.out.print(nBonifico+nAccredito+nBollettino+nF24+nPagoBancomat + "\n");
		return hMap;
	}
}
