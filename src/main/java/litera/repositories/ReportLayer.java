package repositories;

import CommandLineInterface.ReportArgGroup;
import entities.enums.InterviewResults;
import entities.enums.Sources;
import entities.ReportController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportLayer {

    private StringBuilder querySb = new StringBuilder("");
    ReportController reportController = new ReportController();

    public void check(ReportArgGroup argGroup) throws SQLException {

        String year = null;
        String month = null;
        boolean haveDate = false;
        String firstDate;
        String lastDate;
        String allSources = null;
        String sourceName = null;
        boolean haveSourceGroup = false;
        boolean haveSourceWhere = false;
        String allStatuses = null;
        String statusName = null;
        boolean haveStatusGroup = false;
        String aggregationName = null;

        String[] period = null;
        if (!(argGroup.getPeriod() == null)) {
            period = argGroup.getPeriod().split("-");
            year = period[0];
            if (period.length == 2) {
                month = period[1];
            }
        }

        String[] source;
        if (!(argGroup.getSource() == null)) {
            source = argGroup.getSource().split(" ");
            allSources = source[0];
            if (source.length == 2) {
                sourceName = source[1];
            }
        }

        String[] status;
        if (!(argGroup.getStatus() == null)) {
            status = argGroup.getStatus().split(" ");
            allStatuses = status[0];
            if (status.length == 2) {
                statusName = status[1];
            }
        }

        if (argGroup.getAggregation() != null) {
            aggregationName = argGroup.getAggregation();
        }

        if (argGroup.isTalentFunnel()) {
            querySb.append("SELECT COUNT(*) AS accepted FROM\n" +
                    "(SELECT COUNT(*) as applied FROM candidate as c\n" +
                    "INNER JOIN application as a on c.id = a.candidate_id\n" +
                    "WHERE a.selection_result = \"ACCEPTED\") as ac\n" +
                    "INNER JOIN application as a2\n" +
                    "INNER JOIN candidate c2 on c2.id = a2.candidate_id\n" +
                    "INNER JOIN city as ci on c2.city_id = ci.id\n" +
                    "INNER JOIN interview as i on a2.interview_id = i.id\n" +
                    "INNER JOIN technology as t on a2.technology_id = t.id\n" +
                    "");
        } else {
            querySb.append("SELECT COUNT(*) AS count FROM application as a\n" +
                    "INNER JOIN candidate as c on a.candidate_id = c.id\n" +
                    "INNER JOIN city as ci on c.city_id = ci.id\n" +
                    "INNER JOIN interview as i on a.interview_id = i.id\n" +
                    "INNER JOIN technology as t on a.technology_id = t.id\n" +
                    "");
        }


        if (period != null) {
            if (period.length == 1) {
                querySb.append(" WHERE YEAR(`interview_date`) = '").append(year).append("'");
                haveDate = true;
            }
            if (period.length == 2) {
                if (month.equals("spring")) {
                    firstDate = "03-01";
                    lastDate = "05-31";
                } else if (month.equals("summer")) {
                    firstDate = "06-01";
                    lastDate = "08-31";
                } else if (month.equals("fall") || (month.equals("autumn"))) {
                    firstDate = "09-01";
                    lastDate = "11-31";
                } else {
                    firstDate = "12-01";
                    lastDate = "02-29";
                }
                querySb.append(" WHERE YEAR(`interview_date`) = '").append(year).append("' ")
                        .append("AND MONTH(`interview_date`) between ").append(firstDate).append(" and ")
                        .append(lastDate);
                haveDate = true;
            }
        }

        if (argGroup.isTalentFunnel()) {
            if (haveDate) {
                querySb.append(" AND `selection_result` = '").append("ACCEPTED").append("' ");
            } else {
                querySb.append(" WHERE `selection_result` = '").append("ACCEPTED").append("' ");
            }
        }

        assert allSources != null;
        if (!allSources.equals("none") || sourceName != null) {
            if (sourceName == null) {
                querySb.append(" GROUP BY 'source");
                haveSourceGroup = true;
//            querySb.append("" + allSources + "");
            }
            if (sourceName != null && haveDate) {
                querySb.append(" AND c.source = '");
                querySb.append("" + sourceName + "");
                haveSourceWhere = true;
            } else if (sourceName != null && !haveDate) {
                querySb.append(" WHERE c.source = '");
                querySb.append("" + sourceName + "");
                haveSourceWhere = true;
            }
            querySb.append("' ");
        }

        assert allStatuses != null;
        if (!allStatuses.equals("none") || statusName != null) {
            if (statusName == null && haveSourceGroup) {
                querySb.append(" AND 'interview_result");
                haveStatusGroup = true;
            } else if (statusName == null && !haveSourceGroup) {
                querySb.append(" GROUP BY 'interview_result");
                haveStatusGroup = true;
            }
            if (statusName != null && ((haveSourceWhere || haveDate) || (haveSourceWhere && haveDate))) {
                querySb.append(" AND i.interview_result = '");
                querySb.append("" + statusName + "");
            } else if (statusName != null && !((haveSourceWhere || haveDate) || !(haveSourceWhere && haveDate))) {
                querySb.append(" WHERE i.interview_result = '");
                querySb.append("" + statusName + "");
            }
            querySb.append("' ");
        }

        if (aggregationName != null) {
            if (aggregationName.equals("technology")) {
                if (haveSourceGroup || haveStatusGroup || haveSourceGroup && haveStatusGroup) {
                    querySb.append(" AND '" + aggregationName);
                } else {
                    querySb.append(" GROUP BY '" + aggregationName);
                }
            } else {
                if (haveSourceGroup || haveStatusGroup || haveSourceGroup && haveStatusGroup) {
                    querySb.append(" AND '" + aggregationName);
                } else {
                    querySb.append(" GROUP BY '" + aggregationName);
                }
            }
            querySb.append("' ");
        }
        getDataFromDatabase(querySb);
    }

    public void getDataFromDatabase(StringBuilder querySb) throws SQLException {

        try {
            Connection connection = ConnectionJbdc.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement(String.valueOf(querySb));

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                ReportInfo reportInfo = new ReportInfo();
                reportInfo.setAppliedCandidatesSum(resultSet.getInt("applied"));
                reportInfo.setAcceptedCandidatesSum(resultSet.getInt("accepted"));
                reportInfo.setSource(Sources.valueOf(resultSet.getString("source")));
                reportInfo.setInterviewResult(InterviewResults.valueOf(resultSet.getString("interview_result")));
                reportInfo.setAggregation(resultSet.getString("name"));

                reportController.addReportInfo(reportInfo);
            }
        } catch (SQLException | IOException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
