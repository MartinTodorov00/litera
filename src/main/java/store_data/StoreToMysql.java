package store_data;

import entities.enums.InterviewResults;
import entities.enums.PreSelectionStatuses;
import entities.enums.SelectionResults;
import entities.enums.Sources;
import parse_data.ApplicationModel;

import java.sql.*;
import java.time.LocalDateTime;

public class StoreToMysql {

    public void storeAllDataToMysql(ApplicationModel applications) throws SQLException {

        for (int i = 0; i < applications.getApplications().size(); i++) {
            storeCity(applications, i);
            storeCandidate(applications, i);
            storeTechnology(applications,i);
            storeInterview(applications, i);
            storeApplication(applications, i);
        }
    }

    public void storeCity(ApplicationModel applications, int i) throws SQLException {
        Connection connection = ConnectionJbdc.getConnection();

        String queryForCity = "INSERT INTO city(name)\n" +
                "VALUES (?)";

        PreparedStatement preparedStatementForCity = connection.prepareStatement(queryForCity);

        String city = applications.getApplications().get(i).getCandidate().getCity().getName();

        if (city != null) {
            preparedStatementForCity.setString(1, city);
        }else {
            preparedStatementForCity.setString(1, "");
        }

        preparedStatementForCity.execute();
    }

    public void storeCandidate(ApplicationModel applications, int i) throws SQLException {
        Connection connection = ConnectionJbdc.getConnection();

        String queryForCandidate = "INSERT INTO candidate(name, surname, city_id, email, phone, source)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatementForCandidate = connection.prepareStatement(queryForCandidate);

        String candidateName = applications.getApplications().get(i).getCandidate().getName();
        String candidateSurName = applications.getApplications().get(i).getCandidate().getSurName();
        String candidateEmail = applications.getApplications().get(i).getCandidate().getEmail();
        String candidatePhone = applications.getApplications().get(i).getCandidate().getPhone();
        Sources candidateSource = applications.getApplications().get(i).getCandidate().getSource();


        preparedStatementForCandidate.setString(1, candidateName);
        preparedStatementForCandidate.setString(2, candidateSurName);
        preparedStatementForCandidate.setInt(3, i + 1);
        preparedStatementForCandidate.setString(4, candidateEmail);
        preparedStatementForCandidate.setString(5, candidatePhone);
        preparedStatementForCandidate.setString(6, candidateSource.name());

        preparedStatementForCandidate.execute();
    }

    public void storeTechnology(ApplicationModel applications, int i) throws SQLException {
        Connection connection = ConnectionJbdc.getConnection();

        String queryForTechnology = "INSERT INTO technology(name)\n" +
                "VALUES (?)";

        PreparedStatement preparedStatementForTechnology = connection.prepareStatement(queryForTechnology);

        String technology = applications.getApplications().get(i).getTechnology().getName();

        if (technology != null) {
            preparedStatementForTechnology.setString(1, technology);
        }else {
            preparedStatementForTechnology.setString(1, "");
        }

        preparedStatementForTechnology.execute();
    }

    public void storeInterview(ApplicationModel applications, int i) throws SQLException {
        Connection connection = ConnectionJbdc.getConnection();

        String queryForInterview = "INSERT INTO interview(interview_date, interview_result)\n" +
                "VALUES (?, ?)";

        PreparedStatement preparedStatementForInterview = connection.prepareStatement(queryForInterview);

        LocalDateTime interviewDate = applications.getApplications().get(i).getInterviewDateAndTime();
        InterviewResults interviewResult = applications.getApplications().get(i).getInterviewResult();

        if (interviewDate != null) {
            preparedStatementForInterview.setTimestamp(1, Timestamp.valueOf(interviewDate));
        }
        if (interviewResult != null) {
            preparedStatementForInterview.setString(2, interviewResult.name());
        }else {
            preparedStatementForInterview.setString(2, "");
        }

        preparedStatementForInterview.execute();
    }

    public void storeApplication(ApplicationModel applications, int i) throws SQLException {
        Connection connection = ConnectionJbdc.getConnection();

        String queryForApplication = "INSERT INTO application(`pre-selection_status`, `selection_result`, candidate_id, technology_id, interview_id)\n" +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatementForApplication = connection.prepareStatement(queryForApplication);

        PreSelectionStatuses preSelectionStatus = applications.getApplications().get(i).getPreSelectionStatus();
        SelectionResults selectionResult = applications.getApplications().get(i).getSelectionResult();

        if (preSelectionStatus != null) {
            preparedStatementForApplication.setString(1, preSelectionStatus.name());
        }else {
            preparedStatementForApplication.setString(1, "");
        }
        if (selectionResult != null) {
            preparedStatementForApplication.setString(2, selectionResult.name());
        }else {
            preparedStatementForApplication.setString(2, "");
        }
        preparedStatementForApplication.setInt(3, i + 1);
        preparedStatementForApplication.setInt(4, i + 1);
        preparedStatementForApplication.setInt(5, i + 1);

        preparedStatementForApplication.execute();
    }
}
