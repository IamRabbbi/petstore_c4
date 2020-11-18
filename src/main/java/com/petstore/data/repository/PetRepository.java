package com.petstore.data.repository;

import com.petstore.data.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {


}
