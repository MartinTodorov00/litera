package repositories;

import CommandLineInterface.ReportArguments;
import services.ConnectionJbdc;
import services.ReportModel;
import services.Report;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportLayerImpl implements ReportLayer {

    private StringBuilder querySb = new StringBuilder("");
    private ReportModel reportController = new ReportModel();

    public void check(ReportArguments argGroup) throws SQLException {

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
        boolean haveSource = false;
        boolean haveResult = false;
        boolean haveName = false;

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
            querySb.append("""
                    SELECT COUNT(*) AS accepted, (SELECT COUNT(*) as applied FROM candidate as c
                    INNER JOIN application as a on c.id = a.candidate_id
                    WHERE a.selection_result = "ACCEPTED") AS applied FROM application AS a
                    INNER JOIN candidate c on c.id = a.candidate_id
                    INNER JOIN city as ci on c.city_id = ci.id
                    INNER JOIN interview as i on a.interview_id = i.id
                    INNER JOIN technology as t on a.technology_id = t.id
                    """);
        } else {
            querySb.append("SELECT COUNT(*) AS applied FROM application as a\n" +
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
            haveSource = true;
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
            haveResult = true;
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
            haveName = true;
            querySb.append("' ");
        }
        getDataFromDatabase(querySb, argGroup, haveSource, haveResult, haveName);
    }

    public void getDataFromDatabase(StringBuilder querySb, ReportArguments argGroup, boolean haveSource, boolean haveResult,
                                    boolean haveName) throws SQLException {

        try {
            Connection connection = ConnectionJbdc.getConnection("litera");

            PreparedStatement prepareStatement = connection.prepareStatement(String.valueOf(querySb));

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Report reportInfo = new Report();
                reportInfo.setAppliedCandidatesSum(resultSet.getInt("applied"));
                if (argGroup.isTalentFunnel()) {
                    reportInfo.setAcceptedCandidatesSum(resultSet.getInt("accepted"));
                }
//                if (haveSource) {
//                    reportInfo.setSource(Sources.valueOf(resultSet.getString("source")));
//                }
//                if (haveResult) {
//                    reportInfo.setInterviewResult(InterviewResults.valueOf(resultSet.getString("interview_result")));
//                }
//                if (haveName) {
//                    reportInfo.setAggregation(resultSet.getString("name"));
//                }
                reportController.addReportInfo(reportInfo);
            }
        } catch (SQLException | IOException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
