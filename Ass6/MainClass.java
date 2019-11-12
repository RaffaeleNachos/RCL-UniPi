//Raffaele Apetino - Matricola 549220

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainClass {
	
	public static void main(String[] args) {
		//Testato su ambiente MacOS
		//Utilizzo: lanciare java MainClass da terminale e successivamente
		//aprire un browser web. Richiedere quindi i file digitando:
		//localhost:6789/filename
		
		//creo listening socket del server
		try (ServerSocket server = new ServerSocket(6789)) {
			while (true) {
				//creazione socket comunicazione client
				try (Socket connection = server.accept()) {
					OutputStream out = connection.getOutputStream();
					//uso un BufferedReader perche' andro' a leggere un header HTTP
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					//leggo solamente l'header
					String header = reader.readLine();
					//il mio server gestisce solamente richieste GET 
					if (header.startsWith("GET")){
						//tokenizzo l'header e prendo il secondo elemento della stringa, il quale indica il file richiesto
						String [] token;
						token = header.split("\\s");
						String reqPath = "." + token[1];
						//creo il path e uso metodi statici di Files per ottenere le informazioni su di esso
						Path pt = Paths.get(reqPath);
						if (Files.exists(pt) && !Files.isDirectory(pt)) {
							//probeContentType restituisce informazioni sul file in base standard MIME
							positiveResponse(reqPath, out, Files.probeContentType(pt), Files.size(pt));
						} else {
							negativeResponse(out);
						}
					}
					else {
						//risposta per le richieste non gestite come POST, DELETE ecc...
						notResponse(out);
					}
					//chiudo la connessione rendendola non persistente
					//cioè ad ogni richiesta viene creata una nuova socket
					connection.close();
				} catch (IOException ex) { 
					ex.printStackTrace();
				} 
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		                                                                                                                                                                                                
	}
	

	public static void positiveResponse(String filename, OutputStream out, String ext, long dimension) {
		try {
			String risp = "HTTP/1.1 200 OK\r\n";
			risp = risp + "Content-Type: " + ext + "\r\n";
			risp = risp + "Content-Lenght: "+ dimension + "\r\n";
			risp = risp + "\r\n";
			//scrivo header
			out.write(risp.getBytes());
			File file = new File(filename);
			//scrivo file
			Files.copy(file.toPath(), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void negativeResponse(OutputStream out) {
		try {
			String risp = "HTTP/1.1 404 Not Found\r\n";
			risp = risp + "Content-Type: text/html\r\n";
			risp = risp + "\r\n";
			out.write(risp.getBytes());
			risp = "<center><h1>Aaaarg! I don't have this file, sorry!</h1></center>";
			out.write(risp.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void notResponse(OutputStream out) {
		try {
			String risp = "HTTP/1.1 501 Not Implemented\r\n";
			risp = risp + "Content-Type: text/html\r\n";
			risp = risp + "\r\n";
			out.write(risp.getBytes());
			risp = "<center><h1>Ops, I haven't programmed that part yet!</h1></center>";
			out.write(risp.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




