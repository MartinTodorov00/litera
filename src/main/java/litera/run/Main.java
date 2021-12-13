package run;

import CommandLineInterface.Process;
import CommandLineInterface.Config;
import CommandLineInterface.Report;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.sql.SQLException;

@Command(subcommands = {Process.class, Config.class, Report.class}, mixinStandardHelpOptions = true)
public class Main {
    public static void main(String[] args) throws SQLException {
        new CommandLine(new Main()).execute(args);
    }
}
