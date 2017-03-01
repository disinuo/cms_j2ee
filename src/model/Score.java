package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by disinuo on 17/2/25.
 */
@Entity
@Table(name = "score")
public class Score implements Serializable {
    private Student student;
    private Exam exam;
    private int score;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="sid")  //外键
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student=student;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="eid")  //外键
    public Exam getExam() { return exam;}

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
