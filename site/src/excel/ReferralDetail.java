package excel;

import models.SailProfit;
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
public class ReferralDetail extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map map, HSSFWorkbook hssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        HSSFSheet excelSheet = hssfWorkbook.createSheet("Referrals detail");
        setExcelHeader(excelSheet);

        List<SailProfit> statistics = (List) map.get("referralSail");
        setExcelRows(excelSheet,statistics);

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Id");
        excelHeader.createCell(1).setCellValue("Date");
        excelHeader.createCell(2).setCellValue("Products profit");
        excelHeader.createCell(3).setCellValue("Total cost");
        excelHeader.createCell(4).setCellValue("Cashback Percent");
        excelHeader.createCell(5).setCellValue("Total Profit");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<SailProfit> statistics){
        int record = 1;
        for (SailProfit sail : statistics) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(sail.getSail().getId());
            excelRow.createCell(1).setCellValue(sail.getSail().getDate());
            excelRow.createCell(2).setCellValue(sail.getProductListWithProfit());
            excelRow.createCell(3).setCellValue(sail.getSail().getTotalsum());
            excelRow.createCell(4).setCellValue(sail.getSail().getCashbackPercent());
            excelRow.createCell(5).setCellValue(sail.getProfit());
        }
    }

}
