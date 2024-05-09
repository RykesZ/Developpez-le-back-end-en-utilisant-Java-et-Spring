package com.chatop.portal.service;

import com.chatop.portal.model.Rental;
import com.chatop.portal.repository.RentalRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

@Data
@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public Optional<Rental> getRental(final long id) {
        return rentalRepository.findById(id);
    }

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

  public Rental saveRental(String name, double surface, double price, String picturePath, String description, Long ownerId) {
    Rental rental = new Rental();
    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setPicture(picturePath);
    rental.setDescription(description);
    rental.setOwner_id(ownerId);

    return rentalRepository.save(rental);
  }

  public Rental updateRental(Long id, String name, double surface, double price, String description) {
    Optional<Rental> optionalRental = rentalRepository.findById(id);
    if (optionalRental.isPresent()) {
      Rental rental = optionalRental.get();
      rental.setName(name);
      rental.setSurface(surface);
      rental.setPrice(price);
      rental.setDescription(description);
      return rentalRepository.save(rental);
    } else {
      return null;
    }
  }
}
