package services;

import java.util.ArrayList;
import java.util.List;

public class ReportModel {

    private List<Report> reportInfos;

    public ReportModel() {
        this.reportInfos = new ArrayList<>();
    }

    public void addReportInfo(Report reportInfo) {
        this.reportInfos.add(reportInfo);
    }

    public List<Report> getReportInfos() {
        return reportInfos;
    }

    public void setReportInfos(List<Report> reportInfos) {
        this.reportInfos = reportInfos;
    }
}
