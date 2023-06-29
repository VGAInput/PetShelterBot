package edu.group5.petshelterbot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@ToString
@EqualsAndHashCode
@Getter
@Setter

@NoArgsConstructor
@MappedSuperclass
public abstract class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private String breed;
    private String sex;
    private boolean is_sterilized;
    public Pet(String name, int age, String breed, String sex, boolean is_sterilized) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.sex = sex;
        this.is_sterilized = is_sterilized;
    }

}
