package parse_data;

import entities.Application;

import java.util.ArrayList;

public class Applications {

    private ArrayList<Application> applications;

    public Applications() {
        this.applications = new ArrayList<>();
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public ArrayList<Application> getApplications() {
        return this.applications;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }
}