package services;

import java.util.List;

public class Print {
    public void print(List<ReportModel> reportModels) {
        System.out.print("|Candidates ");
        if (reportModels.get(0).getAcceptedCandidates() != null) {
            System.out.print("|Accepted ");
        }
        if (reportModels.get(0).getAcceptedCandidatesPercent() != null) {
            System.out.print("|Percent Accepted ");
        }
        if (reportModels.get(0).getSource() != null) {
            System.out.print("|Source ");
        }
        if (reportModels.get(0).getInterviewResult() != null) {
            System.out.print("|Result ");
        }
        if (reportModels.get(0).getAggregation() != null) {
            System.out.print("|Aggregation ");
        }
        for (ReportModel reportModel : reportModels) {
            System.out.println();
            System.out.print("|" + reportModel.getAppliedCandidates() + "         ");
            if (reportModel.getAcceptedCandidates() != null) {
                System.out.print("|" + reportModel.getAcceptedCandidates() + "        ");
            }
            if (reportModel.getAcceptedCandidatesPercent() != null) {
                System.out.print("|" + reportModel.getAcceptedCandidatesPercent() + "               ");
            }
            if (reportModel.getSource() != null) {
                System.out.print("|" + reportModel.getSource() + " ");
            }
            if (reportModel.getInterviewResult() != null) {
                System.out.print("|" + reportModel.getInterviewResult() + " ");
            }
            if (reportModel.getAggregation() != null) {
                System.out.print("|" + reportModel.getAggregation() + "");
            }
        }
    }
}
