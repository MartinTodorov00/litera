package parse_data;

import entities.Application;

import java.util.ArrayList;

public class Applications {

    private ArrayList<Application> ApplicationsRepository;

    public Applications() {
        this.ApplicationsRepository = new ArrayList<>();
    }

    public void addApplication(Application application) {
        this.ApplicationsRepository.add(application);
    }

    public ArrayList<Application> getApplications() {
        return this.ApplicationsRepository;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.ApplicationsRepository = applications;
    }
}