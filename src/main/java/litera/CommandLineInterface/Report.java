package CommandLineInterface;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import repositories.ReportLayer;

import java.sql.SQLException;

@Command(name = "litera report", description = "Creates a report with the desired data", mixinStandardHelpOptions = true)
public class Report implements Runnable {

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "1")
    private ReportArgGroup argGroup = new ReportArgGroup();

    @Override
    public void run() {
        ReportLayer reportingLayer = new ReportLayer();
        try {
            reportingLayer.check(argGroup);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
