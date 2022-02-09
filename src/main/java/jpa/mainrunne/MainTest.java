package jpa.mainrunne;

import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

import java.util.List;

import static java.lang.System.out;

public class MainTest {
    public static void main(String[] args) {
        CourseService courseService = new CourseService();
        StudentService studentService = new StudentService();
//        Student student = new Student();
//        student.setsName("Ahlam Ebjad");
//        student.setsPass("123");
//        student.setsEmail("aebjad@gmail.com");
        // Add student works
//        studentService.addStudent(student);

//        Student student1 = studentService.getStudentByEmail("aebjad@gmail.com");
//        out.println(student1.getsEmail()+" "+student1.getsName()+ " "+student1.getsPass());
//        // Update student password works
//        student1.setsPass("asd123");
        //out.println(student1.getsEmail()+" "+student1.getsName()+ " "+student1.getsPass());

  /// Delete student works
//         int recordsUpdated = studentService.deleteStudent("aebjad@gmail.com");
//        out.println(recordsUpdated+ " record updated");
//
        List<Student> allStudents = studentService.getAllStudents();
        out.println("All Students:");
        for(Student student : allStudents )
            out.println(student);


    }
}
