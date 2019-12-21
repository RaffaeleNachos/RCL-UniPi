import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServer {
	
	private static int PORT = 6789; 
	//indirizzo classe D dedicati
	private static String HOST;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java TimeServer hostname");
			return;
		} else {
			HOST = args[0];
		}
		InetAddress address = null;
		try {
			address = InetAddress.getByName(HOST);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//controllo che l'indirizzo inserito sia un MulticastAddress
		if (address.isMulticastAddress()){
			System.out.println("L'indirizzo inserito è un Multicast Address");
		} else {
			System.out.println("L'indirizzo inserito non è un Multicast Address, Server in chiusura");
			return;
		}
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now;
		while(true) {
			now = LocalDateTime.now();
			//associo la formattazione scelta precedentemente
			String message = now.format(myFormat);
			try {
				s.send(new DatagramPacket(message.getBytes(), message.length(), address, PORT));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(message + " > Package Sent");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
