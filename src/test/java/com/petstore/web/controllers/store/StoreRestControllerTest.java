package com.petstore.web.controllers.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import com.petstore.data.repository.PetRepository;
import com.petstore.service.pet.PetService;
import com.petstore.web.exceptions.PetDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/insert.sql"})
class StoreRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;

    @Autowired
    PetService petService;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenICallTheCreateStorePostMethod_thenCreateAStoreObject() throws Exception {

        Store petStore = new Store();
        petStore.setName("Pet Store of Dublin");
        petStore.setLocation("Dublin");
        petStore.setContactNo("09087676844");


        this.mockMvc.perform(post("/store/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(petStore)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(not(nullValue()))))
                .andExpect(jsonPath("$.name").value("Pet Store of Dublin"))
                .andExpect(jsonPath("$.location").value("Dublin"))
                .andExpect(jsonPath("$.contactNo").value("09087676844"))
                .andReturn();
    }

    @Test
    void whenICallTheAddPetsPutMethod_thenAddExistingPetsObjectToStoreTest() throws Exception {

        Pet pet = petService.findPetById(34);

        assertThat(pet.getName()).isEqualTo("sally");
        assertThat(pet.getColor()).isEqualTo("brown");
        assertThat(pet.getAge()).isEqualTo(5);
        assertThat(pet.getBreed()).isEqualTo("dog");
        assertThat(pet.getPetSex()).isEqualTo(Gender.FEMALE);

        this.mockMvc.perform(put("/store/").param("storeId", "25")
                .contentType("application/json")
                .content(mapper.writeValueAsString(pet)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(25))
                .andExpect(jsonPath("$.petList[0].id").value(34))
                .andReturn();
    }
}