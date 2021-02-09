package ru.netology.web;

import com.github.javafaker.Faker;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import lombok.Data;
import java.util.Locale;

@Data
public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static UserRegistration generateValidActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new UserRegistration(login,password,"active"));

        return new UserRegistration(login,password,"blocked");
    }

    public static UserRegistration generateValidBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new UserRegistration(login,password,"blocked"));

        return new UserRegistration(login,password,"blocked");
    }

    public static UserRegistration generateUserInvalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new UserRegistration("timofey",password,status));
        return new UserRegistration("grisha",password,status);
    }

    public static UserRegistration generateUserInvalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        makeRegistration(new UserRegistration(login,"password",status));
        return new UserRegistration(login,"pass",status);
    }

    static void makeRegistration(UserRegistration UserRegistration) {
        given()
                .spec(requestSpec)
                .body(UserRegistration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}
