package SMSDOA;

import jpa.entitymodels.Student;
import jpa.service.StudentService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.List;

public class StudentDOATest {

    private static StudentService studentService;

    @BeforeClass
    public static void beforeAll(){
        // create a new instance of student service class to be tested
        studentService = new StudentService();

    }

    @Test
    public void getStudentByEmailTest(){
        Student student = studentService.getStudentByEmail("hluckham0@google.ru");
        Assert.assertEquals(student.getsName(),"Hazel Luckham");

    }

    @Test
    public void getStudentByEmailTest2(){
        Student student = studentService.getStudentByEmail("qllorens2@howstuffworks.com");
        Assert.assertEquals("Checks if can get a student using his/her email: ",student.getsName(),"Quillan Llorens");

    }

    @Test
    public void getAllStudentsTest(){
       List<Student> allStudents = studentService.getAllStudents();
       Assert.assertEquals(allStudents.size(), 10);
    }

    @Test
    public void validateStudentTest(){
        Boolean student = studentService.validateStudent("aiannitti7@is.gd","TWP4hf5j");
        Assert.assertTrue(student);

    }

    //    @Test
//    public void registerStudentToCourseTest(){
//         studentService.registerStudentToCourse("cjaulme9@bing.com", 1);
//
//
//        Assert.assertEquals("Checks if can get a student using his/her email: ", allStudents.size(), 10);
//    }

}
