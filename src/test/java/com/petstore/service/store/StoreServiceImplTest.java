package com.petstore.service.store;

import com.petstore.data.model.Store;
import com.petstore.data.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class StoreServiceImplTest {

    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    StoreService storeService = new StoreServiceImpl();

    Store testStore;

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);

         testStore = new Store();

    }

    @Test
    void mockStoreRepositorySaveStoreTest(){

        when(storeRepository.save(testStore)).thenReturn(testStore);
        storeService.saveStore(testStore);

        verify(storeRepository, times(1)).save(testStore);
    }

    @Test
    void mockStoreRepositoryFindStoreByIdMethodTest(){

        when(storeRepository.findById(7)).thenReturn(Optional.of(testStore));
        storeService.findStoreById(7);

        verify(storeRepository, times(1)).findById(7);

    }

    @Test
    void mockStoreRepositoryDeleteStoreMethodTest(){

        doNothing().when(storeRepository).deleteById(7);
        storeService.deleteStoreById(7);

        verify(storeRepository, times(1)).deleteById(7);

    }

    @Test
    void mockStoreRepositoryFindAllStoresMethodTest(){

        List<Store> storeList= new ArrayList<>();

        when(storeRepository.findAll()).thenReturn(storeList);
        storeService.findAllStores();

        verify(storeRepository, times(1)).findAll();

    }




}