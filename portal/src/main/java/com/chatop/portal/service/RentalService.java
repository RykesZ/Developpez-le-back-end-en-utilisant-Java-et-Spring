package com.chatop.portal.service;

import com.chatop.portal.model.Rental;
import com.chatop.portal.repository.RentalRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public Optional<Rental> getRental(final Long id) {
        return rentalRepository.findById(id);
    }

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental saveRental(Rental rental) {
        Rental savedRental= rentalRepository.save(rental);
        return savedRental;
    }
}
