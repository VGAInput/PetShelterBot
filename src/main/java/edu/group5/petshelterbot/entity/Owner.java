package edu.group5.petshelterbot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor

@Getter
@Setter
@AllArgsConstructor
@Builder

@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long tgUserId;
    private String name;

    public Owner(long tgUserId, String name) {
        this.tgUserId = tgUserId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return id == owner.id && tgUserId == owner.tgUserId && Objects.equals(name, owner.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tgUserId, name);
    }
}
