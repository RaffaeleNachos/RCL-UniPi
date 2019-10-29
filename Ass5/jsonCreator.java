//Raffaele Apetino 549220

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.simple.*;

public class jsonCreator {
	/* classe che si occupa di creare un file JSON con valori casuali del tipo:
	 * 
	 * [
  		{
    		"id": "ID_XXXXX",
    		"transactions": [
      			{
        			"data": "YYYY-MM-DD",
        			"importo": XXXXX,
        			"causale": "PagoBancomat/Bonifico/...",
        			"beneficiario": "ID_XXXXX"
      			}			
    		]
  		}
	]*/

	int numClienti;
	int numTrans;
	String path = "./banca.json";
	
	public jsonCreator(int numClienti, int numTrans){
		this.numClienti=numClienti;
		this.numTrans=numTrans;
	}
	
	public void createFile()  throws IOException{
		//JSONArray più esterno di JSONObject
		JSONArray databaseBanca = new JSONArray();
		for (int i = 0; i<numClienti; i++) {
			databaseBanca.add(createAccount(i));
		}
		//creo ByteBuffer dove inserisco il mio JSONArray
		ByteBuffer buf = ByteBuffer.wrap(databaseBanca.toJSONString().getBytes("UTF-8"));
		try {
			Files.deleteIfExists(Paths.get(path)); //cancello il file precedentemente creato se esite
			Files.createFile(Paths.get(path)); //creo nuovo file
		} catch(Exception e) {
			e.printStackTrace();
		}
		//creo channel per la scrittura su file tramite Nio
		FileChannel outChannel = FileChannel.open(Paths.get(path),StandardOpenOption.WRITE);
		
		while(buf.hasRemaining()) {
			outChannel.write(buf);
		}
		
		outChannel.close();
		System.out.println("Thread " + Thread.currentThread().getName() + ": file creato");
	}
	
	public JSONObject createAccount(int numCliente) {
		JSONObject id = new JSONObject();
		id.put("id", "ID_" + numCliente);
		id.put("transactions", createTransactions());
		return id;
	}
	
	public JSONArray createTransactions() {
		JSONArray transactions = new JSONArray();
		for(int i = 0; i<numTrans; i++) {
			transactions.add(createTransaction());
		}
		return transactions;
	}
	
	public JSONObject createTransaction() {
		JSONObject transaction = new JSONObject();
		//creo randomicamente la data
		int year = (int) (Math.random()*(2)) + 2017;
		int month = (int) (Math.random()*(11)) + 1;
		int day = (int) (Math.random()*(30)) + 1;
		transaction.put("data", year+"-"+month+"-"+day);
		//creo randomicamente la causale
		transaction.put("causale", getCausale((int) (Math.random()*5)));
		//creo randomicamente il beneficiario della transazione
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
