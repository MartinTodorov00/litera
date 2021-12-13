package CommandLineInterface;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import repositories.ReportLayerImpl;

import java.sql.SQLException;

@Command(name = "litera report", description = "Creates a report with the desired data", mixinStandardHelpOptions = true)
public class Report implements Runnable {

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "1")
    private ReportArguments arguments = new ReportArguments();

    @Override
    public void run() {
        ReportLayerImpl reportingLayer = new ReportLayerImpl();
        try {
            reportingLayer.check(arguments);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
