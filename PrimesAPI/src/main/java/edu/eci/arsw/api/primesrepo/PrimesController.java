package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.FoundPrimeException;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    //TODO implement additional methods provided by PrimeService



}
