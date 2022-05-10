package com.goldwiond.ep.datatransfer.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVFile {

    /**
     * CSV文件生成方法
     *
     * @param head       文件头
     * @param dataList   数据列表
     * @param outPutPath 文件输出路径
     * @param filename   文件名
     * @return
     */
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList, String outPutPath, String filename) {
        File csvFile = new File(outPutPath + File.separator + filename + ".csv");

        try (BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                csvFile), "GB2312"), 1024)) {

            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","

            // 写入文件头部
            writeRow(head, csvWriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWriter);
            }
            csvWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuilder sb = new StringBuilder();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
//            csvWriter.newLine();
        }
        csvWriter.newLine();
    }


    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        List<Object> exportData = new ArrayList<>();
        exportData.add("第一列");
        exportData.add("第二列");
        exportData.add("第三列");
        List<List<Object>> datalist = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        data.add("123");
        data.add("222");
        data.add("333");
        List<Object> data1 = new ArrayList<>();
        data1.add("444");
        data1.add("555");
        data1.add("666");
        datalist.add(data);
        datalist.add(data1);
        String path = "d:/exportCsv/";
        String fileName = "文件导出";

        File file = new CSVFile().createCSVFile(exportData, datalist, path, fileName);
        String fileName2 = file.getName();
        log.info("文件名称：" + fileName2);
    }
}
