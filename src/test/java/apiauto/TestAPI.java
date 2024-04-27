package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class TestAPI {

    @Test
    public void testGetSingleUser(){
        RestAssured
                .given().when()
                .get("https://reqres.in/api/users/2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("data.id", Matchers.equalTo(2))
                .assertThat().body("data.first_name", Matchers.equalTo("Janet"))
                .assertThat().body("data.last_name", Matchers.equalTo("Weaver"));
    }
    @Test
    public void testGetListUsers(){
        RestAssured
                .given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("page", Matchers.equalTo(2))
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("total", Matchers.equalTo(13))
                .assertThat().body("total_pages", Matchers.equalTo(2));
    }

    @Test
    public void testPostCreateUser(){
        String name = "Hans";
        String job = "Student";

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("name", name);
        bodyObj.put("job", job);

        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(name))
                .assertThat().body("job", Matchers.equalTo(job));
    }
}
