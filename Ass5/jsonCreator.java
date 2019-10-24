import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.simple.*;

public class jsonCreator {
	
	public jsonCreator() throws IOException {
		JSONArray databaseBanca = new JSONArray();
		for (int numClienti = 0; numClienti<50; numClienti++) {
			databaseBanca.add(createAccount(numClienti));
		}
		//ByteBuffer buf = ByteBuffer.allocateDirect(databaseBanca.toJSONString().getBytes().length); 
		ByteBuffer buf = ByteBuffer.wrap(databaseBanca.toJSONString().getBytes("UTF-8"));
		//System.out.println(Arrays.toString(buf.array()));
		try {
			Files.deleteIfExists(Paths.get("./banca.json"));
			Files.createFile(Paths.get("./banca.json"));
		} catch(Exception e) {
			System.err.print("file già presente, elimino e creo nuovo file \n");
		}
		FileChannel outChannel = FileChannel.open(Paths.get("banca.json"),StandardOpenOption.WRITE);
		
		while(buf.hasRemaining()) {
			outChannel.write(buf);
		}
		
		outChannel.close();
	}
	
	public JSONObject createAccount(int numCliente) {
		JSONObject id = new JSONObject();
		id.put("id", "ID_"+numCliente);
		id.put("transactions", createTransactions());
		return id;
	}
	
	public JSONArray createTransactions() {
		JSONArray transactions = new JSONArray();
		for(int i = 0; i<20; i++) {
			transactions.add(createTransaction());
		}
		return transactions;
	}
	
	public JSONObject createTransaction() {
		JSONObject transaction = new JSONObject();
		int year = (int) (Math.random()*(2)) + 2017;
		int month = (int) (Math.random()*(11)) + 1;
		int day = (int) (Math.random()*(30)) + 1;
		transaction.put("data", year+"-"+month+"-"+day);
		transaction.put("causale", getCausale((int) (Math.random()*5)));
		int randomID = (int)(Math.random()*99998)+1;
		transaction.put("beneficiario", "ID_" + randomID);
		transaction.put("importo", (int)(Math.random()*99998)+1);
		return transaction;
	}
	
	private String getCausale(int x) {
		switch(x) {
		case 0 : 
			return "Bonifico";
		case 1 :
			return "Accredito";
		case 2 : 
			return "Bollettino";
		case 3 : 
			return "F24";
		case 4 :
			return "PagoBancomat";
		}
		return null;
	}

}
