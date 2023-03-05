package petStore.Store;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import petStore.BaseTest;

import static config.enums.PetStoreEndpoint.STORE_INVENTORY;
import static config.enums.PetStoreEndpoint.STORE_ORDER;
import static globalConstants.Constants.URL;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreStoreTests extends BaseTest {

    static final Integer ID = 615651;


    @Test(description = "Returns pet inventories by status")
    public void getPetStoreInventoryByStatusTest(){
        given().spec(specForRequest)
                .when().get(format("%s%s",URL,STORE_INVENTORY))
                    .then().spec(responseSpecOK);

    }
    @Test(description = "Place an order for a pet")
    public void postPetStoreOrderCreateTest(){

        String bodyForRequest = "{\"id\":615651,"+
                "\"petId\":56516,"+
                "\"quantity\":5,"+
                "\"shipDate\":\"2023-01-09T10:01:22.688Z\","+
                "\"status\":\"placed\","+
                "\"complete\":true}";

        given().spec(specForRequest)
                .body(bodyForRequest)
                .when().post(format("%s%s",URL,STORE_ORDER))
                .then()
                .spec(responseSpecOK)
                .body("id", equalTo(ID))
                .body("status", equalTo("placed"));
    }
    @Test(dependsOnMethods = "postPetStoreOrderCreateTest", description = "Find purchase order by ID")
    public void getPetStoreOrderByIdTest(){
        given().spec(specForRequest)
                .when().get(format("%s%s%d",URL,STORE_ORDER,ID))
                .then()
                .spec(responseSpecOK);

    }
    @Test(description = "Find purchase order by incorrect ID (Negative case)")
    public void getPetStoreOrderByIncorrectIdNegativeTest(){

        given().spec(specForRequest)
                .when().get(format("%s%s%s",URL,STORE_ORDER,"000001"))
                .then()
                .log().all()
                .statusCode(404).contentType(ContentType.JSON)
                .body(containsStringIgnoringCase("error"));

    }
    @Test(dependsOnMethods = "getPetStoreOrderByIdTest", description = "Delete purchase order by ID")
    public void deletePetStoreOrderByIdTest(){
        given().spec(specForRequest)
                .when().delete(format("%s%s%d",URL,STORE_ORDER,ID))
                .then()
                .spec(responseSpecOK)
                .body(containsStringIgnoringCase("Unknown"));

    }
}
