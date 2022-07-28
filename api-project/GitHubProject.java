package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import javafx.scene.layout.Priority;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProject {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCTWfRjaqKYv6pMOR1E/FyyhSEJQI3lz6Q5GFzxb+EFJkknDI4webVEOBXX9znjmg82DYrPoz3vKmBc2rMgYfV/EFN6d571vdTmzLYM2HURvrYgKICZFWzRdMINT/RjZixCXWn+zHAxnb12GUfc7O1IC/o0Pxj9Opc3sZ2/+Mzp2fgOOIntiDK9R7zYyz5dPIcCxgivZ0Eocjq2v0K68bH9Qa0iZCBJRLGR16Rp6rUYqEirsj4fR7kXBSni7nFiZYmEW1Sariet50/iNchIvTLLpJGYqs9VWnEMqccgag4IIH4E8VxYH4nW+nj79AgKTH5vycWL6zOd2XOnFPp8JGzJ\n";
    int id;
    String GitId="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";

    @BeforeClass
    public void setUp(){

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(sshKey))
                .build();
    }

    @Test(priority = 1)
    public void addKey() {

        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("Key", "sshKey");
        Response response = null;
        id = response.then().extract().path("id");
        response = given().spec(requestSpec).body(reqBody).when().post();

    }

    @Test(priority = 2)
    public void getKey(){

        //Generate Response
        Response response=given().spec(requestSpec).pathParams("keyId", GitId)
                .when().get("/user/keys/{keyId}");
    }

    @Test(priority = 3)
    public void remove(){

        //Generate response
        given().spec(requestSpec).pathParam("keyId", GitId).
                when().delete("/user/keys/{keyId}").
                then().statusCode(204);

    }



}
