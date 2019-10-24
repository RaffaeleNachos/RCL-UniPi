import java.io.IOException;

public class MainClass {

	public static void main(String[] args) {
		try {
			jsonCreator test = new jsonCreator();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reader prod = new Reader();
		prod.start();
		
	}
}
