package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by disinuo on 17/2/25.
 */
@Entity
@Table(name = "teacher", schema = "myDB", catalog = "")
public class Teacher implements Serializable {
    private int id;
    private String chineseName;
    private String sex;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "chineseName")
    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }


    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
