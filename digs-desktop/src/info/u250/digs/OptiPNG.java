package info.u250.digs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OptiPNG {
	public static void main(String[] args) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(100);
		service.submit(newJob("OptiPNG/optipng.exe  assets/data/all.png"));
		service.submit(newJob("OptiPNG/optipng.exe  assets/data/fnt/*.png"));
		service.submit(newJob("OptiPNG/optipng.exe  assets/qvg/*.png"));
		service.submit(newJob("OptiPNG/optipng.exe  assets/wb/*.png"));
		service.submit(newJob("OptiPNG/optipng.exe  assets/paint/*.png"));
		service.shutdown();
	}
	
	public static Runnable newJob(final String exec){
		return new Runnable() {
			@Override
			public void run() {
				try{
					String s;
					Process process = Runtime.getRuntime().exec(exec);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while((s=bufferedReader.readLine()) != null){
						System.out.println(s);
					}
					process.waitFor();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		};
	}

}
