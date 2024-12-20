package testcase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.LoginPojo;

public class LoginTest {
    public String setLogin(String uname, String pwd) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LoginPojo loginPojo = new LoginPojo();
        loginPojo.setUsername(uname);
        loginPojo.setPassword(pwd);
        String request = mapper.writeValueAsString(loginPojo);
        return request;


    }
}
