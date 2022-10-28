package dataApplication;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class GetData {

    public String getTestData (String strColumn, int intRow,XSSFSheet sheet){
        String sItem = null;

        XSSFRow row = sheet.getRow(0);
        for (int i = 0; i < columnSize(sheet); i++){
            //System.out.println("====================================================");
            //System.out.println("Column from excel " + i +" Cell "+row.getCell(i).getStringCellValue());
           // System.out.println("Column name from getTestData: " + strColumn);
           // if(row.getCell(i).getRichStringCellValue().equals(strColumn)){
                XSSFRow xRow = sheet.getRow(intRow);
                XSSFCell cell = xRow.getCell(i);
                DataFormatter formater = new DataFormatter();
                sItem = formater.formatCellValue(cell);
           // }
        }
        System.out.println(sItem);
        return sItem;
    }

    private int columnSize(XSSFSheet sheet) {

        return sheet.getRow(0).getLastCellNum();
    }
}
