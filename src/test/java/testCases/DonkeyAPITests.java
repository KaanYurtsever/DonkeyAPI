package testCases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DonkeyAPITests {

    @Test(priority = 1)
    public void donkeyGetReq(){

        //This Get request brings us to all values
        given()
                .when()
                    .get("http://localhost:54092/api/values")
                .then()
                    .statusCode(200);
    }

    @Test(priority = 2)
    public void donkeyPostReqDeals(){

        //This Post request add values which are in the Deals Table
        HashMap data = new HashMap();
        data.put("Id", "8");
        data.put("Name", "Edi");
        data.put("Amount", "1234");
        data.put("MaturityDate", "2022-09-09");

        Response res=
            given()
                    .contentType("application/json")
                    .body(data)
                .when()
                    .post("http://localhost:54092/api/values/deals")
                .then()
                    .statusCode(200)
                    .log().body()
                    .extract().response();

        String jsonString = res.asString();
        Assert.assertTrue(jsonString.contains("Process Done"));
    }


    @Test(priority = 3)
    public void donkeyPostReqClient(){

        //This Post request add values which are in the Client Table
        HashMap data = new HashMap();
        data.put("Id", "7");
        data.put("FirstName", "Carol");
        data.put("LastName", "Mac");
        data.put("Age", "27");

        Response res=
                given()
                        .contentType("application/json")
                        .body(data)
                        .when()
                        .post("http://localhost:54092/api/values/client")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().response();

        String jsonString = res.asString();
        Assert.assertTrue(jsonString.contains("Process Done"));
    }

    @Test(priority = 4)
    public void donkeyGetReqDealsId(){

        //This Get request brings us the Deals Id which we want
        given()
                .when()
                .get("http://localhost:54092/api/values/deals/4")
                .then()
                .statusCode(200)
                .log().body()
                .body("deals.Id", equalTo("4"));
    }

    @Test(priority = 5)
    public void donkeyPutReqClient(){

        //This PUT request updates the client
        HashMap data = new HashMap();
        data.put("Id", "7");
        data.put("FirstName", "Marine");
        data.put("LastName", "Aral");
        data.put("Age", "34");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("http://localhost:54092/api/values/client/7")
                .then()
                .statusCode(200)
                .log().body()
                .body("client.Id", equalTo("7"))
                .body("client.FirstName", equalTo("Marine"));
    }

    @Test(priority = 6)
    public void donkeyDeleteReqClient(){

        //This DELETE request deletes the client by id
        Response res=
            given()
                    .when()
                    .delete("http://localhost:54092/api/values/client/7")
                    .then()
                    .statusCode(200)
                    .log().body()
                    .extract().response();
        String jsonString = res.asString();
        Assert.assertTrue(jsonString.contains("Client Deleted"));
    }
}
