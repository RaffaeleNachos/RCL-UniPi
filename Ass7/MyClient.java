import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class MyClient {
	
	public static void main(String[] args) {
		//SLIDE 19 per modalità non bloccante
		SocketAddress socket = new InetSocketAddress(6789);
		//il client deve esplicitamente creare il SocketChannel
		SocketChannel socketChannel;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.connect(socket);
			System.out.println("Collegato a: " + socketChannel);
			Scanner scanner = new Scanner(System.in);
		    while(true) {
		    	System.out.println("Client | modalità scrittura");
		    	System.out.println("Enter a string:");
		    	String strInvio = scanner.nextLine();
		    	ByteBuffer byteBuffer = ByteBuffer.wrap(strInvio.getBytes());
		    	while (byteBuffer.hasRemaining()) {
		    		System.out.println("Client | scrivo: " + socketChannel.write(byteBuffer) + " bytes");
		    	}
		    	System.out.println("Client | modalità lettura");
		    	ByteBuffer response = ByteBuffer.allocate(1024);
		    	System.out.println("Client | leggo: " + socketChannel.read(response) + " bytes");
		    	System.out.println(new String(response.array()));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
