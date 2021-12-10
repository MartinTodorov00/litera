package entities;

import entities.enums.InterviewResults;
import entities.enums.PreSelectionStatuses;
import entities.enums.SelectionResults;

import java.time.LocalDateTime;

public class Application {

    private Candidate candidate;
    private PreSelectionStatuses preSelectionStatus;
    private SelectionResults selectionResult;
    private Technology technology;
    private LocalDateTime interviewDateAndTime;
    private InterviewResults interviewResult;

    public Application() {

    }

    public Application(Candidate candidate, PreSelectionStatuses preSelectionStatus, SelectionResults selectionResult, Technology technology,
                       LocalDateTime interviewDateAndTime, InterviewResults interviewResult) {
        this.candidate = candidate;
        this.preSelectionStatus = preSelectionStatus;
        this.selectionResult = selectionResult;
        this.technology = technology;
        this.interviewDateAndTime = interviewDateAndTime;
        this.interviewResult = interviewResult;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
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

    public InterviewResults getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(InterviewResults interviewResult) {
        this.interviewResult = interviewResult;
    }
}
