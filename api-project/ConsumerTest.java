package liveProject;


import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.sun.tracing.ProviderName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    //Headers
    Map<String, String> reqHeader= new HashMap<>();

    //Set resource Path
    String resourcePath= "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){

        //Set Header
        reqHeader.put("Content-Type", "application/json");

        //Create a body of both request and response
        DslPart reqResBody= new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName","Tripti")
                .stringType("lastName","Pandey")
                .stringType("email","tripti@test.com");

        //Create the interaction
        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                .method("POST")
                .path(resourcePath)
                .headers(reqHeader)
                .body(reqResBody)
                .willRespondWith()
                .status(201)
                .body(reqResBody)
                .toPact();
    }

    //Pact doesn't support the testNG hence using the @Test JUnit
    @Test
    @PactTestFor(providerName ="UserProvider",port= "8282")
    public void consumerTest(){
        //Set baseURI
        String baseURI ="http://localhost:8282";

        //Create request body
        Map<String, Object> reqBody= new HashMap<>();
        reqBody.put("id",123);
        reqBody.put("firstName", "Tripti");
        reqBody.put("lastName","Pandey");
        reqBody.put("email","tripti@test.com");

        //Generate response
        given().headers(reqHeader).body(reqBody).
                when().post(baseURI + resourcePath).
                then().statusCode(201).log().all();
    }


}
