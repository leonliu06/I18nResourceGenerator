package com.zhaogang.intl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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

        //File file = new File("E:\\i18nResourceGenerator\\src\\main\\java\\com\\zhaogang\\intl\\i18n.xls");
        File file = new File("i18n.xls");

        if(!file.exists()){
            System.out.println("File doesn't exist!");
            return;
        }

        FileInputStream fileInputStream = new FileInputStream(file);

        Workbook workbook = new HSSFWorkbook(fileInputStream);

        Sheet sheet = workbook.getSheetAt(0);

        int totalRows = sheet.getPhysicalNumberOfRows();

        int keyRow = 0;
        int keyCol = 0;

        boolean foundKEY = false;

        for(int row = 0; row < totalRows; row++){
            for(int col = 0; col < sheet.getRow(row).getLastCellNum(); col++){
                if(sheet.getRow(row).getCell(col) != null && sheet.getRow(row).getCell(col).toString().equals("KEY")){
                    keyRow = row;
                    keyCol = col;
                    foundKEY = true;
                    break;
                }
            }
            if(foundKEY){
                break;
            }
        }
        if(!foundKEY){
            System.out.println("wrong excel format");
            return;
        }

        // key
        List<String> keys = new ArrayList<String>();

        for(int i = keyRow + 1; i < totalRows; i++){
            keys.add(sheet.getRow(i).getCell(keyCol) == null ? "" : sheet.getRow(i).getCell(keyCol).toString());
        }

        for(int col = keyCol + 1; col < sheet.getRow(keyRow).getLastCellNum(); col++){

            if(sheet.getRow(keyRow).getCell(col) == null){
                continue;
            }
            File proFile = new File("message_" + sheet.getRow(keyRow).getCell(col).toString() + ".properties");
            FileOutputStream fos = new FileOutputStream(proFile);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));

            for(int row = keyRow + 1; row < totalRows; row++){
                String value = sheet.getRow(row).getCell(col) == null ? "" : sheet.getRow(row).getCell(col).toString();
                dos.write((keys.get(row - keyRow - 1) + "=" + value + "\r\n").getBytes("utf-8"));
            }
            dos.flush();

        }

        //System.out.println("Success! Please enter any key to end: ");
        //System.in.read();

    }
}
