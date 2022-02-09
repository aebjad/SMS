package jpa.entitymodels;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table( name="course")
public class Course implements Serializable {

    @Id
    @Column(name="id")
    private Integer cid;

    @Basic
    @Column(name="name")
    private String cName;

    @Basic
    @Column(name="instructor")
    private String cInstructorName;

    @ManyToMany(mappedBy = "sCourses")
    private List<Student> students = new ArrayList<Student>();

    public Course() {

    }

    public Course(Integer cid, String cName, String cInstructorName) {
        this.cid = cid;
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        result = prime * result + ((cid == null) ? 0 : cid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course course = (Course) obj;
        return cid.equals(course.cid) && cName.equals(course.cName) && cInstructorName.equals(course.cInstructorName);
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", cName='" + cName + '\'' +
                ", cInstructorName='" + cInstructorName + '\'' +
                '}';
    }
}
