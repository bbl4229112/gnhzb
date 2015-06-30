package edu.zju.cims201.GOF.service.lca;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.Component;
import edu.zju.cims201.GOF.hibernate.pojo.Modelflow;


public interface ComponentService {
	//获取该level的所有节点
	public List<Component> getNodesByLevel(int level);
	//根据parentid获取所有节点
	public List<Component> getNodesByParent(int parentId);
	//查找零件
	public Component getComponent(int id);
	//增加component
	public Component addComponent(Component c);
	public Modelflow addModelflow(Modelflow m);
	//删除component
	public void deleteComponent(int id);
	//更新component
	public Component updateComponent(Component c);
	//查询对应的component
	public List<Component> searchComponent(String name);
	//检验是否存在否个component
	public int checkComponent(String name);
	public List getnomodulenodesByParent(int parseInt, String ids);
}
