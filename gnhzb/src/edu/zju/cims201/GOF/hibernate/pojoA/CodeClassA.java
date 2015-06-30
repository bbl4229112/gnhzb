package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CodeClassA {

	 private long id;
     private String classname;
     private String classcode;
     private int flag;
     private String rule;
     private String uuid;

     @Id
     @GeneratedValue
 	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
    public String getClassname() {
        return this.classname;
    }
    
    public void setClassname(String classname) {
        this.classname = classname;
    }
    @Column(nullable = false)
    public String getClasscode() {
        return this.classcode;
    }
    
    public void setClasscode(String classcode) {
        this.classcode = classcode;
    }
    @Column(nullable = false)
    public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    @Column(name="coderule")
    public String getRule() {
        return this.rule;
    }
    
    public void setRule(String rule) {
        this.rule = rule;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}




}