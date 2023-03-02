package com.youzhi.canvas.util;

import java.sql.*;
import java.util.List;

public class MySqlUtil {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String dbDriver = null;
    private String dbConnectionURL = null;
    private String dbUsername = null;
    private String dbPassword = null;

    public MySqlUtil(){
        // update the dataBaseIp/dataBaseName/dbUsername/dbPassword according to the info of real database server
        String dataBaseIp = "34.69.174.9";
        String dataBaseName = "pixel-canvas-usc:us-central1:pixelcanvasusc";
        this.dbUsername = "root";
        this.dbPassword = "gotrojan";

        this.dbDriver = "com.mysql.cj.jdbc.Driver";
        this.dbConnectionURL = "jdbc:mysql://"+dataBaseIp+":3306/"+dataBaseName+"?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";

    }

    private Connection getConnection() {
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbConnectionURL, dbUsername,
                    dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet select(String sql) {
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }

    public int getRecordCount(String sql) {
        int counter = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }
        return counter;
    }


    public int executeupdate(String sql) throws Exception {
        int num = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            num = ps.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            close();
        }
        return num;
    }


    public int executeBatch(List<String> sqlList) {
        int result = 0;
        for (String sql : sqlList) {
            try {
                result += executeupdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MySqlUtil mysqlUtil = new MySqlUtil();
        ResultSet resultSet = mysqlUtil.select("select * from color");
        try {
            while (resultSet.next()){
                System.out.println(resultSet.getInt(0));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            mysqlUtil.close();
        }
    }
}
