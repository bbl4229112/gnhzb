package edu.zju.cims201.GOF.hibernate.pojoA;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class PlatStructTree {
	private long id;
	private PlatformManage plat;
	private ClassificationTree moduleClass;
	
	private String classCode;
	private String classText;

	private int leaf;
	private int sequence;
	private int onlyone;
	private int ismust;
	
	private PlatStructTree parent;
	private List<PlatStructTree> children;
	
	private Set<ModuleConfigStatus> moduleConfigStatusList;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	@ManyToOne
	@JoinColumn(name="plat_id")
	public PlatformManage getPlat() {
		return plat;
	}
	
	@OneToOne
	@JoinColumn(name="module_class_id")
	public ClassificationTree getModuleClass() {
		return moduleClass;
	}
	public String getClassCode() {
		return classCode;
	}
	public String getClassText() {
		return classText;
	}
	public int getLeaf() {
		return leaf;
	}
	public int getSequence() {
		return sequence;
	}
	public int getOnlyone() {
		return onlyone;
	}
	public int getIsmust() {
		return ismust;
	}
	@ManyToOne
	@JoinColumn(name="parent_id")
	public PlatStructTree getParent() {
		return parent;
	}
	
	@OneToMany(mappedBy="parent")
	public List<PlatStructTree> getChildren() {
		return children;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPlat(PlatformManage plat) {
		this.plat = plat;
	}
	public void setModuleClass(ClassificationTree moduleClass) {
		this.moduleClass = moduleClass;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public void setClassText(String classText) {
		this.classText = classText;
	}
	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public void setOnlyone(int onlyone) {
		this.onlyone = onlyone;
	}
	public void setIsmust(int ismust) {
		this.ismust = ismust;
	}
	public void setParent(PlatStructTree parent) {
		this.parent = parent;
	}
	public void setChildren(List<PlatStructTree> children) {
		this.children = children;
	}
	
	@OneToMany(mappedBy="platStructTree",cascade=CascadeType.ALL)
	public Set<ModuleConfigStatus> getModuleConfigStatusList() {
		return moduleConfigStatusList;
	}

	public void setModuleConfigStatusList(
			Set<ModuleConfigStatus> moduleConfigStatusList) {
		this.moduleConfigStatusList = moduleConfigStatusList;
	}
	
	
}
