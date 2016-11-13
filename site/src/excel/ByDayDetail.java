package excel;

import models.Referral;
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
public class ByDayDetail extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map map, HSSFWorkbook hssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        HSSFSheet excelSheet = hssfWorkbook.createSheet("Statistic by day detail");
        setExcelHeader(excelSheet);
        List<Referral> statistics = (List) map.get("ByDayDetail");
        setExcelRows(excelSheet,statistics);

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Id");
        excelHeader.createCell(1).setCellValue("Sails");
        excelHeader.createCell(2).setCellValue("Profit");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<Referral> statistics){
        int record = 1;
        for (Referral referral : statistics) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(referral.getId());
            excelRow.createCell(1).setCellValue(referral.sailsToString());
            excelRow.createCell(2).setCellValue(referral.getProfit());
        }
    }

}
