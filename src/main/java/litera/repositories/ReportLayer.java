package repositories;

import CommandLineInterface.ReportArguments;

import java.sql.SQLException;

public interface ReportLayer {

    void check(ReportArguments argGroup) throws SQLException;

    void getDataFromDatabase(StringBuilder querySb, ReportArguments argGroup, boolean haveSource, boolean haveResult, boolean haveName)
            throws SQLException;
}
