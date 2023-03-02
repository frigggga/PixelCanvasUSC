package com.youzhi.canvas.controller;

import com.youzhi.canvas.bean.LoginResult;
import com.youzhi.canvas.bean.RegisterResult;
import com.youzhi.canvas.util.MySqlUtil;
import com.youzhi.canvas.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("login")//http://127.0.0.1:8080/user/login?email=asd&password=123
    public LoginResult login(String username, String email, String password) throws Exception{
        MySqlUtil mySqlUtil = new MySqlUtil();
        ResultSet resultSet = mySqlUtil.select("select username, email from user_info where username = '"+username+"' and email = '"+email+"' and password = '" +password+"'");
        LoginResult loginResult = new LoginResult();
        while (resultSet.next()){
            loginResult.setUsername(resultSet.getString("username"));
            loginResult.setEmail(resultSet.getString("email"));
        }
        if (loginResult.getUsername() ==null){
            loginResult.setMessage("fail");
            loginResult.setResult("0");
        }else {
            loginResult.setMessage("success");
            loginResult.setResult("1");
            loginResult.setToken(TokenUtil.token(email,password));
        }
        return loginResult;
    }

    @RequestMapping("register")//http://127.0.0.1:8080/user/register?email=asd&password=123
    public RegisterResult register(String username,String email, String password)throws Exception{
        MySqlUtil mySqlUtil = new MySqlUtil();
        int count = mySqlUtil.getRecordCount("select username, email, password from user_info where username= '"+username+"' and email = '"+email+"' and password = '" +password+"'");
        RegisterResult registerResult = new RegisterResult();
        registerResult.setEmail(email);
        if (count > 0){ // repeat register
            registerResult.setMessage("fail(Repeat registration)");
            registerResult.setResult("0");
        }else {
            mySqlUtil.executeupdate("insert into user_info(username,email,password) VALUES('"+username+"','"+email+"','"+password+"')");
            ResultSet resultSet = mySqlUtil.select("select username, email, password from user_info where username= '"+username+"' and email = '"+email+"' and password = '" +password+"'");
            while (resultSet.next()){
                registerResult.setUsername(resultSet.getString("username"));
            }
            if (registerResult.getUsername() ==null){
                registerResult.setResult("0");
                registerResult.setMessage("fail(Insertion table failed)");
            }else {
                registerResult.setResult("1");
                registerResult.setMessage("success");
            }
        }
        return registerResult;
    }

}
