package com.petstore.data.repository;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest()
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void saveStoreToDatabaseTest(){

        Store mystore = new Store();
        mystore.setName("Elite Pets");
        mystore.setLocation("Lagos");
        mystore.setContactNo("0909078766464");

        log.info("Store object before saving --> {}", mystore);
        storeRepository.save(mystore);
        log.info("Store object after saving --> {}", mystore);
        assertThat(mystore.getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void findAllStoresSavedToDBTests(){

        List<Store> mystores = storeRepository.findAll();
        log.info("Stores retrieved from the database --> {}", mystores);
        assertThat(mystores).isNotNull();
        assertThat(mystores.size()).isEqualTo(5);

        log.info("Pets in store with ID 21 --> {}", mystores.get(0).getPetList());

    }


    @Test
    void updateExistingStoreDetailsTest(){

        Store elite = storeRepository.findById(22).orElse(null);
        assertThat(elite).isNotNull();
        assertThat(elite.getLocation()).isEqualTo("Kaduna");
        log.info("Store object updated --> {}", elite);

        elite.setLocation("Borno");
        storeRepository.save(elite);
        log.info("Store object updated --> {}", elite);
        assertThat(elite.getLocation()).isEqualTo("Borno");
    }

    @Test
    void deleteStoreTest(){

        assertThat(storeRepository.existsById(23)).isTrue();
        storeRepository.deleteById(23);
        assertThat(storeRepository.existsById(23)).isFalse();

    }

    @Transactional
    @Rollback(value = false)
    @Test
    void findAllPetsInAStoreTest(){

        Store store = storeRepository.findById(21).orElse(null);
        assertThat(store).isNotNull();
        assertThat(store.getPetList()).isNotNull();
        log.info("Pets in the store of ID 21 --> {}", store.getPetList());
    }

    @Test
    void findStoreByNameTest(){

        Store store = storeRepository.findByName("Elite store");

        log.info("Store object --> {}", store);
        assertThat(store).isNotNull();

    }


}