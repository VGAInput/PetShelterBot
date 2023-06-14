package edu.group5.petshelterbot.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long tgUserId;
    private String shelterTableName;

    public Volunteer() {
    }

    public Volunteer(String name, String shelterTableName, long tgUserId) {
        this.name = name;
        this.tgUserId = tgUserId;
        this.shelterTableName = shelterTableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id && tgUserId == volunteer.tgUserId && Objects.equals(name, volunteer.name) && Objects.equals(shelterTableName, volunteer.shelterTableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tgUserId, shelterTableName);
    }
}
