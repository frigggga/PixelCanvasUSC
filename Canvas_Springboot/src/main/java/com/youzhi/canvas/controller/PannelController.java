package com.youzhi.canvas.controller;

import com.youzhi.canvas.bean.ColorInfo;
import com.youzhi.canvas.util.MySqlUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.ArrayList;

@RestController
@RequestMapping("panel")
public class PannelController {
    @RequestMapping("drawColor") //http://127.0.0.1:8080/panel/drawColor?x=120&y=110&color=red
    public String drawColor(String x_coordinate, String y_coordinate, String color, String email) throws Exception{
        if(x_coordinate==null || y_coordinate==null || color==null || email ==null){
            return "fail";
        }
        MySqlUtil mySqlUtil = new MySqlUtil();
        System.out.println("insert into canvas_info(x_coordinate,y_coordinate,color,email) VALUES("+x_coordinate+","+y_coordinate+",'"+color+"','"+email+"')");
        int num = mySqlUtil.executeupdate("insert into canvas_info(x_coordinate,y_coordinate,color,email) VALUES("+x_coordinate+","+y_coordinate+",'"+color+"','"+email+"')");
        if (num == 0){
            return "fail";
        }else {
            return "success";
        }
    }

    @RequestMapping("getColors") //http://127.0.0.1:8080/panel/getColors
    public ArrayList<ColorInfo> getColors() throws Exception{
        MySqlUtil mySqlUtil = new MySqlUtil();
        ResultSet resultSet = mySqlUtil.select("select x_coordinate,y_coordinate,color, email from canvas_info");
        ArrayList<ColorInfo> colorInfos=new ArrayList<ColorInfo>();
        while (resultSet.next()){
            ColorInfo colorInfo = new ColorInfo();
            colorInfo.setEmail(String.valueOf(resultSet.getInt("email")));
            colorInfo.setX_coordinate(resultSet.getString("x_coordinate"));
            colorInfo.setY_coordinate(resultSet.getString("y_coordinate"));
            colorInfo.setColor(resultSet.getString("color"));
            colorInfo.setEmail(resultSet.getString("email"));
            colorInfos.add(colorInfo);
        }
        return colorInfos;
    }
}
