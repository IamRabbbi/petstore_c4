package com.petstore.web.controllers.pet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
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
class PetRestControllerTest {


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
    void whenICallTheCreatePetPostMethod_thenCreateAPetObject() throws Exception {

        Pet pet = new Pet();
        pet.setName("silk");
        pet.setColor("blue");
        pet.setPetSex(Gender.MALE);
        pet.setBreed("cat");
        pet.setAge(7);


        this.mockMvc.perform(post("/pet/create")
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(pet)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(not(nullValue()))))
                    .andExpect(jsonPath("$.name").value("silk"))
                    .andReturn();
    }

    @Test
    void whenICallThegetPetsMethod_thenReturnAllPetsObject() throws Exception {

        this.mockMvc.perform(get("/pet/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(31, 32, 33, 34, 35, 36, 37)))
                .andReturn();
    }

    @Test
    void whenICallTheFindMethod_thenReturnPetObject() throws Exception {

        MvcResult result =  this.mockMvc.perform(get("/pet/one/31"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(31))
                .andExpect(jsonPath("$.name").value("jill"))
                .andReturn();

    }

    @Test
    void whenICallTheDeletePete_thenDeletePetObject() throws Exception {

        this.mockMvc.perform(delete("/pet/one/31"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        assertThrows(PetDoesNotExistException.class, ()-> petService.findPetById(31));

    }

    @Test
    void whenIcallPatchRequest_thenUpdatePetDetails() throws Exception {

        Pet pet = petService.findPetById(34);

        assertThat(pet.getName()).isEqualTo("sally");
        assertThat(pet.getColor()).isEqualTo("brown");
        assertThat(pet.getAge()).isEqualTo(5);
        assertThat(pet.getBreed()).isEqualTo("dog");
        assertThat(pet.getPetSex()).isEqualTo(Gender.FEMALE);


        //update name through patch request
        pet.setName("brian");
        pet.setPetSex(Gender.MALE);
        pet.setAge(7);

         this.mockMvc.perform(patch("/pet/one")
                .contentType("application/json")
                .content(mapper.writeValueAsString(pet)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pet.getId()))
                .andExpect(jsonPath("$.name").value("brian"))
                .andExpect(jsonPath("$.color").value("brown"))
                .andExpect(jsonPath("$.petSex").value("MALE"))
                .andExpect(jsonPath("$.breed").value("dog"))
                .andExpect(jsonPath("$.age").value(7))
                .andReturn();

    }





}