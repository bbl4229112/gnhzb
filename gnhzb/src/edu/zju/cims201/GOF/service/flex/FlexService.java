package edu.zju.cims201.GOF.service.flex;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.KeepTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

public interface FlexService {
	public List<SystemUser> list();
	public List<KeepTreeNode> listBookmark();
}

