package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class InterfaceImage {
	private long id;
	private InterfaceModule interfaceModule;
	private String imageUrl;
	private long width;
	private long height;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	@ManyToOne
	@JoinColumn(name="interface_module_id")
	public InterfaceModule getInterfaceModule() {
		return interfaceModule;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public long getWidth() {
		return width;
	}
	public long getHeight() {
		return height;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInterfaceModule(InterfaceModule interfaceModule) {
		this.interfaceModule = interfaceModule;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setWidth(long width) {
		this.width = width;
	}
	public void setHeight(long height) {
		this.height = height;
	}
	
	
}
