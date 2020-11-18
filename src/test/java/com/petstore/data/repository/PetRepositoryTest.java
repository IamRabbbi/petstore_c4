package com.petstore.data.repository;

import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PetRepositoryTest {



    @Autowired
    PetRepository petRepository;

    @Autowired
    StoreRepository storeRepository;


    @BeforeEach
    void setUp() {
    }


    //Test that we can save a pet to the database
    @Test
    public void whenPetIsSaved_thenReturnAPetId() {

        //step 1: Create an instance of a pet
        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setAge(2);
        pet.setBreed("Dog");
        pet.setColor("Black");
        pet.setPetSex(Gender.MALE);

        log.info("Pet instance before saving --> {}", pet);

        //Call repository save method
        pet = petRepository.save(pet);

        assertThat(pet.getId()).isNotNull();
        log.info("Pet instance after saving --> {}", pet);



    }
    @Test
    @Transactional
    @Rollback(value = false)
    public void whenStoreIsMappedToPet_thenForeignKeyIsPresent() {

        //create a pet
        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setAge(2);
        pet.setBreed("Dog");
        pet.setColor("Black");
        pet.setPetSex(Gender.MALE);

        log.info("Pet instance before saving --> {}", pet);

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("09088585748");

        log.info("Store instance before saving --> {}", pet);
        //map pet to store
        pet.setStore(store);
        //save pet
        petRepository.save(pet);

        log.info("Pet instance after saving --> {}", pet);
        log.info("Store instance after saving --> {}", store);


        //assert
        assertThat(pet.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(pet.getStore()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void whenIAddPetsToAStore_thenICanFetchAListOfPetsFromStore() {

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("09088585748");

        //create 2 pets
        Pet jack = new Pet();
        jack.setName("Jack");
        jack.setAge(5);
        jack.setBreed("Dog");
        jack.setColor("Black");
        jack.setPetSex(Gender.MALE);
        jack.setStore(store);

        Pet sally  = new Pet();
        sally.setName("sally");
        sally.setAge(2);
        sally.setBreed("Dog");
        sally.setColor("Brown");
        sally.setPetSex(Gender.FEMALE);
        sally.setStore(store);

        log.info("Pet instances before saving --> {}", jack, sally);

        //map pet to store
        store.addPets(jack);
        store.addPets(sally);
        //save store

        storeRepository.save(store);

        log.info("Store instance after saving --> {}", store);

        //assert for pet id's
        assertThat(jack.getId()).isNotNull();
        assertThat(sally.getId()).isNotNull();
        //assert for store id
        assertThat(store.getId()).isNotNull();
        //assert that
        assertThat(store.getPetList()).isNotNull();
    }

    @Test
    public void whenFindAllPetIsCalled_thenReturnAllPetsInStore(){

        //find pets from store
        List<Pet> savedPets = petRepository.findAll();

        log.info("Fetched pets list from db --> {}", savedPets);
        //assert that pets exists
        assertThat(savedPets).isNotEmpty();
        assertThat(savedPets.size()).isEqualTo(7);


    }

    @Test
    public void updateExisitingPetDetailsTest(){

        //fetch a pet
        Pet sally = petRepository.findById(34).orElse(null);
        log.info("Pet object retrieved from database --> {}", sally);

        //aseert the field
        assertThat(sally).isNotNull();
        assertThat(sally.getColor()).isEqualTo("brown");
        //update pet field
        sally.setColor("purple");
        //save pet
        petRepository.save(sally);
        log.info("After updating pet object--> {}",sally);
        //assert that udpated field has changed
        assertThat(sally.getColor()).isEqualTo("purple");

    }

    @Test
    public void whenIdeletePetFromDatabase_thenPetIsDeleted(){

        //check if pet exists
        boolean result = petRepository.existsById(31);
        //assert that pet exist
        assertThat(result).isTrue();
        //delete pet
        petRepository.deleteById(31);
        //check if pet exists
        assertThat(petRepository.existsById(31)).isFalse();

    }
























}