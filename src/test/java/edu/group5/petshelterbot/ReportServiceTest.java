package edu.group5.petshelterbot;

import edu.group5.petshelterbot.entity.Report;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.ReportRepository;
import edu.group5.petshelterbot.service.ReportService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    private static final Integer MESSAGE_ID = 1;
    private static final Long OWNER_ID = 1L;
    private static final Long VOLUNTEER_ID = 1L;
    private static final Date REPORT_DATE = new Date();
    private static final String REPORT_TEXT = "SOME TEXT";
    private static final Integer IS_APPROVED = 1;
    private static final Integer IS_NOT_APPROVED = 0;

    private final Report report = new Report(MESSAGE_ID, OWNER_ID, REPORT_DATE, VOLUNTEER_ID, REPORT_TEXT, IS_APPROVED);

    private final List<Report> reports = new ArrayList<>(Arrays.asList(
            new Report(MESSAGE_ID, OWNER_ID, REPORT_DATE, VOLUNTEER_ID, REPORT_TEXT, IS_APPROVED),
            new Report(MESSAGE_ID, OWNER_ID, REPORT_DATE, VOLUNTEER_ID, REPORT_TEXT, IS_APPROVED),
            new Report(MESSAGE_ID, OWNER_ID, REPORT_DATE, VOLUNTEER_ID, REPORT_TEXT, IS_APPROVED),
            new Report(MESSAGE_ID, OWNER_ID, REPORT_DATE, VOLUNTEER_ID, REPORT_TEXT, IS_APPROVED)
    ));

    @Mock
    private ReportRepository reportRepositoryMock;

    @InjectMocks
    private ReportService reportService;


    @Test
    void saveReport() {
        Mockito.when(reportRepositoryMock.save(any(Report.class))).thenReturn(report);

        Report report1 = reportService.saveReport(report);

        Assertions.assertThat(report1.getMessageId()).isEqualTo(report.getMessageId());
        Assertions.assertThat(report1.getOwnerId()).isEqualTo(report.getOwnerId());
        Assertions.assertThat(report1.getReportDate()).isEqualTo(report.getReportDate());
        Assertions.assertThat(report1.getVolunteerId()).isEqualTo(report.getVolunteerId());
        Assertions.assertThat(report1.getReportText()).isEqualTo(report.getReportText());
        Assertions.assertThat(report1.getIsApproved()).isEqualTo(report.getIsApproved());
    }

    @Test
    void getReportByID() {
        Mockito.when(reportRepositoryMock.findReportById(any(Long.class))).thenReturn(report);

        Report report1 = reportService.getReportByID(1L);

        Assertions.assertThat(report1.getMessageId()).isEqualTo(report.getMessageId());
        Assertions.assertThat(report1.getOwnerId()).isEqualTo(report.getOwnerId());
        Assertions.assertThat(report1.getReportDate()).isEqualTo(report.getReportDate());
        Assertions.assertThat(report1.getVolunteerId()).isEqualTo(report.getVolunteerId());
        Assertions.assertThat(report1.getReportText()).isEqualTo(report.getReportText());
        Assertions.assertThat(report1.getIsApproved()).isEqualTo(report.getIsApproved());
    }

    @Test
    void setApprove() {
        Mockito.when(reportRepositoryMock.setApprove(any(Integer.class), any(Long.class))).thenReturn(1);

        int result = reportRepositoryMock.setApprove(1, 1L);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void getReportByMessageId() {
        Mockito.when(reportRepositoryMock.findReportByMessageId(any(Integer.class))).thenReturn(report);

        Report report1 = reportService.getReportByMessageId(1);

        Assertions.assertThat(report1.getMessageId()).isEqualTo(report.getMessageId());
        Assertions.assertThat(report1.getOwnerId()).isEqualTo(report.getOwnerId());
        Assertions.assertThat(report1.getReportDate()).isEqualTo(report.getReportDate());
        Assertions.assertThat(report1.getVolunteerId()).isEqualTo(report.getVolunteerId());
        Assertions.assertThat(report1.getReportText()).isEqualTo(report.getReportText());
        Assertions.assertThat(report1.getIsApproved()).isEqualTo(report.getIsApproved());
    }

    @Test
    void getReportByText() {
        Mockito.when(reportRepositoryMock.findReportByReportText(any(String.class))).thenReturn(report);

        Report report1 = reportService.getReportByText("text");

        Assertions.assertThat(report1.getMessageId()).isEqualTo(report.getMessageId());
        Assertions.assertThat(report1.getOwnerId()).isEqualTo(report.getOwnerId());
        Assertions.assertThat(report1.getReportDate()).isEqualTo(report.getReportDate());
        Assertions.assertThat(report1.getVolunteerId()).isEqualTo(report.getVolunteerId());
        Assertions.assertThat(report1.getReportText()).isEqualTo(report.getReportText());
        Assertions.assertThat(report1.getIsApproved()).isEqualTo(report.getIsApproved());
    }

    @Test
    void getReportByOwnerID() {
        Mockito.when(reportRepositoryMock.findReportByOwnerId(any(Long.class))).thenReturn(reports);

        List<Report> reports1 = reportService.getReportByOwnerID(1L);

        Assertions.assertThat(reports1.size()).isEqualTo(reports.size());
        Assertions.assertThat(reports1).isEqualTo(reports);
    }

    @Test
    void getReportByVolunteerID() {
        Mockito.when(reportRepositoryMock.findReportByVolunteerId(any(Long.class))).thenReturn(reports);

        List<Report> reports1 = reportService.getReportByVolunteerID(1L);

        Assertions.assertThat(reports1.size()).isEqualTo(reports.size());
        Assertions.assertThat(reports1).isEqualTo(reports);
    }
}
