package examples;

import io.restassured.response.Response;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class FirstTest {

   @Test
    public void getMethodTestWithQueryParam(){
        //Set the base URI
        String baseURI= "https://petstore.swagger.io/v2/pet";

//        //if multiple headers are there then use Map
//        Map<String, Object> header= new HashMap<>();
//        header.put("Content-Type", "application/json");
//        header.put("Authorization","token ")

        //Generate response
        Response response=
                given()
                        .headers("content-Type","application/json")
                        .queryParam("status", "sold")
                        .when()
                        .get(baseURI + "/findByStatus");

        //Print the response
        System.out.println(response.getBody().asString());
        System.out.println(response.getBody().asPrettyString());
        //System.out.println(response.getHeaders().asList());
        //System.out.println(response.getHeader("Server"));


        //Extract Value
       String petStatus= response.then().extract().path("[0].status");
       int petId= response.then().extract().path("[0].id");
       System.out.println(petId);

       //Assertion
       //response.then().statusCode(200);
      // response.then().body("[0].Status",equalTo("sold"));
        response.then().time(lessThan(3000L));

    }

}
