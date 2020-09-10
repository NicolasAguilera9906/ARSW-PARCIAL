package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.FoundPrimeException;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RequestMapping(value = "/primes")
@RestController
public class PrimesController
{
    @Autowired
    PrimeService primeService;


    @RequestMapping( value = "/primes", method = GET )
    public ResponseEntity<?> getPrimes()
    {
        try {
            return new ResponseEntity<>(primeService.getFoundPrimes(), HttpStatus.ACCEPTED);
        } catch (FoundPrimeException ex) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{primenumber}", method = RequestMethod.GET)
    public ResponseEntity<?> accountStatus(@PathVariable String prime) {
        try {
            return new ResponseEntity<>(primeService.getPrime(prime), HttpStatus.ACCEPTED);
        } catch (FoundPrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addSuspect(@RequestBody List<FoundPrime> primes) {
        try {
            for(FoundPrime prime : primes){
                primeService.addFoundPrime(prime);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (HttpMessageNotReadableException | FoundPrimeException hx){
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, null, hx);
            return new ResponseEntity<>("Existe un error en el formato del JSON de la funcion", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{primenumber}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSuspect(@RequestBody FoundPrime prime) {
        try {
            primeService.addFoundPrime(prime);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FoundPrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
