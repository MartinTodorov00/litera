package CommandLineInterface;

import picocli.CommandLine;

public class ReportArgGroup {
    @CommandLine.Option(names = {"--period", "-p"})
    String period;
    @CommandLine.Option(names = "--source", arity = "0..1", defaultValue = "none")
    String source;
    @CommandLine.Option(names = "--interview", arity = "0..1", defaultValue = "none")
    String status;
    @CommandLine.Option(names = "--talent-funnel", description = "The number of candidates who applied " +
            "and the number of those who accepted an internship ")
    boolean talentFunnel;
    @CommandLine.Option(names = "--aggregation", description = "Grouped by either city or technology")
    String aggregation;

    public String getPeriod() {
        return period;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
        return status;
    }

    public boolean isTalentFunnel() {
        return talentFunnel;
    }

    public String getAggregation() {
        return aggregation;
    }
}
