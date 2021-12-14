package CommandLineInterface;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.File;

@Command(name = "litera config", description = "Creates a directory where reports will be stored",
        mixinStandardHelpOptions = true)
public class Config implements Runnable {

    @Option(names = "--export-dir", required = true,
            description = "A directory in which the exported .xlsx files will be stored. " +
                    "Directory will be created if it doesn't exist")
    private String path;

    @Override
    public void run() {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
