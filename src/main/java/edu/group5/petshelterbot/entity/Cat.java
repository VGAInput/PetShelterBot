package edu.group5.petshelterbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cat_shelter")

public class Cat extends Pet implements Serializable {

    public Cat(String name, int age, String breed, String sex, boolean is_sterilized) {
        super(name, age, breed, sex, is_sterilized);
    }
}
