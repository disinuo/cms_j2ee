package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by disinuo on 17/2/25.
 */

@Entity
@Table(name = "selectC")
public class SelectC implements Serializable {
    private Student student;
    private Course course;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="sid")  //外键
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student=student;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="cid")  //外键
    public Course getCourse() { return course;}

    public void setCourse(Course course) {
        this.course=course;
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
