package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest( classes = SchoolApplication.class)
public class StudentServiceImplTest
{
    @Autowired
    StudentService studentService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll()
    {
        assertEquals(13, studentService.findAll().size());
    }

    @Test
    public void findStudentsById()
    {

    }
}
