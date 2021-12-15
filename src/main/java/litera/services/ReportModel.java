package services;

public class ReportModel {

    private Integer appliedCandidates;
    private Integer acceptedCandidates;
    private Double acceptedCandidatesPercent;
    private String interviewResult;
    private String source;
    private String aggregation;

    public ReportModel() {
    }

    public Double getAcceptedCandidatesPercent() {
        return acceptedCandidatesPercent;
    }

    public void setAcceptedCandidatesPercent(Double acceptedCandidatesPercent) {
        this.acceptedCandidatesPercent = percent();
    }

    public String getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(String interviewResult) {
        this.interviewResult = interviewResult;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getAppliedCandidates() {
        return appliedCandidates;
    }

    public void setAppliedCandidates(Integer appliedCandidates) {
        this.appliedCandidates = appliedCandidates;
    }

    public Integer getAcceptedCandidates() {
        return acceptedCandidates;
    }

    public void setAcceptedCandidates(Integer acceptedCandidates) {
        this.acceptedCandidates = acceptedCandidates;
        percent();
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }

    private double percent() {
        if (acceptedCandidates != 0) {
           return this.acceptedCandidatesPercent = (double) (appliedCandidates / acceptedCandidates);
        }else {
           return this.acceptedCandidatesPercent = 0.0;
        }
    }
}
