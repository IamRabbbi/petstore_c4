package com.petstore.data.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    private String color;

    private String breed;

    @Enumerated(EnumType.STRING)
    private Gender petSex;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Store store;

}
