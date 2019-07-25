package com.lambdaschool.school.service;


import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceimplTest
{

    @Autowired
    CourseService courseService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findCourseById()
    {
        assertEquals("Data Science", courseService.findCourseById(1).getCoursename());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNotFound()
    {
        courseService.delete(100);
        assertEquals(5,courseService.findAll(Pageable.unpaged()).size());
    }

    @Test
    public void deleteFound()
    {
        courseService.delete(6);
        assertEquals(5,courseService.findAll(Pageable.unpaged()).size());

    }

}
