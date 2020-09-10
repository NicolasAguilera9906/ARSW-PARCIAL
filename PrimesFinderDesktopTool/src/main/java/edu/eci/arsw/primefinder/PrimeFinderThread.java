package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinderThread extends Thread {
    private BigInteger start;
    private BigInteger end;
    private static PrimesResultSet primesResultSet;
    private static boolean pause;
    private static AtomicInteger amountOfNumbersProcessed;
    private static Object object = new Object();

    public PrimeFinderThread(BigInteger start, BigInteger end, PrimesResultSet primesResultSet , AtomicInteger amountOfNumbersProcessed) {
        this.start = start;
        this.end = end;
        this.primesResultSet=primesResultSet;
        this.amountOfNumbersProcessed = amountOfNumbersProcessed;
    }


    @Override
    public void run() {
        for (BigInteger i = start; i.compareTo(end) < 0; i = i.add(BigInteger.ONE)) {
            synchronized (object) {
                if(pause) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            MathUtilities mt = new MathUtilities();
            if (mt.isPrime(i)){
                primesResultSet.addPrime(i);
            }
            amountOfNumbersProcessed.incrementAndGet();
        }
    }

    public synchronized void pause(){
        this.pause = true;
    }
    public synchronized void restart(){
        this.pause = false;
        notifyAll();
    }

}
