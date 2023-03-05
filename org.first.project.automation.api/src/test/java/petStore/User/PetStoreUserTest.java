package petStore.User;

import org.testng.annotations.*;
import petStore.BaseTest;
import static config.enums.PetStoreEndpoint.USER;
import static globalConstants.Constants.URL;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class PetStoreUserTest extends BaseTest {
    static final String USERNAME = "testUser";

    //@Test(description = "Delete user")
    @AfterMethod
    public void DeletePetStoreUser() {
        deleteUser();
    }

    //@Test(description = "Created user object")
    @BeforeMethod
    public void postCreateUserTest() {
        createUser();
    }

    @Test(description = "Get user by user name")
    public void getPetStoreUserByNameTest() {

        given().spec(specForRequest)
                .when().get(format("%s%s%s", URL, USER, USERNAME))
                .then()
                .spec(responseSpecOK)
                .body(matchesJsonSchemaInClasspath("json.schema.PetStore/receiveResponseGetUserByUsername.json"))
                .body("username", equalTo(USERNAME));

    }

    /*@Test(description = "Get user by username")
    public void putPetStoreUser(){

        String bodyForPutRequest = "{\"id\":40," +
                "\"username\":\"testUser2\"," +
                "\"firstName\":\"David\"," +
                "\"lastName\":\"Zoolander\"," +
                "\"email\":\"test@gmail.com\"," +
                "\"password\":\"testUser2\"," +
                "\"phone\":\"+380960913814\"," +
                "\"userStatus\":2}";

        given().spec(specForRequest)
                .body(bodyForPutRequest)
                .when().put(format("%s%s%s",URL,USER,USERNAME))
                .then()
                .spec(responseSpecOK)
                .body(matchesJsonSchemaInClasspath("json.schema.PetStore/receiveResponsePostUser.json"));
    }*/

    @Test(description = "Log in user session")
    public void getPetStoreUserLogin() {
        given().spec(specForRequest)
                .when().get(format("%s%s%s%s%s%s%s", URL, USER, "login?", "username=", USERNAME, "&password=", USERNAME))
                .then().log().all()
                .spec(responseSpecOK)
                .body(containsStringIgnoringCase("logged in user session"));
    }

    @Test(description = "Logs out current logged in user session")
    public void getPetStoreUserLogout() {
        given().spec(specForRequest)
                .when().get(format("%s%s%s", URL, USER, "logout"))
                .then().log().all()
                .spec(responseSpecOK)
                .body(containsStringIgnoringCase("ok"));
    }

    private void createUser() {

        String bodyForPostRequest = "{\"id\":40," +
                "\"username\":\"testUser\"," +
                "\"firstName\":\"Ruslan\"," +
                "\"lastName\":\"Yurchenko\"," +
                "\"email\":\"test@gmail.com\"," +
                "\"password\":\"testUser\"," +
                "\"phone\":\"22-00-23\"," +
                "\"userStatus\":0}";

        given().spec(specForRequest)
                .body(bodyForPostRequest)
                .when().post(format("%s%s", URL, USER))
                .then()
                .spec(responseSpecOK)
                .body(matchesJsonSchemaInClasspath("json.schema.PetStore/receiveResponsePostUser.json"));

    }

    private void deleteUser() {
        given().spec(specForRequest)
                .when().delete(format("%s%s%s", URL, USER, USERNAME))
                .then().log().all()
                .spec(responseSpecOK)
                .body("message", equalTo(USERNAME));
    }
}
