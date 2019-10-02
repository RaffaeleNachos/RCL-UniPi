
public class Task implements Runnable{
	
	public String name;
	
	public Task(String name) {
		this.name=name;
		}

	@Override
	public void run() {
		Long duration = (long) (Math.random()*5000);
		System.out.println(duration);
		try{
			System.out.println(name + " eseguito da " + Thread.currentThread().getName() + " sleeping for: " + duration + "ms");
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + " eseguito da " + Thread.currentThread().getName() + " terminato");
		
	}
}
