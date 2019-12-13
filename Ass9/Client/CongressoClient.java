import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CongressoClient {
	
	public static String name;

	public static void main(String[] args) {
		//Testato su ambiente MacOS
		//comando: java CongressoClient nomeSpeaker
		//nomeSpeaker: stringa che identifica il nome dello speaker interessato al congresso
		
		switch (args.length) {
		case 1:
			name = args[0];
			break;
		default:
			System.err.println("Usage: java CongressoClient nomeSpeaker");
			return;
		}
		
		Registry r;
		//RemoteObject: spazio di indirizzamento diverso, gira su un'altra JVM
		Remote remoteObj;
		//ho bisogno di una interfaccia per chiamare correttamente i metodi
		CongressoInterface congressoGest;
		//scanner per leggere in input dal client
		Scanner scan = new Scanner(System.in);
		try {
			//getRegistry(String host, int port) nel caso di host remoti
			r = LocateRegistry.getRegistry(6789);
			//eseguo la lookup nel registry e mi restituisce un oggetto remoto
			remoteObj = r.lookup("CONGRESSO-GEST");
			congressoGest = (CongressoInterface) remoteObj;
			
			System.out.println("Bentornato " + name + "!");
			System.out.println("---------- GESTIONE CONGRESSO ----------");
			System.out.println("L'attuale congresso ha una durata di " + congressoGest.getnumDays() + " giorni");
			System.out.println("Ogni giornata è composta da " + congressoGest.getnumSess() + " sessioni");
			System.out.println("In ogni sessione ci sono " + congressoGest.getnumSlots() + " slots");
			System.out.println("Che operazione vuoi eseguire?");
			int op = -1;
			while (op != 0) {
				System.out.println("Inserisci 0 se vuoi terminare il client");
				System.out.println("Inserisci 1 se vuoi registrarti in una sessione");
				System.out.println("Inserisci 2 se vuoi stampare l'intero programma");
				System.out.println("Inserisci 3 se vuoi stampare il programma di un preciso giorno");
				//leggo op int da std-in
				op = scan.nextInt();
				//variabili in cui salvo la lettura da std-in
				int giorno;
				int sessione;
				switch (op) {
				case 0:
					System.out.println("Chiusura Client...");
					break;
				case 1:
					System.out.println("In quale giorno vuoi registrarti? (inserire un valore numerico 1,2,3...7)");
					giorno = scan.nextInt() - 1;
					System.out.println("In quale sessione vuoi registrarti? (inserire un valore numerico 1,2,3...N)");
					sessione = scan.nextInt() - 1;
					try {
						if (congressoGest.signUpSpeaker(name, giorno, sessione)) {
							System.out.println("Iscrizione eseguita con successo.");
						}
						else {
							System.err.println("Ooops, questo non doveva succedere!");
						}
					} catch (InvalidParameterException e) {
						System.err.println(e.getMessage());
					} catch (NullPointerException e) {
						System.err.println("Inserisci un nome valido");
					} catch (SessionFullException e) {
						System.err.println("La sessione da te scelta è piena, ci scusiamo.");
					}
					break;
				case 2:
					ArrayList<ArrayList<ArrayList<String>>> myCongresso = congressoGest.getCongress();
					for (int i = 0; i < myCongresso.size(); i++) {
						System.out.println("Congress Day: " + (i+1));
						for (int j = 0; j < myCongresso.get(i).size(); j++) {
							System.out.print("Session " + (j+1) + ": \t");
							System.out.println(myCongresso.get(i).get(j));
							/*for (int k = 0; k < myCongresso.get(i).get(j).size(); k++) {
								System.out.print(myCongresso.get(i).get(j).get(k) + " ");
							}
							System.out.print("\n");*/
						}
						System.out.print("\n");
					}
					break;
				case 3:
					System.out.println("A quale giorno sei interessato? (inserire un valore numerico 1,2,3,...,N)");
					giorno = scan.nextInt() - 1;
					try {
						ArrayList<ArrayList<String>> myGiorno = congressoGest.getDayCongress(giorno);
						System.out.println("Congress Day: " + (giorno+1));
						for (int j = 0; j < myGiorno.size(); j++) {
							System.out.print("Session " + (j+1) + ": \t" );
							System.out.println(myGiorno.get(j));
							/*for (int k = 0; k < myGiorno.get(j).size(); k++) {
								System.out.print(myGiorno.get(j).get(k) + " ");
							}
							System.out.print("\n");*/
						}
						System.out.print("\n");
					} catch (InvalidParameterException e) {
						System.err.println(e.getMessage());
					}
					break;
				default:
					System.out.println("Inserisci il numero corretto di una operazione!");
					break;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.err.println("Hai inserito un valore scorretto, inserisci un valore numerico!");
		}
		
	}
	
}
