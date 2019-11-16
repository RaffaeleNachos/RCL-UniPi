import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class MainClass {
	
	public static int DEFAULT_PORT = 6789;

	public static void main(String[] args) {
		Selector selector = null;
		ServerSocket serverSocket;
		ServerSocketChannel serverChannel;
		try {
			//creo ServerSocketChannel per poterlo settare in modalità non bloccante
			//il SocketChannel è creato implicitamente
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			//ottengo la socket associata al channel
			serverSocket = serverChannel.socket();
			//bindo la socket del server sulla porta 6789 e come indirizzo usa una wildcard standard che mi indica tutti gli indirizzi IP della macchina
			InetSocketAddress address = new InetSocketAddress(DEFAULT_PORT);
			serverSocket.bind(address);
			//creo il mio selector e lo associo al channel
			selector = Selector.open(); 
			//per ora sto in ascolto solo sulla key che identifica la accept
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException ex) { 
			ex.printStackTrace();
		}
		while (true) { 
			try {
				//seleziono il canale disponibile (bloccante)
				//System.out.println(selector.keys());
				//System.out.println(selector.selectedKeys());
				System.out.println("Server | Aspetto sulla select");
				selector.select();
			} catch (IOException ex) {
				ex.printStackTrace(); 
				break;
			}
			//selectionkey entifica i tipi delle operazioni da gestire
			//readyKeys identifica i canali pronti
			Set <SelectionKey> readyKeys = selector.selectedKeys();
			Iterator <SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();
				//esplicita rimozione chiave dal selectedSet
				iterator.remove();
				try {
					if (key.isAcceptable()) {
						System.out.println("Server | pronta una chiave di accettazione");
						ServerSocketChannel server = (ServerSocketChannel) key.channel(); 
						SocketChannel client = server.accept(); 
						System.out.println("Connessione accettata verso: " + client); 
						client.configureBlocking(false);
						SelectionKey clientkey = client.register(selector, SelectionKey.OP_READ, null);
					}
					else if (key.isWritable()) {
						System.out.println("Server | pronta una chiave in scrittura");
						SocketChannel client = (SocketChannel) key.channel();
						String write = (String) key.attachment();
						ByteBuffer end = ByteBuffer.wrap(write.getBytes());
						int bWrite = client.write(end);
						if (bWrite == write.length()) {
							write = "echoed by Server: ";
							key.attach(write);
							key.interestOps(SelectionKey.OP_READ);
							System.out.println("Server | key impostata su read");
						}
						else if (bWrite == -1) {
							key.cancel();
							System.out.println("Server | socket chiusa dal client");
						}
						else {
							System.out.println("Server | scrivo: " + bWrite + " bytes");
							end.flip();
							key.attach(StandardCharsets.UTF_8.decode(end).toString());
						}
					}
					else if (key.isReadable()) {
						System.out.println("Server | pronta una chiave in lettura");
						SocketChannel client = (SocketChannel) key.channel();
						String read = (String) key.attachment();
						if (read == null) read = "echoed by Server: ";
						ByteBuffer input = ByteBuffer.allocate(8);
						input.clear();
						int bRead = client.read(input);
						if (bRead == 8 /*e nella socket c'è ancora qualcosa*/){
							System.out.println("Server | leggo: " + bRead + " bytes");
							input.flip();
							read = read + StandardCharsets.UTF_8.decode(input).toString();;
							key.attach(read);
						}
						else if (bRead < 8 /*e nella socket non c'è più nulla*/ ) {
							System.out.println("Server | leggo: " + bRead + " bytes");
							input.flip();
							read = read + StandardCharsets.UTF_8.decode(input).toString();
							key.attach(read);
							key.interestOps(SelectionKey.OP_WRITE);
							System.out.println("Server | key impostata su write");
						}
						else if (bRead == -1) {
							System.out.println("Server | socket chiusa dal client");
							key.cancel();
						}
					}
				} catch (IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
