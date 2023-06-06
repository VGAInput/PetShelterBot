package edu.group5.petshelterbot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "catshelter")

public class Cat extends Pet implements Serializable {
    public Cat() {
    }
    public Cat(String name, int age, String breed, String sex, boolean isSterilized) {
        super(name, age, breed, sex, isSterilized);
    }
}
