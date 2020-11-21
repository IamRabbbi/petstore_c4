package com.petstore.service.store;

import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import com.petstore.data.repository.PetRepository;
import com.petstore.data.repository.StoreRepository;
import com.petstore.web.exceptions.StoreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {


    @Autowired
    StoreRepository storeRepository;

    @Autowired
    PetRepository petRepository;

    @Override
    public Store saveStore(Store store) {

        if(store == null){
            throw new NullPointerException("Null Object cannot be created");
        }
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) {
        return null;
    }

    @Override
    public Store findStoreById(Integer id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStoreById(Integer id) {
        storeRepository.deleteById(id);
    }

    @Override
    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store addPet(Integer storeId, Pet pet) throws StoreNotFoundException {

        //check that store exists
        Store store = storeRepository.findById(storeId).orElse(null);
        Pet savedPet;
        if(store == null){
            throw new StoreNotFoundException("Store object does not exist");
        }
        if(pet == null){
            throw new NullPointerException("Pet object cannot be null");
        }

        if(pet.getId() != null){
            savedPet = petRepository.findById(pet.getId()).orElse(null);

            if(savedPet == null){
                pet.setId(null);
                savedPet = petRepository.save(pet);
            }
        }
        else savedPet = petRepository.save(pet);

        store.addPets(savedPet);
        savedPet.setStore(store);

        return storeRepository.save(store);
    }
}
