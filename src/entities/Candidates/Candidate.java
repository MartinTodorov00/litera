package entities.Candidates;

public class Candidate {

    private String name;
    private String surName;
    private String city;
    private String email;
    private String phone;
    private String source;

    public Candidate(String name, String surName, String city, String email, String phone, String source) {
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
}
