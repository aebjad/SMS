package SMSDOA;

import jpa.entitymodels.Course;
import jpa.service.CourseService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CourseDOATest {

    private static CourseService courseService;

    @BeforeClass
    public static void beforeAll(){
        // create a new instance of course service class to be tested
        courseService = new CourseService();
    }

    @Test
    public void getAllCoursesTest(){
        List<Course> courses = courseService.getAllCourses();
        Assert.assertEquals(courses.size(), 10);
    }




}
