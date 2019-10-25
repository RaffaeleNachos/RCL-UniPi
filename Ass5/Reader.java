import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Reader extends Thread{
	
	JSONParser parser;
	Contatori counter;
	ThePool pool;
	
	public Reader(Contatori counter, ThePool pool) {
		parser = new JSONParser();
		this.counter = counter;
		this.pool = pool;
	}
	
	public void run() {
		try {
			//il reader parsa tutto il file
			JSONArray jsonOutArray = (JSONArray) parser.parse(getFileStringy());
			//itero sull'array
			Iterator<JSONObject> iterator = jsonOutArray.iterator();
			while (iterator.hasNext()) {
				Consumer cons = new Consumer((JSONArray)iterator.next().get("transactions"), counter);
				pool.executeTask(cons);
			}
			pool.closePool();
			//finchè tutti i thread del pool non hanno finito aspetto
			while(pool.executor.isTerminated()==false) {
				Thread.sleep(1000);
			}
			//a questo punto posso stampare le statistiche finali
			System.out.println(counter.getResults().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFileStringy() throws IOException {
		//creo un channel per la lettura del file
		FileChannel inChannel = FileChannel.open(Paths.get("banca.json"), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024);
        boolean stop = false;
        String tmp = "";
        while (!stop) {
        	int bytesRead = inChannel.read(buffer);
        	if (bytesRead==-1) {
        		stop=true;
        	}
        	//flippo il puntatore all'inizio per la lettura decodificata
        	buffer.flip();
            while (buffer.hasRemaining()) {
            	System.out.println("sono dentro");
            	tmp = tmp + StandardCharsets.UTF_8.decode(buffer).toString();
            }
            //riflippo per scrittura
            buffer.flip();
        }
        inChannel.close();
        return tmp;
	}
	
}
