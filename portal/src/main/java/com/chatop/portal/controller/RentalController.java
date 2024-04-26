package com.chatop.portal.controller;

import com.chatop.portal.model.Rental;
import com.chatop.portal.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals")
    public Iterable<Rental> getRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/rentals/{id}")
    public Optional<Rental> getRental(@PathVariable Long id) {
        return rentalService.getRental(id);
    }

    @PostMapping("/rentals")
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.saveRental(rental);
    }

    @PutMapping("/rentals/{id}")
    public Rental updateRental(@PathVariable Rental rental) {
        return rentalService.saveRental(rental);
    }

}
