package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */


@Service
public class PrimeServiceStub implements PrimeService
{

    private List<FoundPrime> foundPrimes = new CopyOnWriteArrayList<>();

    @Override
    public void addFoundPrime( FoundPrime foundPrime ) throws FoundPrimeException {
        if(foundPrime==null){
            throw new FoundPrimeException("El primo es nulo");
        }
        for(FoundPrime prime : foundPrimes){
            if(prime.getPrime().equals(foundPrime.getPrime())){
                if(!(prime.getUser().equals(foundPrime.getUser()))){
                    throw new FoundPrimeException("No se permite añadir el mismo primo con dos usuarios diferentes");
                }
            }
        }
        foundPrimes.add(foundPrime);
    }

    @Override
    public List<FoundPrime> getFoundPrimes() throws FoundPrimeException {
        if(foundPrimes.size()==0){
            throw new FoundPrimeException("No hay primos");
        }
        return foundPrimes;
    }

    @Override
    public FoundPrime getPrime( String prime ) throws FoundPrimeException {
        boolean found = false;
        FoundPrime foundPrime = null;
        for(FoundPrime fPrime : foundPrimes){
            if(fPrime.getPrime().equals(prime)){
                foundPrime = fPrime;
                found=true;
                break;
            }
        }
        if(!found){
            throw new FoundPrimeException("El primo no existe");
        }
        return foundPrime;
    }
}
