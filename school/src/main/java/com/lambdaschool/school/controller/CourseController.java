package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetails;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;



    //localhost:2019/courses/courses
    @ApiOperation(value = "returns all Courses with paging Ability", responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> AllCourses( @PageableDefault(page = 0, size = 3) Pageable pageable )
    {
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @GetMapping(value = "/listCourses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses()
    {
        ArrayList<Course> myCourses = courseService.findAll(Pageable.unpaged());
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }







    //localhost:2019/courses/studcount
    @ApiOperation(value = " Retrieves Count of students in Courses", response = Course.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Course Count Found", response = Course.class),
                            @ApiResponse(code = 404, message = "Course Count NOT Found", response = ErrorDetails.class)})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }






    @GetMapping(value = "/Course/{courseId}", produces = {"application/json"})
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId, HttpServletRequest request)
    {
        logger.trace(request.getMethod() + request.getRequestURI() + " accessed");

        Course c = courseService.findCourseById(courseId);
        return new ResponseEntity<>(c,HttpStatus.OK);
    }







    //localhost:2019/courses/CourseAdd
    @PostMapping(value = "/CourseAdd",consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewCourse(@Valid @RequestBody Course newCourse,HttpServletRequest request) throws URISyntaxException
    {
        newCourse = courseService.save(newCourse);

        HttpHeaders responseHeaders = new HttpHeaders();

        URI newCourseURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{courseId}").buildAndExpand(newCourse.getCourseid()).toUri();
        responseHeaders.setLocation(newCourseURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }








    //localhost:2019/courses/courses/{courseid}
    @ApiOperation(value = "Deletes a Course associated with the CourseId", response = Course.class)
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseId)
    {
        courseService.delete(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
