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
	
	public Reader() {
		parser = new JSONParser();
	}
	
	public void run() {
		try {
			JSONArray jsonOutArray = (JSONArray) parser.parse(getFileStringy());
			Iterator<JSONObject> iterator = jsonOutArray.iterator();
			while (iterator.hasNext()) { 
				System.out.println(iterator.next().get("transactions"));
			}
			//System.out.println(jsonOutArray.toJSONString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFileStringy() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("banca.json"), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024);
        boolean stop=false;
        while (!stop) {
        	int bytesRead = inChannel.read(buffer);
        	if (bytesRead==-1) {
        		stop=true;
        		}
        }
        inChannel.close();
        buffer.flip();
        String tmp = StandardCharsets.UTF_8.decode(buffer).toString();
        return tmp;
	}
	
}
