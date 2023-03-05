package petStore.Pet;

import org.testng.Assert;
import org.testng.annotations.Test;
import petStore.BaseTest;

import static config.enums.PetStoreEndpoint.PET;
import static config.enums.PetStoreEndpoint.PET_FIND_BY_STATUS;
import static globalConstants.Constants.*;
import static io.restassured.RestAssured.given;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class PetStorePetTests extends BaseTest {

    @Test(description = "Finds Pets by status with TestNG", enabled = false)
    public void getPetStorePetsByStatusTestNGTest(){
        String resp = given().spec(specForRequest)
                .param(STATUS,"available")
                .when().get(format("%s%s",URL,PET_FIND_BY_STATUS))
                .then()
                .spec(responseSpecOK)
                .extract().response().getBody().asString();

        Assert.assertTrue(resp.contains(STATUS));
        Assert.assertFalse(resp.contains("sold"), "There is excess word in the response");

    }

    @Test(description = "Finds Pets by status")
    public void getPetStorePetsByStatusTest() {
        given().spec(specForRequest)
                .param(STATUS, "for sale")
                .when().get(format("%s%s",URL,PET_FIND_BY_STATUS))
                .then()
                .spec(responseSpecOK)
                .body("category", is(notNullValue()));
    }

    @Test(description = "Update an existing pet")
    public void putUpdateAnExistingPetTest(){

        String bodyForPutRequest = "{" +
                "\"name\":\"doggie\"," +
                "\"photoUrls\":[\"http://py.jpg\"]," +
                "\"id\":998," +
                "\"category\":{" +
                "\"id\":556," +
                "\"name\":\"Spike\"}," +
                "\"tags\":[" +
                "{\"id\":1010,\"name\":\"lovely dog\"}," +
                "{\"id\":1022,\"name\":\"smart dog\"}]," +
                "\"status\":\"for sale\"}";

        given().spec(specForRequest)
                .body(bodyForPutRequest)
                .when().put(format("%s%s",URL,PET))
                .then().spec(responseSpecOK)
                .body(matchesJsonSchemaInClasspath("json.schema.PetStore/receiveResponseUpdateExistingPet.json"));
    }
}
