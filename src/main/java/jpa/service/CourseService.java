package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements CourseDAO {

    EntityManagerFactory factory;// = Persistence.createEntityManagerFactory("SMS");
    EntityManager manager;// = factory.createEntityManager();

    //getAllCourses –This method takes no parameter and returns every Course in the table.
    @Override
    public List<Course> getAllCourses(){

        factory = Persistence.createEntityManagerFactory("SMS");
        manager = factory.createEntityManager();

       //List<Course> allCourses = new ArrayList<>();
       try {
           Query query = manager.createQuery("SELECT c FROM Course c", Course.class);
         //  TypedQuery<Course> query = manager.createQuery("select c from course c", Course.class);
           List<Course> allCourses = query.getResultList();

           disconnect();
           return allCourses;

       } catch (Exception e){
           System.out.println(e.getStackTrace());
           disconnect();
           return null;
       }

       //return allCourses;

    }

    public Course getCourseById(int cId) {
       connect();

        Course course = new Course();

        try {
            course = manager.find(Course.class, cId);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return null;
       } finally {
           disconnect();
        }
        return course;
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
