import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class TimeClient {
	
	private static int PORT = 6789; 
	private static String HOST;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java TimeClient hostname");
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
		//controllo che l'indirizzo inserito sia un Multicast Address
		if (address.isMulticastAddress()){
			System.out.println("L'indirizzo inserito è un Multicast Address");
		} else {
			System.out.println("L'indirizzo inserito non è un Multicast Address, Server in chiusura");
			return;
		}
		//creo la MulticastSocket alla porta conosciuta
		MulticastSocket s = null;
		try {
			s = new MulticastSocket(PORT);
			//aggiungo al gruppo del Date-Multicast
			s.joinGroup(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytebuf = new byte[1024];
		DatagramPacket recPack = new DatagramPacket(bytebuf, bytebuf.length);
		for (int i = 0; i<10 ; i++) {
			try {
				s.receive(recPack);
				System.out.println(new String(recPack.getData(), 0,  recPack.getLength()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		s.close();
	}

}
