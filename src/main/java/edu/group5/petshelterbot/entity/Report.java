package edu.group5.petshelterbot.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "reports")

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long messageId;
    private long ownerId;
    private Date reportDate;
    private long volunteerId;
    private String reportText;
    private int isApproved;

    public Report(int messageId, long idOwner, Date reportDate, long volunteerId, String reportText, int isApproved) {
        this.messageId = messageId;
        this.ownerId = idOwner;
        this.reportDate = reportDate;
        this.volunteerId = volunteerId;
        this.reportText = reportText;
        this.isApproved = isApproved;
    }
}
