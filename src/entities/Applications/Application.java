package entities.Applications;

import java.time.LocalDateTime;

public class Application {

    private String preSelectionStatus;
    private String selectionResult;
    private String technology;
    private LocalDateTime interviewDateAndTime;
    private String interviewResult;

    public Application(String preSelectionStatus, String selectionResult, String technology, LocalDateTime interviewDateAndTime,
                       String interviewResult) {
        this.preSelectionStatus = preSelectionStatus;
        this.selectionResult = selectionResult;
        this.technology = technology;
        this.interviewDateAndTime = interviewDateAndTime;
        this.interviewResult = interviewResult;
    }

    public String getPreSelectionStatus() {
        return preSelectionStatus;
    }

    public void setPreSelectionStatus(String preSelectionStatus) {
        this.preSelectionStatus = preSelectionStatus;
    }

    public String getSelectionResult() {
        return selectionResult;
    }

    public void setSelectionResult(String selectionResult) {
        this.selectionResult = selectionResult;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
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
}
