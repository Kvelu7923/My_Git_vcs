package restfullbooker;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseClass {


    @BeforeMethod
    public void createIncident() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";

     //   RestAssured.authentication = RestAssured.basic("", "")

    }


}
