package io.quarkus.it.spring.data.jpa;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PhoneCallResourceTest {

    @Test
    public void testFindById() {
        when().get("/phonecall/1234/56789").then()
                .statusCode(200)
                .body(containsString("25"));
    }

    @Test
    public void testFindByAreaCode() {
        when().get("/phonecall/1010").then()
                .statusCode(200)
                .body(containsString("11111"))
                .body(containsString("13"));
    }

    @Test
    public void testFindAllIds() {
        when().get("/phonecall/ids").then()
                .statusCode(200)
                .body(containsString("11111"))
                .body(containsString("56789"));
    }
}
