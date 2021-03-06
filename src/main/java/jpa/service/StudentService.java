package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.HibernateException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {

   private EntityManagerFactory factory;
   private EntityManager manager;

    public StudentService() {

        factory = Persistence.createEntityManagerFactory("SMS");
        manager = factory.createEntityManager();
    }
    //getAllStudents- This  method  reads  the student table in your  database and returns the data as a List<Student>
    @Override
    public List<Student> getAllStudents() {

        connect(); // entitymanager
        List<Student> students = new ArrayList<>();

         try{
            Query query = manager.createQuery("SELECT s FROM Student s ", Student.class);
           //TypedQuery<Student> query = manager.createQuery("select s from Student s", Student.class);
            students = query.getResultList();


            disconnect();
            return students;
        } catch (Exception e){
             System.out.println("getAllStudents "+e.getMessage());
             return null;
         } finally {
             disconnect();
             return students;
         }

    }

    // getStudentByEmail –This  method  takes  a Student’s  email  as  a
    //String  and  parses  the student list for a Student with that email and  returns  a  Student Object.
    @Override
    public Student getStudentByEmail(String sEmail) {
        connect(); // entitymanager

        Student student = new Student();
        try {
            student = manager.find(Student.class, sEmail);


        } catch (Exception e) {
            System.out.println("getStudentByEmail" + e.getMessage());
            return null;
        } finally {
            disconnect();

        }
        return student;
    }

    //validateStudent –This  method  takes  two parameters:  the  first one  is  the  user  email
    //and  the  second  one  is the  password  from  the user input. Return whether or not  student was found.
    @Override
    public boolean validateStudent(String sEmail, String sPassword) {

        Student student = getStudentByEmail(sEmail);
        if (student != null) {
            return student.getsPass().equals(sPassword);
        }
        return false;

    }

    //registerStudentToCourse –After a successful student validation, this method takes a Student’s  email  and  a
    //Course ID. It checks in the  join  table  (i.e.Student_Course) generated by JPA to find if a Student with that
    //Email is currently attending a Course with that ID.If  the  Student  is  not attending  that  Course,
    //register the student to that  course;  otherwise not.
    @Override
    public void registerStudentToCourse(String sEmail, int cId) {
        // connect to the Database
      //  connect();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            Student student = entityManager.find(Student.class, sEmail);
            Course course = entityManager.find(Course.class, cId);
            Query query = entityManager.createNativeQuery("select *from student_course sc where sc.student_email=:sEmail and sc.course_id=:id")
                    .setParameter("sEmail", sEmail).setParameter("id", cId);
            Object rows = query.getSingleResult();
           // String rowsSt = String.valueOf(rows);
          //  System.out.println("Quantity of rows in student-course table: "+ rowsSt);
            if (String.valueOf(rows).equals("0")) {
                entityManager.getTransaction().begin();
                student.addCourse(course);
                entityManager.getTransaction().commit();
                System.out.println("You have successfully registered to " + course.getcName() + " course.");
            } else {
                System.out.println(student.getsName() + " is already registered for "+course.getcName()+" course");
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            System.out.println("wrong " + sEmail + " and/or course id");
            e.printStackTrace();
        } catch (Exception e) {
            if (entityManager.getTransaction() !=null) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
//       try {
////               manager.getTransaction().begin();
////               Query query = manager.createNativeQuery("INSERT INTO student_course (student_email, course_id) VALUES (?,?)");
////               query.setParameter(1, sEmail);
////               query.setParameter(2, cId);
////               query.executeUpdate();
////               manager.getTransaction().commit();
//
//           Student student = manager.find(Student.class, sEmail);
//           manager.getTransaction().begin();
//           Course course = manager.find(Course.class, cId);
//           List<Course> courses = student.getsCourses();
//           courses.add(course);
//           student.setsCourses(courses);
//           manager.persist(student);
//           manager.getTransaction().commit();
//
//       }catch(Exception e){
//           manager.getTransaction().rollback();
//       } finally {
//           disconnect();
//       }

//       /Checks if the student is currently enrolled in the course they've selected
//        Query q = em.createNativeQuery("SELECT * FROM Student_Course WHERE sEmail = ? AND id = ?");
//        q.setParameter(1,sEmail);
//        q.setParameter(2,cId);
//        List<Course> courses = (List<Course>) q.getResultList();
//
//        //If the student isn't enrolled in the selected course, add them to the course.
//        if (courses.isEmpty()){
//            Query addCourse = em.createNativeQuery("INSERT INTO Student_Course (sEmail, id) VALUES (?,?)");
//            addCourse.setParameter(1,sEmail);
//            addCourse.setParameter(2,cId);
//            addCourse.executeUpdate();
//            em.getTransaction().commit();
//
//            //If the student is already registered to the selected course, alert them and exit the program.
//        } else System.out.println("You are already registered in that course!");
//
//
//    }

}

    // getStudentCourses–This method takes a Student’s Email as a parameter and would find all the courses a student is registered.
    public List<Course> getStudentCourses(String sEmail) {
      //  connect();

        try {

            Query query = manager.createNativeQuery("Select c.* from Course c JOIN student_course sc on c.id = sc.course_id " +
                                                     "WHERE sc.student_email = :email", Course.class);

            query.setParameter("email",sEmail);
            List<Course> studentCoursesList = query.getResultList();
            return studentCoursesList;

        } catch (Exception e){
            System.out.println(e.getStackTrace());
            return null;
        }
        finally {
            disconnect();

        }

     }

     // Add student is not requirement just to test it for myself
    public void addStudent(Student student) {
        // begin a transaction is required for creating or updating records
       // connect();
        manager.getTransaction().begin();
        // tell the entity manager to save this object
        manager.persist(student);
        // commit the transaction to actually save the data to the database
        manager.getTransaction().commit();


    }

    // Delete student is not requirement just to test it for myself
    public int deleteStudent(String sEmail){
        String sql = "DELETE FROM Student where email = :SEmail";

        // set this to use the correct entity
        Query query = manager.createQuery(sql);
        query.setParameter("SEmail", sEmail);

        manager.getTransaction().begin();
        int recordsUpdated = query.executeUpdate();
        manager.getTransaction().commit();

        return recordsUpdated;

    }


    public void connect() {

        factory = Persistence.createEntityManagerFactory("SMS");
        manager = factory.createEntityManager();
    }

    public void disconnect() {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            manager.close();
        }
    }
}

