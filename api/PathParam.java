package examples;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.lessThan;

public class PathParam {

    @Test
    public void getMethodTestWithPathParam(){
        //Set the base URI
        String baseURI= "https://petstore.swagger.io/v2/pet";


        //Generate response
        Response response=
                given()
                        .headers("content-Type","application/json")
                        .pathParam("petId", "9")
                        .when()
                        .get(baseURI + "/{petId}");

        //Print the response
        System.out.println(response.getBody().asString());
        System.out.println(response.getBody().asPrettyString());



        //Extract Value
        String petStatus= response.then().extract().path("status");
        int petId= response.then().extract().path("id");
        System.out.println(petId);

        //Assertion

        response.then().time(lessThan(3000L));
        response.then().body(matchesJsonSchema("https://petstore.swagger.io/v2/swagger.json"));

    }
}
