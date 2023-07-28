package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Report;
import edu.group5.petshelterbot.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public Report getReportByID(long id) {
        return reportRepository.findReportById(id);
    }

    public void setApprove(int approve, long id) {
        reportRepository.setApprove(approve, id);
    }

    public Report getReportByMessageId(int id) {
        return reportRepository.findReportByMessageId(id);
    }

    public Report getReportByText(String text) {
        return reportRepository.findReportByReportText(text);
    }

    public List<Report> getReportByOwnerID(long id) {
        return reportRepository.findReportByOwnerId(id);
    }


    public List<Report> getReportByVolunteerID(long id) {
        return reportRepository.findReportByVolunteerId(id);
    }

    public void deleteReport(Report deleteReport) {
        reportRepository.delete(deleteReport);
    }

}
