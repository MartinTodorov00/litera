package CommandLineInterface;

import picocli.CommandLine.Command;
import repositories.StoreToMysql;

import java.io.IOException;
import java.sql.SQLException;

@Command(name = "litera process", description = "Reads from a CSV file, creates and fills a database.",
        mixinStandardHelpOptions = true)
public class Process implements Runnable {

    @Override
    public void run() {
        try {
            new StoreToMysql().storeAllDataToMysql();
        } catch (SQLException | IOException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
