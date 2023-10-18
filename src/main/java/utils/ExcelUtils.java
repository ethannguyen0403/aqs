package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelUtils {
    public static List<Map<String, String>> getDataTest(String filePath, String sheetName, List<String> columns, String escapeValue) {
        InputStream input = null;
        List<Map<String, String>> dataEntryList = new ArrayList<>();
        try {
            input = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int numberOfTestData = sheet.getPhysicalNumberOfRows();
            Map<String, String> element;
            for (int i = 1; i < numberOfTestData; i++) {
                Row rowData = sheet.getRow(i);
                if (columns.get(0).equals(getDataFromCell(rowData.getCell(0)))) {
                    Row rowHeader = sheet.getRow(i);
                    for (int j = 1; j < (numberOfTestData - i); j++) {
                        element = new HashMap<>();
                        rowData = sheet.getRow(i + j);
                        if (getDataFromCell(rowData.getCell(0)).equalsIgnoreCase(escapeValue) || escapeValue == null) return dataEntryList;
                        for (int k = 0; k < columns.size(); k++) {
                            int indexExpectedCol = findColumnByName(rowHeader, columns.get(k));
                            String paramsValue = getDataFromCell(rowData.getCell(indexExpectedCol));
                            element.put(columns.get(k), paramsValue);
                        }
                        dataEntryList.add(element);
                    }
                }
            }
            if (workbook != null) workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("ERROR: Can't read excel file: " + filePath);
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) {
            }
        }
        return dataEntryList;
    }


    private static String getDataFromCell(Cell cell) {
        String result = "";
        try {
            result = getCellValue(cell).trim();
        } catch (Exception e) {
        }
        return (result == null) ? "" : result;
    }

    private static int findColumnByName(Row row, String name) {
        for (Cell cell : row) {
            String cellValue = getCellValue(cell);
            if (cellValue == null) {
                return -1;
            }
            if (cellValue.trim().equalsIgnoreCase(name)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    public static String getCellValue(Cell cell) {
        String result = "";
        CellType cellType = cell.getCellType();
        DataFormatter dataFormatter = new DataFormatter();

        switch (cellType) {
            case STRING:
            case NUMERIC:
            case BOOLEAN:
                result = dataFormatter.formatCellValue(cell);
                break;
            case BLANK:
                result = "";
                break;
            default:
                break;
        }
        return result;
    }
}