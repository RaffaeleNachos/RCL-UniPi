import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CongressoServer {

	public static void main(String[] args) {
		//creazione istanza del congresso con parametri numSessioni, numSlots e numGiorni
		CongressoImpl tedx = new CongressoImpl(12, 5, 3);
		try {
			//esportazione oggetto remoto
			CongressoInterface stub = (CongressoInterface) UnicastRemoteObject.exportObject(tedx, 0);
			//creazione del registro che fa da bootstrap, esso gestirà le richieste che arrivano sulla porta 6789 del server
			Registry r = LocateRegistry.createRegistry(6789);
			//registazione di stub nel registry, necessario per poi essere reperito da un altro host
			//rebind altrimenti eccezione
			r.rebind("CONGRESSO-GEST", stub);
		} catch (RemoteException e) {
			System.err.println("Qualcosa è andato storto nella procedura RMI");
			e.printStackTrace();
		}
		System.out.println("Server pronto, Gestore congresso inizializzato");
	}

}
