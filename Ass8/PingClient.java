import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class PingClient {
	
	//porta, name del server e InetAddress calcolato
	private static int servPORT;
	private static String servNAME;
	private static InetAddress servADDR;
	private static int packageSent = 0;
	private static int packageRec = 0;
	private static long maxRTT = 0;
	private static long minRTT = Long.MAX_VALUE;
	private static float avgRTT = 0;
	

	public static void main(String[] args) {
		//Testato su ambiente MacOS
		//comando: java PingClient nameServer portaServer
		//nameServer: stringa che identifica il server
		//portaServer: intero che identifica la porta del server
		switch (args.length) {
			case 2:
				try {
					servNAME = args[0];
				} catch (Exception e) {
					System.err.println("Client | Errore -args 1");
					System.err.println("Client | Client shutdown");
					return;
				}
				try {
					servPORT = Integer.parseInt(args[1]);
				} catch (Exception e) {
					System.err.println("Client | Errore -args 2");
					System.err.println("Client | Client shutdown");
					return;
				}
				break;
			default:
				System.out.println("Usage: java PingClient hostname port");
				return;
		}
		try {
			servADDR = InetAddress.getByName(servNAME);
		} catch (UnknownHostException e) {
			System.err.println("Client | Errore -args 1");
			System.err.println("Client | Client shutdown");
			return;
		}
		DatagramSocket cSocket = null;
		DatagramPacket mypack = null;
		try {
			cSocket = new DatagramSocket();
			//setto il timeout a 2 secondi
			cSocket.setSoTimeout(2000);
			System.out.println("Client | porta scelta: " + cSocket.getLocalPort());
		} catch (SocketException e) {
			System.err.println("Client | Client shutdown");
			e.printStackTrace();
			return;
		}
		try {
			for (int i = 0; i < 10; i++) {
				//invio del PING al server
				long currentTime = System.currentTimeMillis();
				String tmp = "PING " + i + " " + currentTime;
				byte[] bytearr = tmp.getBytes();
				mypack = new DatagramPacket(bytearr, bytearr.length, servADDR, servPORT);
				try {
					cSocket.send(mypack);
				} catch (PortUnreachableException e) {
					System.err.println("Client | Errore -args 2");
					System.err.println("Client | Client shutdown");
					cSocket.close();
					return;
				}
				packageSent++;
				//ricezione del PING da parte del server
				//posso usare lo stesso packet?
				try {
					cSocket.receive(mypack);
				} catch (SocketTimeoutException e) {
					System.out.println(tmp + " RTT: *");
					continue;
				}
				packageRec++;
				String received = new String(mypack.getData(), 0, mypack.getLength());
				long delay = System.currentTimeMillis() - currentTime;
				if (delay > maxRTT) {
					maxRTT = delay;
					avgRTT = avgRTT + delay;
				}
				else if (delay < minRTT) {
					minRTT = delay;
					avgRTT = avgRTT + delay;
				}
				System.out.println(received + " RTT: " + delay + " ms");
			}
		} catch (IOException e) {
			System.err.println("Client | Client shutdown");
			e.printStackTrace();
			cSocket.close();
			return;
		}
		//riassunto finale
		System.out.println("---------- PING STATISTICS ----------");
		System.out.println("Numero pacchetti trasmessi: " + packageSent);
		System.out.println("Numero pacchetti ricevuti: " + packageRec);
		//packagesent : 100 = packagelost (sent-rec) : x
		System.out.println("Percentuale di perdita dei pacchetti: " + (((packageSent-packageRec)*100)/packageSent) + "%");
		System.out.println("min RTT: " + minRTT + " ms");
		System.out.println("max RTT: " + maxRTT + " ms");
		System.out.printf("avg RTT: %.2f ms \n", avgRTT/packageRec);
		
		cSocket.close();
	}
	
}
