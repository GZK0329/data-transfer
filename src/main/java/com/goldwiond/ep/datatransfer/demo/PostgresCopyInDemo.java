package com.goldwiond.ep.datatransfer.demo;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresCopyInDemo {

    private static String url = "jdbc:postgresql://localhost:5432/pgtest";

    private static String user = "postgres";

    private static String password = "123456";

    public static void main(String[] args) {

        //id,sex,name,birthday,geom,create_time,update_time\n"

        //3|1|张三|2021-08-03 23:22:31|2021-08-03 23:20:39|

        //4|1|赵丽丽|2021-05-03 23:24:16|2021-05-03 23:24:20|

        String studentTable = "student";

        List<String> studentList = new ArrayList<>();

        studentList.add("11|1|张三|2021-08-07 23:24:16|2021-05-03 23:24:20|");

        studentList.add("12|1|李四|2021-08-07 23:24:16|2021-05-03 23:24:20|");

        StringBuilder stringBuilder = new StringBuilder();

        studentList.forEach(m -> stringBuilder.append(m + "\r\n"));

        try (Connection connection = DriverManager.getConnection(url, user, password);
             InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8))) {

            log.info("init PGConnectPool.");

            CopyManager copyManager = new CopyManager((BaseConnection) connection);

            String copyIn = "COPY " + studentTable + " FROM STDIN DELIMITER E'|' CSV";

            long copyInLine = copyManager.copyIn(copyIn, inputStream);

            log.info("import db copyInLine={}", copyInLine);

        } catch (Exception exception) {

            exception.printStackTrace();
        }

    }
}
