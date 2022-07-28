package examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CodeReuseExample {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    int petId;

    @BeforeClass
    public void setUp(){

        //Build requestSpec
        requestSpec =new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType(ContentType.JSON)
                .build();

        //Build responseSpec
        responseSpec= new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(5L), TimeUnit.SECONDS)
                .expectBody("status", equalTo("alive"))
                .build();
    }

    @Test(priority = 1)
    public void addPet() {
        //Request Body
        String reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";

        //Generate response
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        //Print response
        System.out.println(response.getBody().asString());
        petId =response.then().extract().path("id");

        //Assertion
        response.then().spec(responseSpec);
    }
        @Test(priority = 2)
        public void getPet() {

            //Generate response
          given().spec(requestSpec).pathParams("petId", petId).
                 when().get("/{petId}").
                 then().spec(responseSpec).log().body();

        }

        @Test(priority = 3)
        public void remove(){

        //Generate response
       given().spec(requestSpec).pathParam("petId",petId).
                when().delete("/{petId}").
                then().statusCode(200).body("message",equalTo(""+ petId));

        }

    }

