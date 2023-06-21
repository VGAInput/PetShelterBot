package edu.group5.petshelterbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "catShelter")

public class Cat extends Pet implements Serializable {

    public Cat(String name, int age, String breed, String sex, boolean isSterilized, Owner owner) {
        super(name, age, breed, sex, isSterilized, owner);
    }
}
