package com.chatop.portal.controller;

import com.chatop.portal.model.Rental;
import com.chatop.portal.model.User;
import com.chatop.portal.service.FileUploadService;
import com.chatop.portal.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals")
    @Secured("ROLE_TENANT")
    public Iterable<Rental> getRentals() {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return rentalService.getRentals();
    }

    @GetMapping("/rentals/{id}")
    public Optional<Rental> getRental(@PathVariable Long id) {
        return rentalService.getRental(id);
    }

    @PostMapping(value = "/rentals", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createRental(@RequestParam String name, @RequestParam Double surface, @RequestParam Double price, @RequestParam String description, @RequestPart MultipartFile picture) throws IOException {
      String fileName = StringUtils.cleanPath(picture.getOriginalFilename());

      try {
        Path filePath = FileUploadService.saveFile(fileName, picture);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = (User)principal;
        Long currentUserId = currentUser.getId();
        rentalService.saveRental(name, surface, price, filePath.toString(), description, currentUserId);
      } catch (IOException ioException) {
        throw new IOException("Could not save file: " + fileName, ioException);
      }

      return new ResponseEntity<>("success", HttpStatus.OK);
    }

  @PutMapping("/rentals/{id}")
  public ResponseEntity<String> updateRental(@PathVariable Long id, @RequestParam String name, @RequestParam Double surface, @RequestParam Double price, @RequestParam String description) throws IOException {
    // Vérifier si la location avec l'ID spécifié existe
    Optional<Rental> optionalRental = rentalService.getRental(id);
    if (optionalRental.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Rental rental = optionalRental.get();

    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);
    rentalService.updateRental(id, rental.getName(), rental.getSurface(), rental.getPrice(), rental.getDescription());

    return ResponseEntity.ok("Rental updated successfully");
  }
}
