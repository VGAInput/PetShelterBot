package edu.group5.petshelterbot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import liquibase.pro.packaged.A;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private String breed;
    private String sex;
    private boolean isSterilized;
    public Pet() {
    }

    public Pet(String name, int age, String breed, String sex, boolean isSterilized) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.sex = sex;
        this.isSterilized = isSterilized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id && age == pet.age && isSterilized == pet.isSterilized && Objects.equals(name, pet.name) && Objects.equals(breed, pet.breed) && Objects.equals(sex, pet.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, breed, sex, isSterilized);
    }
}
