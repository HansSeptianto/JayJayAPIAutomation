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

    @Test
    public void testPutUser(){
        String newName = "updatedUser";

        String email = RestAssured.given().when().get("https://reqres.in/api/users/2").getBody().jsonPath().get("data.email");
        String firstName = RestAssured.given().when().get("https://reqres.in/api/users/2").getBody().jsonPath().get("data.first_name");
        String lastName = RestAssured.given().when().get("https://reqres.in/api/users/2").getBody().jsonPath().get("data.last_name");
        String avatar = RestAssured.given().when().get("https://reqres.in/api/users/2").getBody().jsonPath().get("data.avatar");

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("id", 2);
        bodyObj.put("email", email);
        bodyObj.put("first_name", newName);
        bodyObj.put("last_name", lastName);
        bodyObj.put("avatar", avatar);

        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(bodyObj.toString())
                .when()
                .put("https://reqres.in/api/users/2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void testPatchUser(){
        String newName = "updatedUser";

        String firstName = RestAssured.given().when().get("https://reqres.in/api/users/1").getBody().jsonPath().get("data.first_name");

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("first_name", newName);

        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(bodyObj.toString())
                .when()
                .patch("https://reqres.in/api/users/1")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void testDeleteUser(){
        RestAssured
                .given()
                .when()
                .delete("https://reqres.in/api/users/3")
                .then().log().all()
                .assertThat().statusCode(204)
                .assertThat().body(Matchers.equalTo(""));
    }
}
