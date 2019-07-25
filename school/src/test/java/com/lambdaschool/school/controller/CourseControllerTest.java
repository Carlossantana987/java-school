package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//SpringRunner because IntelliJ doesnt know that were using spring
@RunWith(SpringRunner.class)
//used for controller testing. Value is set to the controller to test. Secure Falses turns off Authentication.
@WebMvcTest(value = SchoolApplication.class , secure = false)
public class CourseControllerTest
{
    //bringing in class
    @Autowired
    private MockMvc mockMvc;

    //used to stub CourseService. Not calling the real CourseService
    @MockBean
    private CourseService courseService;

    private List<Course> courseList;

    @Before
    public void setUp() throws Exception
    {
       ArrayList<Instructor> instructorsList = new ArrayList<>();

        Instructor i1 = new Instructor("Sally");
        Instructor i2 = new Instructor("Lucy");
        Instructor i3 = new Instructor("Charlie");

        instructorsList.add(i1);
        instructorsList.add(i2);
        instructorsList.add(i3);

        ArrayList<Course> courseList = new ArrayList<>();

        Course c1 = new Course("Data Science", i1);
        Course c2 = new Course("JavaScript", i1);
        Course c3 = new Course("Node.js", i1);
        Course c4 = new Course("Java Back End", i2);
        Course c5 = new Course("Mobile IOS", i2);
        Course c6 = new Course("Mobile Android", i3);

        courseList.add(c1);
        courseList.add(c2);
        courseList.add(c3);
        courseList.add(c4);
        courseList.add(c5);
        courseList.add(c6);

        ArrayList<Student> studentList = new ArrayList<>();

        Student s1 = new Student("John");
        Student s2 = new Student("Julian");
        Student s3 = new Student("Mary");

        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);





    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/listCourses";

        //used to block the actual courseService method within the actual controller
        Mockito.when(courseService.findAll(Pageable.unpaged())).thenReturn((ArrayList<Course>)= courseList);

        //used to call the request body
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        //used to return request
        MvcResult r = mockMvc.perform(rb).andReturn();

        //used to change request response to string
        String tr = r.getResponse().getContentAsString();

        //used to manually converts Object into Json
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        //used to compare er-ExpectedResults to == tr- TestedResults
        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void addNewCourse() throws Exception
    {
        String apiUrl = "/courses/CourseAdd";
        Instructor i1 = new Instructor("Dan");
        Course c1 = new Course("TESTING", i1);
        c1.setCourseid(101);

        //converts Object into Json
        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(c1);

        Mockito.when(courseService.save(any(Course.class))).thenReturn(c1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(courseString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }


}