package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findReportById(long id);

    List<Report> findReportByOwnerId(long id);

    Report findReportByMessageId(int id);

    List<Report> findReportByVolunteerId(long id);

    Report findReportByReportText(String text);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE reports SET is_approved = ? WHERE id = ?", nativeQuery = true)
    int setApprove(Integer is_ready, Long id);

}
