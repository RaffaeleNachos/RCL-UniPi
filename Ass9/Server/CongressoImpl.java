import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.security.InvalidParameterException;
import java.util.ArrayList;

//estende RemoteServer così da ereditare i metodi equals, toString ecc..
public class CongressoImpl extends RemoteServer implements CongressoInterface{
	
	private static ArrayList<ArrayList<ArrayList<String>>> congresso;
	//numero sessioni
	private int numSess;
	//numero giorni
	private int numG;
	//numero slots
	private int numSlots;
	

	public CongressoImpl(int numSessioni, int numSlots, int giorni) {
		this.numSess = numSessioni;
		this.numSlots = numSlots;
		this.numG = giorni;
		//istanziazione della struttura dati utilizzata
		congresso = new ArrayList<>(giorni);
		for (int i = 0; i < giorni; i++) {
			congresso.add(i, new ArrayList<>(numSessioni));
		}
		for (int i = 0; i < giorni; i++) {
			for(int j = 0; j < numSessioni; j++) {
				congresso.get(i).add(j, new ArrayList<>(numSlots));
			}
		}
	}

	@Override
	public synchronized boolean signUpSpeaker(String name, int giorno, int numS) throws NullPointerException, RemoteException, SessionFullException, InvalidParameterException{
		if (name == null) throw new NullPointerException();
		if (giorno < numG && giorno >= 0) {
			if (numS >= 0 && numS < numSess) {
				if (congresso.get(giorno).get(numS).size() < numSlots) {
					return congresso.get(giorno).get(numS).add(name);
				} 
				else {
					//se la dimensione dell'array contenente le stringhe è uguale al numero di slot richiesti lancia eccez.
					throw new SessionFullException();
				}
			}
			else {
				throw new InvalidParameterException("Numero sessione errato");
			}
		} 
		else {
			throw new InvalidParameterException("Numero giorno errato");
		}
	}

	@Override
	public ArrayList<ArrayList<ArrayList<String>>> getCongress() throws RemoteException {
		return congresso;
	}

	@Override
	public ArrayList<ArrayList<String>> getDayCongress(int day) throws RemoteException, InvalidParameterException {
		if (day >= numG || day < 0) throw new InvalidParameterException("Numero giorno errato");
		else {
			return congresso.get(day);
		}
	}

	@Override
	public int getnumDays() throws RemoteException{
		return numG;
	}

	@Override
	public int getnumSess() throws RemoteException{
		return numSess;
	}

	@Override
	public int getnumSlots() throws RemoteException{
		return numSlots;
	}

}
