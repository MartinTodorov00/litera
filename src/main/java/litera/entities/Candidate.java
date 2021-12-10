package entities;

import entities.enums.Sources;

public class Candidate {

    private String name;
    private String surName;
    private City city;
    private String email;
    private String phone;
    private Sources source;

    public Candidate() {

    }

    public Candidate(String name, String surName, City city, String email, String phone, Sources source) {
        this.name = name;
        this.surName = surName;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.source = source;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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

    public Sources getSource() {
        return source;
    }

    public void setSource(Sources source) {
        this.source = source;
    }
}
