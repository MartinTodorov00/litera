package entities;

import entities.Application;

import java.util.ArrayList;
import java.util.List;

//import static javax.management.modelmbean.DescriptorSupport.entities;

public class ApplicationModel {

    private List<Application> applications;

    public ApplicationModel() {
        this.applications = new ArrayList<>();
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}