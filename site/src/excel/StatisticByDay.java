package excel;

import dto.ReportByDay;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class StatisticByDay extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map map, HSSFWorkbook hssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        HSSFSheet excelSheet = hssfWorkbook.createSheet("Statistic by days");
        setExcelHeader(excelSheet);
        List<ReportByDay> statistics = (List) map.get("statisticList");
        setExcelRows(excelSheet,statistics);

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Date");
        excelHeader.createCell(1).setCellValue("amount click");
        excelHeader.createCell(2).setCellValue("amount enter refer code");
        excelHeader.createCell(3).setCellValue("amount registration");
        excelHeader.createCell(4).setCellValue("profit");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<ReportByDay> statistics){
        int record = 1;
        for (ReportByDay report : statistics) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(report.getDateView());
            excelRow.createCell(1).setCellValue(report.getClickLinkAmount());
            excelRow.createCell(2).setCellValue(report.getEnterCodeAmount());
            excelRow.createCell(3).setCellValue(report.getRegistrationAmount());
            excelRow.createCell(4).setCellValue(report.getProfit().doubleValue());
        }
    }
}
