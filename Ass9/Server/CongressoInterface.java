import java.rmi.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public interface CongressoInterface extends Remote{
	
	// REQUIRES: name != null && giorno < numeroGiorni && giorno >= 0 && session >= 0 && session < numeroSessioni
	// THROWS: 	se name = null lancia NullPointerException
	//			nel caso di problemi con RMI, lancia RemoteException
	//			se giorno < 0 || giorno >= numeroGiorni || session < 0 || session >= numeroSessioni lancia InvalidParameterException
	// MODIFIES: this
	// EFFECTS: aggiunge lo speaker nel giorno richiesto, nella sessione richiesta, nel primo slot libero
	// RETURNS: true se è stato aggiunto al giorno, false se non è stato possibile (finiti slot liberi)
	boolean signUpSpeaker(String name, int giorno, int session) throws NullPointerException, RemoteException, SessionFullException, InvalidParameterException;
	
	// REQUIRES: -
	// THROWS: nel caso di problemi con RMI, lancia RemoteException
	// MODIFIES: - 
	// EFFECTS: - 
	// RETURNS: restituisce l'intero scheduling del congresso
	ArrayList<ArrayList<ArrayList<String>>> getCongress() throws RemoteException;
	
	// REQUIRES: day < numeroGiorni && day >= 0
	// THROWS: 	nel caso di problemi con RMI, lancia RemoteException
	//			day >= numeroGiorni && day < 0 lancia una InvalidParameterException
	// MODIFIES: - 
	// EFFECTS: - 
	// RETURNS: restituisce lo scheduling di uno specifico giorno 
	ArrayList<ArrayList<String>> getDayCongress(int day) throws RemoteException , InvalidParameterException;
	
	// REQUIRES: -
	// THROWS: nel caso di problemi con RMI, lancia RemoteException
	// MODIFIES: - 
	// EFFECTS: - 
	// RETURNS: restituisce l'intero che rappresenta la durata del congresso espressa in numero di giorni
	int getnumDays() throws RemoteException;
	
	// REQUIRES: -
	// THROWS: nel caso di problemi con RMI, lancia RemoteException
	// MODIFIES: - 
	// EFFECTS: - 
	// RETURNS: restituisce l'intero che rappresenta il numero di sessioni in un giorno
	int getnumSess() throws RemoteException;
	
	// REQUIRES: -
	// THROWS: nel caso di problemi con RMI, lancia RemoteException
	// MODIFIES: - 
	// EFFECTS: - 
	// RETURNS: restituisce l'intero che rappresenta il numero di slot contenuti in una sessione
	int getnumSlots() throws RemoteException;
}
