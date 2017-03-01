package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by disinuo on 17/2/25.
 */
@Entity
@Table(name = "exam", schema = "myDB", catalog = "")
public class Exam implements Serializable {
    private int id;
    private Course course;
    private String name;
    private String date;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="cid")  //外键
    public Course getCourse() {
        return course;
    }

    public void setCid(Course course) {
        this.course = course;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
