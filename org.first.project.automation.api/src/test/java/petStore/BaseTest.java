package petStore;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static globalConstants.Constants.CODE_OK;

public class BaseTest {
    protected RequestSpecification specForRequest = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    protected ResponseSpecification responseSpecOK = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(CODE_OK)
            .expectContentType(ContentType.JSON)
            .build();
}
