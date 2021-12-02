package domain_model;

import com.opencsv.bean.CsvBindByName;

public class DomainModel {

    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Surname")
    private String surName;
    @CsvBindByName(column = "City")
    private String city;
    @CsvBindByName(column = "Email")
    private String email;
    @CsvBindByName(column = "Phone")
    private String phone;
    @CsvBindByName(column = "Source")
    private String source;
    @CsvBindByName(column = "Technology")
    private String technology;
    @CsvBindByName(column = "Pre-selection Status")
    private String preSelectionStatus;
    @CsvBindByName(column = "Interview Date")
    private String interviewDateAndTime;
    @CsvBindByName(column = "Interview Result")
    private String interviewResult;
    @CsvBindByName(column = "Selection Result")
    private String selectionResult;

    public DomainModel(String name, String surName, String city, String email, String phone, String source,
                       String technology, String preSelectionStatus, String interviewDateAndTime,
                       String interviewResult, String selectionResult) {
        this.name = name;
        this.surName = surName;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.source = source;
        this.technology = technology;
        this.preSelectionStatus = preSelectionStatus;
        this.interviewDateAndTime = interviewDateAndTime;
        this.interviewResult = interviewResult;
        this.selectionResult = selectionResult;
    }

    public DomainModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getPreSelectionStatus() {
        return preSelectionStatus;
    }

    public void setPreSelectionStatus(String preSelectionStatus) {
        this.preSelectionStatus = preSelectionStatus;
    }

    public String getInterviewDateAndTime() {
        return interviewDateAndTime;
    }

    public void setInterviewDateAndTime(String interviewDateAndTime) {
        this.interviewDateAndTime = interviewDateAndTime;
    }

    public String getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(String interviewResult) {
        this.interviewResult = interviewResult;
    }

    public String getSelectionResult() {
        return selectionResult;
    }

    public void setSelectionResult(String selectionResult) {
        this.selectionResult = selectionResult;
    }
}
