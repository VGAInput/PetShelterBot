package edu.group5.petshelterbot.entity;


import javax.persistence.*;

@Entity
@Table (name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String shelterTableName;

    public Volunteer() {
    }

    public Volunteer(String name, String shelterTableName) {
        this.name = name;
        this.shelterTableName = shelterTableName;
    }
}
