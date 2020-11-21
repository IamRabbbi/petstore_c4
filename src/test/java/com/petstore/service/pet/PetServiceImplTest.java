package com.petstore.service.pet;

import com.petstore.data.model.Pet;
import com.petstore.data.repository.PetRepository;
import com.petstore.web.exceptions.PetDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
class PetServiceImplTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService = new PetServiceImpl();

    @Autowired
    PetService petServiceImpl;

    Pet testPet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
         testPet = new Pet();
    }

    @Test
    void mockTheSavePetToRepositoryTest(){

        when(petRepository.save(testPet)).thenReturn(testPet);
        petService.savePet(testPet);

        verify(petRepository, times(1)).save(testPet);
    }

    @Test
    void mockTheFindByIdRepositoryTest() throws PetDoesNotExistException {

        when(petRepository.findById(2)).thenReturn(Optional.of(testPet));
        petService.findPetById(2);

        verify(petRepository, times(1)).findById(2);
    }

    @Test
    void mockDeletePetReposiotoryTest() throws PetDoesNotExistException {

        doNothing().when(petRepository).deleteById(2);
        petService.deletePetById(2);

        verify(petRepository, times(1)).deleteById(2);
    }

    @Test
    void whenPetWithIdDoesNotExist_thenThrowException(){

       assertThrows(PetDoesNotExistException.class, ()-> petServiceImpl.findPetById(7));

    }














}