package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by disinuo on 17/2/25.
 */

@Entity
@Table(name = "selectC")
public class SelectC implements Serializable {
    private int sid;
    private int cid;

    @Basic
    @Column(name = "sid")
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid=sid;
    }

    @Basic
    @Column(name="cid")
    public int getCid(){
    	return cid;
    }

    public void setCid(int cid) {
        this.cid=cid;
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
