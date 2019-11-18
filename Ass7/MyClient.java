import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MyClient {
	
	private static int DIM_BUFFER = 1024;
	
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
			ByteBuffer byteBuffer;
			String strInvio;
		    while(true) {
		    	System.out.println("Client | modalità scrittura");
		    	System.out.println("Please enter a string:");
		    	strInvio = scanner.nextLine();
		    	byteBuffer = ByteBuffer.wrap(strInvio.getBytes());
		    	while (byteBuffer.hasRemaining()) {
		    		System.out.println("Client | scrivo: " + socketChannel.write(byteBuffer) + " bytes");
		    	}
		    	byteBuffer.clear();
		    	byteBuffer.flip();
		    	System.out.println("Client | modalità lettura");
		    	byteBuffer = ByteBuffer.allocate(DIM_BUFFER);
		    	boolean stop = false;
		        String tmp = "";
		        while (!stop) {
		        	byteBuffer.clear();
		        	int bytesRead = socketChannel.read(byteBuffer);
		        	byteBuffer.flip();
		        	tmp = tmp + StandardCharsets.UTF_8.decode(byteBuffer).toString();
		        	byteBuffer.flip();
	        		System.out.println("Client | leggo: " + bytesRead + " bytes");
	        		if (bytesRead < DIM_BUFFER) {
		        		stop=true;
		        	}
		        }
		    	byteBuffer.flip();
		    	System.out.println(tmp);
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
