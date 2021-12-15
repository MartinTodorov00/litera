package services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.ReportLayerImpl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportToExcel {

    private static String path = "C:\\Users\\HP\\Desktop\\PrimeHolding intern\\Excel file\\report";

    public void export(List<ReportModel> reportModels) {
        try {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Report");

            List<String> columnHeadings = new ArrayList<>();

            columnHeadings.add("Candidates");
            if (reportModels.get(0).getAcceptedCandidates() != null) {
                columnHeadings.add("Accepted");
            }
            if (reportModels.get(0).getAcceptedCandidatesPercent() != null) {
                columnHeadings.add("Percent Accepted");
            }
            if (reportModels.get(0).getSource() != null) {
                columnHeadings.add("Source");
            }
            if (reportModels.get(0).getInterviewResult() != null) {
                columnHeadings.add("Result");
            }
            if (reportModels.get(0).getAggregation() != null) {
                columnHeadings.add("Aggregation");
            }

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setColor(IndexedColors.BLACK.index);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < columnHeadings.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeadings.get(i));
                cell.setCellStyle(headerStyle);
            }

            sheet.createFreezePane(0, 1);

            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM/dd/yyyy"));
            int rowNum = 1;
            for (ReportModel reportModel : reportModels) {
                int count = 1;
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reportModel.getAppliedCandidates());
                if (reportModels.get(0).getAcceptedCandidates() != null) {
                    row.createCell(count).setCellValue(reportModel.getAcceptedCandidates());
                    count++;
                }
                if (reportModels.get(0).getAcceptedCandidatesPercent() != null) {
                    row.createCell(count).setCellValue(reportModel.getAcceptedCandidatesPercent());
                    count++;
                }
                if (reportModels.get(0).getSource() != null) {
                    row.createCell(count).setCellValue(reportModel.getSource());
                    count++;
                }
                if (reportModels.get(0).getInterviewResult() != null) {
                    row.createCell(count).setCellValue(reportModel.getInterviewResult());
                    count++;
                }
                if (reportModels.get(0).getAggregation() != null) {
                    row.createCell(count).setCellValue(reportModel.getAggregation());
                }
            }

            int noOfRows = sheet.getLastRowNum();
            sheet.groupRow(1, noOfRows);
            sheet.setRowGroupCollapsed(1, true);

            for (int i = 0; i < columnHeadings.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            ReportLayerImpl reportLayer = new ReportLayerImpl();
            if (reportLayer.getYear() != null) {
                path += "-" + reportLayer.getYear();
                if (reportLayer.getMonth() != null) {
                    path += " " + reportLayer.getMonth();
                }
            }
            if (reportLayer.getAggregationName() != null) {
                path += "-" + reportLayer.getAggregationName();
            }
            path += ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(path);

            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Completed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

