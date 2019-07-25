package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.repository.InstructorRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseContollerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private InstructorRepository instructrepo;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }
//---------------------^^^^^ BoilerPlate before actual testing ^^^^^-----------------------
    // GET /courses/courses
    @Test
    public void whenMeasuredResponseTime() {
        given().when().get("/courses/courses").then().time(lessThan(5000L));
    }

    // POST /courses/course/add
    @Test
    public void givenPostACourse() throws Exception {

        String courseName = "C++";
        Course course = new Course(courseName, null);
        course.setCourseid(1_080);

        ObjectMapper mapper = new ObjectMapper();
        String stringR3 = mapper.writeValueAsString(course);

        given().contentType("application/json").body(stringR3).when().post("/courses/CourseAdd").then().statusCode(201);
    }
}
