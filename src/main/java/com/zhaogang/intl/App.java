package com.zhaogang.intl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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


        System.out.println("Enter any key to begin extracting: ");
        System.in.read();

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入i18x.xls文件全路径：");
//        String line = scanner.nextLine();


        File file = new File("i18n.xls");

        if(!file.exists()){
            System.out.println("File named i18n.xls doesn't exist, please move it to the directory where startup.bat or startup.sh resides!");
            System.out.println("Enter any key to terminate the program and restart it after fixing that.");
            System.in.read();
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
            System.out.println("Wrong excel format, please use the given template as same as resources/i18n.xls!");
            System.out.println("Enter any key to terminate the program and restart it after fixing that.");
            System.out.println();
            System.in.read();
            return;
        }

        // key
        List<String> keys = new ArrayList<String>();

        for(int i = keyRow + 1; i < totalRows; i++){
            keys.add(sheet.getRow(i).getCell(keyCol) == null ? "" : sheet.getRow(i).getCell(keyCol).toString());
        }

        System.out.println("Extracting ...");

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

        System.out.println("Success!" + "\n" + "Please enter any key to quit: ");
        System.in.read();

    }
}
