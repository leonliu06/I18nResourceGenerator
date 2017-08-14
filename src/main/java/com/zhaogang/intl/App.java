package com.zhaogang.intl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException
    {

//        if(args == null || args.length < 1){
//            System.out.println("No File!");
//            return;
//        }
//
//        if(!args[0].endsWith("xls") && !args[0].endsWith("xlsx")){
//            System.out.println("Excel file required!");
//            return;
//        }
//
//        System.out.println( args[0] );


        //Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe");

        //System.out.println("Enter any key to start: ");
        //System.out.println(System.in.read());

        //File file = new File("E:\\i18nResourceGenerator\\src\\main\\java\\com\\zhaogang\\intl\\国际化模版.xls");
        File file = new File("国际化模版.xls");

        if(!file.exists()){
            System.out.println("File doesn't exist!");
            return;
        }



        FileInputStream fileInputStream = new FileInputStream(file);

        Workbook workbook = new HSSFWorkbook(fileInputStream);

        Sheet sheet = workbook.getSheetAt(0);

        int totalRows = sheet.getPhysicalNumberOfRows();

        int zhCNRow = 0;
        int zhCNCol = 0;

        boolean foundZhCN = false;

        for(int row = 0; row < totalRows; row++){
            for(int col = 0; col < sheet.getRow(row).getLastCellNum(); col++){
                if(sheet.getRow(row).getCell(col) != null && sheet.getRow(row).getCell(col).toString().equals("zh_CN")){
                    zhCNRow = row;
                    zhCNCol = col;
                    foundZhCN = true;
                    break;
                }
            }
            if(foundZhCN){
                break;
            }
        }
        if(!foundZhCN){
            System.out.println("模版格式错误！");
            return;
        }

        // key
        List<String> keys = new ArrayList<String>();

        for(int i = zhCNRow + 1; i < totalRows; i++){
            keys.add(sheet.getRow(i).getCell(zhCNCol) == null ? "" : sheet.getRow(i).getCell(zhCNCol).toString());
        }

        for(int col = zhCNCol; col < sheet.getRow(zhCNRow).getLastCellNum(); col++){

            if(sheet.getRow(zhCNRow).getCell(col) == null){
                continue;
            }
            File proFile = new File("message_" + sheet.getRow(zhCNRow).getCell(col).toString() + ".properties");
            FileOutputStream fos = new FileOutputStream(proFile);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));

            for(int row = zhCNRow + 1; row < totalRows; row++){
                String value = sheet.getRow(row).getCell(col) == null ? "" : sheet.getRow(row).getCell(col).toString();
                dos.write((keys.get(row - zhCNRow - 1) + "=" + value + "\r\n").getBytes("utf-8"));
            }
            dos.flush();

        }

        //System.out.println("Success! Please enter any key to end: ");
        //System.in.read();

    }
}
