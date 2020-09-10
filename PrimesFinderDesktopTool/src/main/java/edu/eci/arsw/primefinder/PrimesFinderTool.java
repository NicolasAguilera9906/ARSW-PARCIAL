package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool {

    private static final BigInteger LOWEST_NUMBER=new BigInteger(String.valueOf(1));
    private static final BigInteger MAXIMUM_NUMBER=new BigInteger(String.valueOf(10));
    private static final int NUM_OF_THREADS=4;
    private static final BigInteger AMOUNT_OF_NUMBERS=MAXIMUM_NUMBER.subtract(LOWEST_NUMBER).add(new BigInteger(String.valueOf(1)));
    private static PrimeFinder primeFinder;

	public static void main(String[] args) {
	        primeFinder = new PrimeFinder();
            PrimesResultSet prs=new PrimesResultSet("Nicolas");
	        PrimeFinderThread[] threads = new PrimeFinderThread[NUM_OF_THREADS];
            BigInteger start=new BigInteger(String.valueOf(1));
            BigInteger amount = AMOUNT_OF_NUMBERS.divide(new BigInteger(String.valueOf(NUM_OF_THREADS)));
            for(int i=0;i<NUM_OF_THREADS;i++) {
                if (i == NUM_OF_THREADS - 1) {
                    BigInteger extra = AMOUNT_OF_NUMBERS.mod(new BigInteger(String.valueOf(NUM_OF_THREADS)));
                    BigInteger end = amount.add(extra).add(start);
                    threads[i] = new PrimeFinderThread(start, end, primeFinder , prs);
                } else {
                    BigInteger end = amount.add(start);
                    threads[i] = new PrimeFinderThread(start, end, primeFinder , prs);
                }
                threads[i].start();
                start = start.add(amount);
            }
            int maxPrim=1000;


            PrimeFinder.findPrimes(new BigInteger("1"), new BigInteger("10000"), prs);
            
            System.out.println("Prime numbers found:");
            
            System.out.println(prs.getPrimes());

            /*while(task_not_finished){
                try {
                    //check every 10ms if the idle status (10 seconds without mouse
                    //activity) was reached. 
                    Thread.sleep(10);
                    if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>10000){
                        System.out.println("Idle CPU ");
                    }
                    else{
                        System.out.println("User working again!");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
                        
            
            
            
            
	}
	
}


