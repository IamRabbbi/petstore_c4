package com.petstore.service.store;

import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import com.petstore.web.exceptions.StoreNotFoundException;

import java.util.List;

public interface StoreService {

    Store saveStore(Store store);
    Store updateStore(Store store);
    Store findStoreById(Integer id);
    void deleteStoreById (Integer id);
    List<Store> findAllStores();
    Store addPet(Integer storeId, Pet pet) throws StoreNotFoundException;

}
