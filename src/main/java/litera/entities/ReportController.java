package entities;

import repositories.ReportInfo;

import java.util.ArrayList;
import java.util.List;

public class ReportController {

    private List<ReportInfo> reportInfos;

    public ReportController() {
        this.reportInfos = new ArrayList<>();
    }

    public void addReportInfo(ReportInfo reportInfo) {
        this.reportInfos.add(reportInfo);
    }

    public List<ReportInfo> getReportInfos() {
        return reportInfos;
    }

    public void setReportInfos(List<ReportInfo> reportInfos) {
        this.reportInfos = reportInfos;
    }
}
