package jpa.mainrunne;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;
import org.hibernate.HibernateException;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;


public class SMSRunner {
    private Scanner sin;
    private StringBuilder sb;

    private CourseService courseService;
    private StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();
        courseService = new CourseService();
        studentService = new StudentService();
    }


    public static void main(String[] args) {

//        SMSRunner sms = new SMSRunner();
//        sms.run();

    }

    private void run() {
        // Login or quit
        switch (menu1()) {
            case 1:
                if (studentLogin()) {
                    registerMenu();

                }else out.println("User Validation failed. GoodBye!");
                break;
            case 2:
                out.println("Thank you. Goodbye!");
                break;

            default:
                out.println("Invalid input");
                break;

        }
    }

    private int menu1() {
        sb.append("\nAre you a \n1.Student \n2. Quit\nPlease Enter Selection: 1 or 2");
        out.print(sb.toString());
        sb.delete(0, sb.length());

        return sin.nextInt();
    }

    private boolean studentLogin() {
        try {
            boolean retValue = false;
            out.print("Enter your email address: ");
            String email = sin.next();
            out.print("Enter your password: ");
            String password = sin.next();
            retValue = studentService.validateStudent(email, password);

               Student student = studentService.getStudentByEmail(email);
            if (student != null) {
            currentStudent = student;
            }
//
//        if (currentStudent != null & currentStudent.getsPass().equals(password)) {
            if (retValue) {
                List<Course> courses = studentService.getStudentCourses(email);
                out.println("My Classes:");
                out.printf("%-5s%-35s%-25s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
                for (Course course : courses) {
                    out.printf("%-5s%-35s%-25s\n", course.getCid(), course.getcName(), course.getcInstructorName());
                }
            }
            return retValue;
//        } else {
//            out.println("User Validation failed. GoodBye!");
       // }
            // return retValue;
        } catch (Exception e) {
            out.println(e.getStackTrace());

             return false;}
        }

    private void registerMenu() {
        sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
        out.print(sb.toString());
        sb.delete(0, sb.length());
        int i = sin.nextInt();
        switch (i) {
            case 1:
                List<Course> allCourses = courseService.getAllCourses();
                out.printf("%-5s%-35s%-25s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
                for (Course course : allCourses) {
                    out.printf("%-5s%-35s%-25s\n", course.getCid(), course.getcName(), course.getcInstructorName());
                }

                out.println();
                out.print("Enter Course Number: ");
                int number = sin.nextInt();
                Course newCourse = courseService.getCourseById(number);

                if (newCourse != null) {
                    studentService.registerStudentToCourse(currentStudent.getsEmail(), newCourse.getCid());
                    List<Course> newCourses = studentService.getStudentCourses(currentStudent.getsEmail());
                    out.println("My Classes");
                    out.printf("%-5S%-35S%-25S\n", "ID", "Course", "Instructor");
                    for (Course course : newCourses) {
                        out.printf("%-5s%-35s%-25s\n", course.getCid(), course.getcName(), course.getcInstructorName());
                    }
                    out.println("You have been signed out.");
                }
                break;
            case 2:
                out.println("Thank you. Goodbye!");
                break;
            default:
                out.println("Invalid input. Goodbye!");
        }
    }
}
