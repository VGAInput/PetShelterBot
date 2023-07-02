package edu.group5.petshelterbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dog_shelter")

public class Dog extends Pet implements Serializable {

    public Dog(String name, int age, String breed, String sex, boolean is_sterilized) {
        super(name, age, breed, sex, is_sterilized);
    }
}
