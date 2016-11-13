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
public class ReferralList extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map map, HSSFWorkbook hssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        HSSFSheet excelSheet = hssfWorkbook.createSheet("Statistic by referrals");
        setExcelHeader(excelSheet);
        List<Referral> statistics = (List) map.get("referralsList");
        setExcelRows(excelSheet,statistics);

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Id");
        excelHeader.createCell(1).setCellValue("Name");
        excelHeader.createCell(2).setCellValue("SecondName");
        excelHeader.createCell(3).setCellValue("Age");
        excelHeader.createCell(4).setCellValue("Count sails");
        excelHeader.createCell(5).setCellValue("Tracker");
        excelHeader.createCell(6).setCellValue("Profit");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<Referral> statistics){
        int record = 1;
        for (Referral referral : statistics) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(referral.getId());
            excelRow.createCell(1).setCellValue(referral.getName());
            excelRow.createCell(2).setCellValue(referral.getInfo().getSecondName());
            excelRow.createCell(3).setCellValue(checkEmptyField(referral.getInfo().getAge()));
            excelRow.createCell(4).setCellValue(referral.getSails().size());
            excelRow.createCell(5).setCellValue(referral.getTracker());
            excelRow.createCell(6).setCellValue(referral.getProfit());
        }
    }

    public String checkEmptyField(Integer check) {
        if (check == null)
            return "";
        return check.toString();
    }

}
