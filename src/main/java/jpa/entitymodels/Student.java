package jpa.entitymodels;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table( name="student")
public class Student implements Serializable {

    @Id
    @Column(name="email")
    private String sEmail;

    @Basic
    @Column(name="name")
    private String sName;

    @Basic
    @Column(name="password")
    private String sPass;

    @ManyToMany
    @JoinTable(name = "student_course",
               joinColumns = @JoinColumn(name = "student_email", referencedColumnName = "email"),
               inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
              // uniqueConstraints = {@UniqueConstraint(columnNames = {"student_email", "course_id"})}) // Add this constraint so that a student can only sign up for a class once.
    private List<Course> sCourses = new ArrayList<Course>();

    public Student() {
        sEmail="";
        sName="";
        sPass="";
        List<Course> sCourse = null;
    }

    public Student(String sEmail, String sName, String sPass, List<Course> sCourses) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.sCourses = sCourses;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPass() {
        return sPass;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }

    public List<Course> getsCourses() {
        return sCourses;
    }

    public void setsCourses(List<Course> sCourses) {
        this.sCourses = sCourses;
    }

//        @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result ; //+ courseID;
//        result = prime * result + ((sEmail == null) ? 0 : sEmail.hashCode());
//        return result;
//    }




    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student) {
            Student other = (Student) obj;
            boolean sameEmail = this.sEmail.equals(other.getsEmail());
            boolean sameName = this.sName.equals(other.getsName());
            boolean samePass = this.sPass.equals(other.getsPass());
            if (sameEmail && sameEmail && samePass) return true;
            else return false;
        }else
            return false;

        }

    @Override
    public String toString() {
        return "Student{" +
                "sEmail='" + sEmail + '\'' +
                ", sName='" + sName + '\'' +
                ", sPass='" + sPass + '\'' +
               // ", sCourses=" + sCourses +
                '}';
    }
}
