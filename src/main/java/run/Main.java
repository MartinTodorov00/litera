package run;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.ArgGroup;
import store_data.StoreToMysql;

import java.io.File;
import java.sql.SQLException;

@Command(name = "litera")
public class Main implements Runnable {

    public static void main(String[] args) throws SQLException {

        new CommandLine(new Main()).execute(args);

    }

    @Command(name = "process", description = "Reads from a CSV file, creates and fills a database.")
    private static void process() throws SQLException {
        StoreToMysql storeToMysql = new StoreToMysql();
        storeToMysql.storeAllDataToMysql();
    }

    @Command(name = "config", description = "Creates a directory where reports will be stored",
            mixinStandardHelpOptions = true)
    private void config(
            @Option(names = "--export-dir", required = true,
                    description = "A directory in which the exported .xlsx files will be stored. " +
                            "Directory will be created if it doesn't exist") String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Command(name = "report", description = "Creates a report with the desired data", mixinStandardHelpOptions = true)
    private void report(
            @ArgGroup(exclusive = false, multiplicity = "1")
                    ReportArgGroup argGroup
    ) {
        // Generate reports
    }

    static class ReportArgGroup {
        @Option(names = {"--period", "-p"}) String period;
        @Option(names = "--source", arity = "0..1", defaultValue = "none") String source;
        @Option(names = "--interview", arity = "0..1", defaultValue = "none") String status;
        @Option(names = "--talent-funnel", description = "The number of candidates who applied " +
                        "and the number of those who accepted an internship ") boolean talentFunnel;
        @Option(names = "--aggregation", description = "Grouped by either city or technology") String aggregation;
    }

    @Override
    public void run() {

    }
}
