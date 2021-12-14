package repositories;

import CommandLineInterface.ReportArguments;
import services.ConnectionJbdc;
import services.Print;
import services.ReportModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportLayerImpl implements ReportLayer {

    private final StringBuilder querySb = new StringBuilder();
    private final List<ReportModel> reportModels = new ArrayList<>();

    public void check(ReportArguments argGroup) throws SQLException {

        String year = null;
        String month = null;
        String firstDate;
        String lastDate;
        String allSources = null;
        String sourceName = null;
        String allStatuses = null;
        String statusName = null;
        String aggregationName = null;
        boolean isUsedWhere = false;
        boolean isUsedGroup = false;
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
                    SELECT COUNT(*) AS accepted, c.source, t.name, c.name, i.interview_result, (SELECT COUNT(*) as applied
                    FROM candidate as c
                    INNER JOIN application as a on a.candidate_id = c.id
                    WHERE a.selection_result = "ACCEPTED") AS applied FROM candidate as c
                    INNER JOIN application as a on a.candidate_id = c.id
                    INNER JOIN city as ci on c.city_id = ci.id
                    INNER JOIN interview as i on a.interview_id = i.id
                    INNER JOIN technology as t on a.technology_id = t.id
                    """);
        } else {
            querySb.append("""
                    SELECT COUNT(*) AS applied, c.source, t.name, c.name, i.interview_result FROM candidate as c
                    INNER JOIN application as a on a.candidate_id = c.id
                    INNER JOIN city as ci on c.city_id = ci.id
                    INNER JOIN interview as i on a.interview_id = i.id
                    INNER JOIN technology as t on a.technology_id = t.id
                    """);
        }

        if (period != null) {
            if (period.length == 1) {
                querySb.append(" WHERE YEAR(`interview_date`) = '").append(year).append("'");
                isUsedWhere = true;
            }
            if (period.length == 2) {
                switch (month) {
                    case "spring" -> {
                        firstDate = "03-01";
                        lastDate = "05-31";
                    }
                    case "summer" -> {
                        firstDate = "06-01";
                        lastDate = "08-31";
                    }
                    case "fall", "autumn" -> {
                        firstDate = "09-01";
                        lastDate = "11-31";
                    }
                    default -> {
                        firstDate = "12-01";
                        lastDate = "02-29";
                    }
                }
                querySb.append(" WHERE YEAR(`interview_date`) = '").append(year).append("' ")
                        .append("AND MONTH(`interview_date`) between ").append(firstDate).append(" and ")
                        .append(lastDate);
                isUsedWhere = true;
            }
        }

        if (allSources != null) {
            if (!allSources.equals("none") || sourceName != null) {
                if (sourceName == null) {
                    querySb.append(" GROUP BY c.source");
                    isUsedGroup = true;
                }
                if (sourceName != null && isUsedWhere) {
                    querySb.append(" AND c.source = '");
                    querySb.append("" + sourceName + "'");
                } else if (sourceName != null) {
                    querySb.append(" WHERE c.source = '");
                    querySb.append("" + sourceName + "'");
                    isUsedWhere = true;
                }
                haveSource = true;
            }
        }

        if (allStatuses != null) {
            if (!allStatuses.equals("none") || statusName != null) {
                if (statusName == null && isUsedGroup) {
                    querySb.append(", i.interview_result");
                } else if (statusName == null) {
                    querySb.append(" GROUP BY interview_result");
                    isUsedGroup = true;
                }
                if (statusName != null && isUsedWhere) {
                    querySb.append(" AND i.interview_result = '");
                    querySb.append("" + statusName + "");
                } else if (statusName != null) {
                    querySb.append(" WHERE i.interview_result = '");
                    querySb.append("" + statusName + "'");
                }
                haveResult = true;
            }
        }

        if (aggregationName != null) {
            if (aggregationName.equals("technology")) {
                if (isUsedGroup) {
                    querySb.append(", t.name");
                } else {
                    querySb.append(" GROUP BY t.name");
                }
            } else {
                if (isUsedGroup) {
                    querySb.append(", c.name");
                } else {
                    querySb.append(" GROUP BY c.name");
                }
            }
            haveName = true;
        }
        getDataFromDatabase(querySb, argGroup, haveSource, haveResult, haveName);
    }

    public void getDataFromDatabase(StringBuilder querySb, ReportArguments argGroup, boolean haveSource, boolean haveResult,
                                    boolean haveName) {
        try {
            Connection connection = ConnectionJbdc.getConnection(StoreDataImpl.schema);

            PreparedStatement prepareStatement = connection.prepareStatement(String.valueOf(querySb));

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                ReportModel reportModel = new ReportModel();
                reportModel.setAppliedCandidates(resultSet.getInt("applied"));
                if (argGroup.isTalentFunnel()) {
                    reportModel.setAcceptedCandidates(resultSet.getInt("accepted"));
                }
                if (haveSource) {
                    reportModel.setSource(String.valueOf(resultSet.getString("source")));
                }
                if (haveResult) {
                    reportModel.setInterviewResult(String.valueOf(resultSet.getString("interview_result")));
                }
                if (haveName) {
                    reportModel.setAggregation(resultSet.getString("name"));
                }
                reportModels.add(reportModel);
            }
            Print print = new Print();
            print.print(reportModels);
        } catch (SQLException | IOException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
