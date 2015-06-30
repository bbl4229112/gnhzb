package edu.zju.cims201.GOF.hibernate.pojoA;



import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class ClassificationTree  {
	private Long id;
	private String text;
	//大类编码
	private String classCode;
	//模块编码
	private String code;
	private String lastChildCode;
	private Integer leaf;
	private String imgUrl;
	private String classDes;
	private String modelUrl;
	private CodeClass codeClass;
	private ClassificationTree parent;
	private List<ClassificationTree> children;
	private String uuid;
	//0:true,1:false
	private int lockTree;
	//0:不需要同步，1：需要同步
	private int imgFlag;
	private int modelFlag;
	private int selfFlag;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLastChildCode() {
		return lastChildCode;
	}
	public void setLastChildCode(String lastChildCode) {
		this.lastChildCode = lastChildCode;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getClassDes() {
		return classDes;
	}
	public void setClassDes(String classDes) {
		this.classDes = classDes;
	}
	public String getModelUrl() {
		return modelUrl;
	}
	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	public ClassificationTree getParent() {
		return parent;
	}
	public void setParent(ClassificationTree parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy="parent")
	public List<ClassificationTree> getChildren() {
		return children;
	}
	public void setChildren(List<ClassificationTree> children) {
		this.children = children;
	}
	@ManyToOne
	@JoinColumn(name="codeclass_id")
	public CodeClass getCodeClass() {
		return codeClass;
	}
	public void setCodeClass(CodeClass codeClass) {
		this.codeClass = codeClass;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getLockTree() {
		return lockTree;
	}
	public void setLockTree(int lockTree) {
		this.lockTree = lockTree;
	}
	public int getImgFlag() {
		return imgFlag;
	}
	public void setImgFlag(int imgFlag) {
		this.imgFlag = imgFlag;
	}
	public int getModelFlag() {
		return modelFlag;
	}
	public void setModelFlag(int modelFlag) {
		this.modelFlag = modelFlag;
	}
	public int getSelfFlag() {
		return selfFlag;
	}
	public void setSelfFlag(int selfFlag) {
		this.selfFlag = selfFlag;
	}
	
	
	
	

}