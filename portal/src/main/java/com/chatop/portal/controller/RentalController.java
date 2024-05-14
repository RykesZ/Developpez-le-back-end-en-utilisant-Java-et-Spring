package com.chatop.portal.controller;

import com.chatop.portal.dto.RentalPublic;
import com.chatop.portal.model.Rental;
import com.chatop.portal.model.User;
import com.chatop.portal.service.FileUploadService;
import com.chatop.portal.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Get all the rentals", description = "Allow the current authenticated user to get all the rentals")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/rentals")
    public ResponseEntity<Object> getRentals() {
      Iterable<Rental> rentals = rentalService.getRentals();
      List<RentalPublic> rentalPublicList = new ArrayList<>();
      for (Rental rental : rentals) {
        rentalPublicList.add(new RentalPublic(rental));
      }
      JSONObject responseJson = new JSONObject();
      responseJson.put("rentals", rentalPublicList);
      return ResponseEntity.ok(responseJson.toString());
    }

    @Operation(summary = "Get one rental infos", description = "Allow the current authenticated user to get one rental infos")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/rentals/{id}")
    public ResponseEntity<?> getRental(@PathVariable Long id) {
      Optional<Rental> rental = rentalService.getRental(id);
      if (rental.isPresent()) {
        return ResponseEntity.ok(new RentalPublic(rental.get()));
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Location inexistante pour cet id");
      }
    }

    @Operation(summary = "Post a new rental", description = "Allow the current authenticated user to post a new rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/rentals", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> createRental(@RequestParam String name, @RequestParam Double surface, @RequestParam Double price, @RequestParam String description, @RequestPart MultipartFile picture) throws IOException {
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

      JSONObject responseJson = new JSONObject();
      responseJson.put("message", "Rental created !");
      return ResponseEntity.ok(responseJson.toString());
    }

  @Operation(summary = "Modify an existing rental", description = "Allow the current authenticated user to modify one of their own rentals")
  @SecurityRequirement(name = "Bearer Authentication")
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

    JSONObject responseJson = new JSONObject();
    responseJson.put("message", "Rental updated !");
    return ResponseEntity.ok(responseJson.toString());
  }
}
