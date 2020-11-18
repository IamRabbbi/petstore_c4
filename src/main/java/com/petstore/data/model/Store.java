package com.petstore.data.model;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String location;

    private String contactNo;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Pet> petList;

    public void addPets(Pet pet){

        if(petList == null){
            petList = new ArrayList<>();
        }

        petList.add(pet);
    }

}
