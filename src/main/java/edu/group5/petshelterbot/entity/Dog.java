package edu.group5.petshelterbot.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "dogshelter")

public class Dog extends Pet implements Serializable {

    public Dog() {
    }

    public Dog(String name, int age, String breed, String sex, boolean isSterilized) {
        super(name, age, breed, sex, isSterilized);
    }
}
