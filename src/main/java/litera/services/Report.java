package services;

import entities.enums.InterviewResults;
import entities.enums.Sources;

public class Report {

    private Integer appliedCandidatesSum;
    private Integer acceptedCandidatesSum;
    private InterviewResults interviewResult;
    private Sources source;
    private String aggregation;

    public Report() {
    }

    public Sources getSource() {
        return source;
    }

    public void setSource(Sources source) {
        this.source = source;
    }

    public InterviewResults getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(InterviewResults interviewResult) {
        this.interviewResult = interviewResult;
    }

    public Integer getAppliedCandidatesSum() {
        return appliedCandidatesSum;
    }

    public void setAppliedCandidatesSum(Integer appliedCandidatesSum) {
        this.appliedCandidatesSum = appliedCandidatesSum;
    }

    public Integer getAcceptedCandidatesSum() {
        return acceptedCandidatesSum;
    }

    public void setAcceptedCandidatesSum(Integer acceptedCandidatesSum) {
        this.acceptedCandidatesSum = acceptedCandidatesSum;
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }
}
