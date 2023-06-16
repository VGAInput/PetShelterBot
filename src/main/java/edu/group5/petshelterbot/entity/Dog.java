package edu.group5.petshelterbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dogShelter")

public class Dog extends Pet implements Serializable {

    public Dog(String name, int age, String breed, String sex, boolean isSterilized, Owner owner) {
        super(name, age, breed, sex, isSterilized, owner);
    }
}
