package com.goldwiond.ep.datatransfer.demo;
import java.sql.*;

public class Main {

    public static void main(String []args) {
        Connection connection=null;
        Statement statement =null;
        try{
            String url="jdbc:postgresql://10.200.50.142:5432/env_soam";
            String user="mxadmin";
            String password = "mxadmin";
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection(url, user, password);

            // 创建表
            String sql="CREATE TABLE data_test ("
                    + "time timestamp,"
                    + "tag_id int,"
                    + "metrics1 float8,"
                    + "metrics2 float8,"
                    + "metrics3 float8"
                    + ")Distributed by(tag_id)";
            statement=connection.createStatement();
            statement.execute(sql);

            // 插入数据
            sql = "INSERT INTO data_test VALUES(now(), 1, 1.1, 1.2, 1.3)";
            statement.execute(sql);

            // 查询结果
            sql = "SELECT * FROM data_test";
            ResultSet resultSet=statement.executeQuery(sql);
            while(resultSet.next()){
                String ts=resultSet.getString(1);
                int tag_id = resultSet.getInt(2);
                double metrics1 = resultSet.getDouble(3);
                double metrics2 = resultSet.getDouble(4);
                double metrics3 = resultSet.getDouble(5);
                System.out.printf("%s %d %f %f %f\n", ts, tag_id, metrics1, metrics2, metrics3);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            try{
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }finally{
                try{
                    connection.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
