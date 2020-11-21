package com.petstore.web.controllers.store;


import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import com.petstore.service.store.StoreService;
import com.petstore.web.exceptions.StoreNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@Slf4j
public class StoreRestController {

    @Autowired
    StoreService storeService;


    @PostMapping("/")
    public ResponseEntity<?> savePet(@RequestBody Store store){

        //log request body
        log.info("Request object --> {}", store);
        //save request
        try {
            storeService.saveStore(store);
        }catch (NullPointerException exe){

            return ResponseEntity.badRequest().body(exe.getMessage());
        }

        return new ResponseEntity<>(store, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<?> addPetToStore(@RequestParam("storeId") String storeId, @RequestBody Pet pet ){

        Store store = null;
       try {
           store = storeService.addPet(Integer.parseInt(storeId), pet);
       } catch (StoreNotFoundException e) {
           ResponseEntity.badRequest().body(e.getMessage());
       }

       return ResponseEntity.ok().body(store);
    }

}
