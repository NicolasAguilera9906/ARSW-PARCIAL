package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
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
    private static final BigInteger MAXIMUM_NUMBER=new BigInteger(String.valueOf(10000));
    private static final int NUM_OF_THREADS=4;
    private static final BigInteger AMOUNT_OF_NUMBERS=MAXIMUM_NUMBER.subtract(LOWEST_NUMBER).add(new BigInteger(String.valueOf(1)));
    private static AtomicInteger amountOfNumbersProcessed = new AtomicInteger();
    private static PrimesResultSet primesResultSet;

	public static void main(String[] args) {
	        amountOfNumbersProcessed.set(0);
            primesResultSet=new PrimesResultSet("Nicolas");
	        PrimeFinderThread[] threads = new PrimeFinderThread[NUM_OF_THREADS];
            BigInteger start=new BigInteger(String.valueOf(0));
            BigInteger amount = AMOUNT_OF_NUMBERS.divide(new BigInteger(String.valueOf(NUM_OF_THREADS)));
            for(int i=0;i<NUM_OF_THREADS;i++) {
                if (i == NUM_OF_THREADS - 1) {
                    BigInteger extra = AMOUNT_OF_NUMBERS.mod(new BigInteger(String.valueOf(NUM_OF_THREADS)));
                    BigInteger end = amount.add(extra).add(start);
                    threads[i] = new PrimeFinderThread(start, end, primesResultSet,amountOfNumbersProcessed);
                } else {
                    BigInteger end = amount.add(start);
                    threads[i] = new PrimeFinderThread(start, end, primesResultSet,amountOfNumbersProcessed);
                }
                start = start.add(amount);
                threads[i].start();
            }

            
            System.out.println("Prime numbers found:");
            
            System.out.println(primesResultSet.getPrimes());

            while(AMOUNT_OF_NUMBERS.compareTo(BigInteger.valueOf(amountOfNumbersProcessed.get())) > 0){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>10000){
                    for(int i=0;i<NUM_OF_THREADS;i++) {
                        threads[i].pause();
                    }
                    System.out.println("PAUSA!");
                    System.out.println("Primos encontrados hasta el momento");
                    System.out.println(primesResultSet.getPrimes());
                }
                else{
                    System.out.println("Trabajando");
                    for(int i=0;i<NUM_OF_THREADS;i++) {
                        threads[i].restart();
                    }
                }
            }
        System.out.println("Primos encontrados");
        System.out.println(primesResultSet.getPrimes());
	}
	
}


