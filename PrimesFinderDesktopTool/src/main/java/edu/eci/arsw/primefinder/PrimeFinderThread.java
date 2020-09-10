package edu.eci.arsw.primefinder;

import java.math.BigInteger;

public class PrimeFinderThread extends Thread {
    private BigInteger start;
    private BigInteger end;
    private PrimeFinder primeFinder;
    private PrimesResultSet primesResultSet;
    public PrimeFinderThread(BigInteger start, BigInteger end, PrimeFinder primeFinder, PrimesResultSet primesResultSet) {
        this.start = start;
        this.end = end;
        this.primeFinder=primeFinder;
        this.primesResultSet=primesResultSet;
    }

    @Override
    public void run() {
        for (BigInteger i = start;
             i.compareTo(end) < 0;
             i = i.add(BigInteger.ONE)) {
            System.out.println(i);
        }
    }
}
