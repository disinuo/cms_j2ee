package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by disinuo on 17/2/25.
 */
@Entity
@Table(name = "score")
public class Score implements Serializable {
    private int sid;
    private Exam exam;
    private int score;

//    @ManyToOne
    @Column(name="sid")  
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid=sid;
    }

    @ManyToOne
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
    @Transient
    public String getExamName(){
    	return exam.getName(); 
    }
    @Transient
    public String getExamDate(){
    	return exam.getDate();
    }
    @Transient
    public String getCourseName(){
    	return exam.getCourse().getName(); 
    }
}
