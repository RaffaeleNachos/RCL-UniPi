import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

public class PingServer {
	
	private static int servPORT;
	private static int SEED = 11223344;

	public static void main(String[] args) {
		switch (args.length) {
			case 1:
				try {
					servPORT = Integer.parseInt(args[0]);
				} catch (Exception e) {
					System.err.println("Server | Errore -args 1");
					System.err.println("Server | Client shutdown");
					return;
				}
				break;
			case 2:
				try {
					servPORT = Integer.parseInt(args[0]);
				} catch (Exception e) {
					System.err.println("Server | Errore -args 1");
					System.err.println("Server | Client shutdown");
					return;
				}
				try {
					SEED = Integer.parseInt(args[1]);
				} catch (Exception e) {
					System.err.println("Server | Errore -args 2");
					System.err.println("Server | Client shutdown");
					return;
				}
				break;
			default:
				System.out.println("Usage: java PingServer port [seed]");
				return;
		}
		Random rand = new Random(SEED);
		try {
			DatagramSocket sSocket = null;
			try {
				sSocket = new DatagramSocket(servPORT);
			} catch (SocketException e) {
				System.err.println("Server | Errore -args 1");
				System.err.println("Server | Server shutdown");
				return;
			}
			System.out.println("Server | dim buf ricezione " + sSocket.getReceiveBufferSize()); 
			System.out.println("Server | dim buf invio " + sSocket.getSendBufferSize());
			byte[] bytebuf = new byte[1024];
			DatagramPacket recPack = new DatagramPacket(bytebuf, bytebuf.length);
			while(true) {
				sSocket.receive(recPack);
				System.out.print(recPack.getAddress() + ":" + recPack.getPort() + "> ");
				String received = new String(recPack.getData(), 0, recPack.getLength());
				System.out.print(received);
				if (rand.nextInt(100)<25) {
					System.out.println(" ACTION: not sent");
				}
				else {
					long delay = rand.nextInt(300);
					System.out.println(" ACTION: delayed " + delay + " ms");
					Thread.sleep(delay);
					String tmp = received;
					byte[] bytearr = tmp.getBytes();
					DatagramPacket mypack = new DatagramPacket(bytearr, bytearr.length, recPack.getAddress(), recPack.getPort());
					sSocket.send(mypack);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
