package entities;

import entities.enums.PreSelectionStatuses;
import entities.enums.SelectionResults;

import java.time.LocalDateTime;

public class Application {

    private PreSelectionStatuses preSelectionStatus;
    private SelectionResults selectionResult;
    private Technology technology;
    private LocalDateTime interviewDateAndTime;
    private String interviewResult;
    private Candidate candidate;

    public Application(PreSelectionStatuses preSelectionStatus, SelectionResults selectionResult, Technology technology,
                       LocalDateTime interviewDateAndTime, String interviewResult, Candidate candidate) {
        this.preSelectionStatus = preSelectionStatus;
        this.selectionResult = selectionResult;
        this.technology = technology;
        this.interviewDateAndTime = interviewDateAndTime;
        this.interviewResult = interviewResult;
        this.candidate = candidate;
    }

    public PreSelectionStatuses getPreSelectionStatus() {
        return preSelectionStatus;
    }

    public void setPreSelectionStatus(PreSelectionStatuses preSelectionStatus) {
        this.preSelectionStatus = preSelectionStatus;
    }

    public SelectionResults getSelectionResult() {
        return selectionResult;
    }

    public void setSelectionResult(SelectionResults selectionResult) {
        this.selectionResult = selectionResult;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public LocalDateTime getInterviewDateAndTime() {
        return interviewDateAndTime;
    }

    public void setInterviewDateAndTime(LocalDateTime interviewDateAndTime) {
        this.interviewDateAndTime = interviewDateAndTime;
    }

    public String getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(String interviewResult) {
        this.interviewResult = interviewResult;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
