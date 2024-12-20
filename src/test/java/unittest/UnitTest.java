package unittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pojo.LoginResponse;
import testcase.LoginTest;

public class UnitTest {


    @Test
    public void getID() throws JsonProcessingException {
        LoginTest loginTest = new LoginTest();
        String request=loginTest.setLogin("FebApiuser","Eagle@123"     );
        ObjectMapper objectMapper = new ObjectMapper();
        RestAssured.baseURI = "https://uibank-api.azurewebsites.net/api/users/login";
        Response response = RestAssured.given().header("Content-Type", "application/json")
                .body(request).log().all().when().post();
        response.prettyPrint();
        LoginResponse loginResponse = objectMapper.readValue(response.asString(), LoginResponse.class);
        String id = loginResponse.getId();
            System.out.print(id);

    }


}