package com.example.task.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.VariableElement;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Created by liq on 2019/5/7.
 * excel的操作
 */
public class Excel {

    public static void saveExcel(List<String> lists) throws IOException {

        File excel = new File("d:/task.xls");
        FileOutputStream fos = new FileOutputStream(excel);

        // 文件不存在,创建excel
        if (!excel.exists()) {
            excel.mkdir();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            HSSFSheet sheet = hssfWorkbook.createSheet();
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("发送方");
            titleRow.createCell(1).setCellValue("消息内容");

        }
        if(null != lists && lists.size() > 0) {
            FileInputStream fis = new FileInputStream(excel);
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fis);

            // 获取文档对象
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);


            // 获取工作表
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            // 追加数据
            for (String list : lists) {
                HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue("服务器");
                row.createCell(1).setCellValue(list);

            }
            // 写入数据，关闭流
            fos.flush();
            hssfWorkbook.write(fos);
            fos.close();
        }


    }

}
