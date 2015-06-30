package edu.zju.cims201.GOF.service.privilege;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.HibernateUtils;
import edu.zju.cims201.GOF.dao.privilege.RolePrivilegeTripleDao;
import edu.zju.cims201.GOF.rs.dto.TripleDTO;


@Service


public class SpringSecurityServiceImpl implements SpringSecurityService {

public void findDirectNodes(Long knowledgeID,Set<Long> directCNodeIDS,Set<Long> directDNodeIDS,Connection connection){
		
		ResultSet res1=null;
		ResultSet res2=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {
			
			ps = connection.prepareStatement("SELECT b.domain_nodeid dnode FROM " +
						" CALTKS_META_KNOWLEDGE b where  b.id=?");
			ps.setLong(1, knowledgeID);
			res1 = ps.executeQuery();
			while(res1.next()){
				directDNodeIDS.add(res1.getLong(1));
			}
			
			
			
			ps1 = connection.prepareStatement("SELECT a.category_tree_nodeid cnode FROM " +
			" CALTKS_KNOWLEDGE_CATEGORIES  a where  a.knowledgeid=?");
			ps1.setLong(1, knowledgeID);
			res2 = ps.executeQuery();
			while(res1.next()){
				directCNodeIDS.add(res2.getLong(1));
}
			
			
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res1!=null)
					res1.close();
				if(ps!=null)
					ps.close();
				
				if(res2!=null)
					res2.close();
				if(ps1!=null)
					ps1.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	


	public Set<TripleDTO> getRoleTriples(String operationName,Set<Long> allcdNodeIDS,Set<Long> allrNodeIDS,Connection connection){
		
		
		ResultSet res=null;
		PreparedStatement ps=null;
		Set<TripleDTO> dtos=new HashSet<TripleDTO>();
		

		try {
			String inString=null;
			String sql=null;
			if(operationName!=null&&allcdNodeIDS!=null&&allrNodeIDS==null){
				
				if(allcdNodeIDS.size()==0)
					return dtos;
				
				inString="(";
				for(Long id:allcdNodeIDS){
					inString+=id+",";
				}
				inString=inString.substring(0, inString.length()-1);
				inString+=")";	
				sql="SELECT  a.role_tree_nodeid,a.cd_tree_nodeid,a.operation_rightid FROM CALTKS_ROLE_PRIVILEGE_TRIPLE a,CALTKS.CALTKS_OPERATION_RIGHT right " +
				"where " +
				"a.role_tree_nodeid is not null and a.cd_tree_nodeid is not null and a.operation_rightid is not null and " +
				" a.operation_rightid=right.id and right.code=? and a.cd_tree_nodeid in "+inString;
				//System.out.println(sql);
				ps = connection.prepareStatement(sql);
				ps.setString(1, operationName);
			}else if(operationName==null&&allcdNodeIDS==null&&allrNodeIDS!=null){//通过角色树查
				
				if(allrNodeIDS.size()==0)
					return dtos;
				inString="(";
				for(Long id:allrNodeIDS){
					inString+=id+",";
				}
				inString=inString.substring(0, inString.length()-1);
				inString+=")";	
				sql="SELECT  a.role_tree_nodeid,a.cd_tree_nodeid,a.operation_rightid FROM CALTKS_ROLE_PRIVILEGE_TRIPLE a " +
				"where a.role_tree_nodeid is not null and a.cd_tree_nodeid is not null and a.operation_rightid is not null and " +
				" a.role_tree_nodeid in "+inString;
				//System.out.println(sql);
				ps = connection.prepareStatement(sql);
			}
			
			res=ps.executeQuery();
			
			while(res.next()){
				TripleDTO tripleDTO=new TripleDTO(res.getLong(1),res.getLong(2),res.getLong(3));
				dtos.add(tripleDTO);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		return dtos;
	}
	
	public Set<TripleDTO> getUserTriples(String operationName,Set<Long> allNodeIDS,Connection connection){
		
		ResultSet res=null;
		PreparedStatement ps=null;
		Set<TripleDTO> dtos=new HashSet<TripleDTO>();
		

		try {
			
			if(allNodeIDS.size()!=0){
				
				String inString="(";
				for(Long id:allNodeIDS){
					inString+=id+",";
				}
				inString=inString.substring(0, inString.length()-1);
				inString+=")";
				String sql="SELECT  a.USERID,a.CD_TREE_NODEID,a.OPERATION_RIGHTID FROM CALTKS_USER_PRIVILEGE_TRIPLE a,CALTKS_OPERATION_RIGHT right " +
				"where a.USERID is not null and a.CD_TREE_NODEID is not null and a.OPERATION_RIGHTID is not null " +
				"and a.OPERATION_RIGHTID=right.id and right.code=? and a.CD_TREE_NODEID in "+inString;
				
				//System.out.println(sql);
				ps = connection.prepareStatement(sql);
				ps.setString(1, operationName);
				res=ps.executeQuery();
				
				while(res.next()){
					TripleDTO tripleDTO=new TripleDTO(res.getLong(1),res.getLong(2),res.getLong(3));
					dtos.add(tripleDTO);
				}
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		return dtos;
	}
	
	
	
	public Set<Long> listDirectRoleNodes(Long userID,Connection connection){
		
		Set<Long> directRoles=new HashSet<Long>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="SELECT a.role_tree_nodeid " +
			"FROM CALTKS_USER_ROLE_NODES a where a.userid=?";
			ps = connection.prepareStatement(sql);
			ps.setLong(1, userID);
			rs=ps.executeQuery();
			while(rs.next()){
				directRoles.add(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return directRoles;
		
	}
	
	
	public Set<Long> listParentNodes(Set<Long> directRoles,Connection connection){
		Set<Long> allRoles=new HashSet<Long>();
		
		ResultSet rs1=null;
		PreparedStatement ps1=null;
		try {
			
			if(directRoles.size()==0)
				return allRoles;
			
			String inString="(";
			for(Long id:directRoles){
				inString+=id+",";
			}
			inString=inString.substring(0, inString.length()-1);
			inString+=")";
			ps1 = connection.prepareStatement("select a.id from CALTKS_TREE_NODE a where a.id in "+inString+" and a.parent_id in (select id from CALTKS_TREE_NODE)");
			rs1=ps1.executeQuery();
			while(rs1.next()){
				allRoles.add(rs1.getLong(1));
		
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs1!=null)
					rs1.close();
				if(ps1!=null)
					ps1.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return allRoles;
		
	}
	
	public Set<TripleDTO> getUserTriples(Long userID,Connection connection){
		if(userID==null)
			return null;
		ResultSet res=null;
		PreparedStatement ps=null;
		Set<TripleDTO> dtos=new HashSet<TripleDTO>();
		String sql="SELECT  a.USERID,a.CD_TREE_NODEID,a.OPERATION_RIGHTID FROM CALTKS_USER_PRIVILEGE_TRIPLE a " +
		"where a.USERID is not null and a.CD_TREE_NODEID is not null and a.OPERATION_RIGHTID is not null  and a.USERID=? ";
		//System.out.println(sql);
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, userID);
			res=ps.executeQuery();
			
			while(res.next()){
				TripleDTO tripleDTO=new TripleDTO(res.getLong(1),res.getLong(2),res.getLong(3));
				dtos.add(tripleDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return dtos;
		
		
	}



	public Set<Long> listDirectAdminNodes(Long adminID, Connection connection) {
		if(adminID==null)
			return null;
		ResultSet res=null;
		PreparedStatement ps=null;
		Set<Long> directNodes=new HashSet<Long>();
		String sql="SELECT a.tree_nodeid FROM CALTKS_ADMIN_PRIVILEGE_TRIPLE a where a.adminid=?";
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, adminID);
			res=ps.executeQuery();
			
			while(res.next()){
				directNodes.add(res.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return directNodes;
	}



	public Set<Long> listSubNodes(Set<Long> directNodes, Connection connection) {
		Set<Long> allSubNodes=new HashSet<Long>();
		if(directNodes.size()==0)
			return allSubNodes;
		
		ResultSet rs1=null;
		PreparedStatement ps1=null;
		try {
			
			if(directNodes.size()==0)
				return allSubNodes;
			
			String inString="(";
			for(Long id:directNodes){
				inString+=id+",";
			}
			inString=inString.substring(0, inString.length()-1);
			inString+=")";
			ps1 = connection.prepareStatement("select id from CALTKS_TREE_NODE id start with id in"+inString+" connect by prior id = parent_id");
			rs1=ps1.executeQuery();
			while(rs1.next()){
				allSubNodes.add(rs1.getLong(1));
		
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs1!=null)
					rs1.close();
				if(ps1!=null)
					ps1.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return allSubNodes;
	}



	public Set<TripleDTO> getAdminTriples(String operationName, Set<Long> allTreeNodeIDS, Long adminID,Set<Long> allAdminNodeIDS, Connection connection) {
		
		
		ResultSet res=null;
		PreparedStatement ps=null;
		Set<TripleDTO> dtos=new HashSet<TripleDTO>();
		String inString=null;
		String sql=null;
		
		try {
			if(operationName==null&&allTreeNodeIDS==null&&adminID!=null&&allAdminNodeIDS!=null){
				if(allAdminNodeIDS.size()==0)
					return dtos;
				
				inString="(";
				for(Long id:allAdminNodeIDS){
					inString+=id+",";
				}
				inString=inString.substring(0, inString.length()-1);
				inString+=")";	
				sql="SELECT  a.adminid,a.tree_nodeid,a.admin_operation_rightid FROM CALTKS_ADMIN_PRIVILEGE_TRIPLE a " +
						"where a.adminid is not null and a.tree_nodeid is not null and a.admin_operation_rightid is not null and " +
						"a.adminid=? and a.tree_nodeid in" +inString;
				//System.out.println(sql);
				ps = connection.prepareStatement(sql);
				ps.setLong(1, adminID);
			}else if(operationName!=null&&allTreeNodeIDS!=null&&adminID==null&&allAdminNodeIDS==null){
				
				if(allTreeNodeIDS.size()==0)
					return dtos;
				inString="(";
				for(Long id:allTreeNodeIDS){
					inString+=id+",";
				}
				inString=inString.substring(0, inString.length()-1);
				inString+=")";
				
				sql="SELECT  a.adminid,a.tree_nodeid,a.admin_operation_rightid FROM CALTKS_ADMIN_PRIVILEGE_TRIPLE a,CALTKS_OPERATION_RIGHT b " +
						"where a.adminid is not null and a.tree_nodeid is not null and a.admin_operation_rightid is not null and " +
						"a.admin_operation_rightid=b.id and b.code=? and a.tree_nodeid in" +inString;
				//System.out.println(sql);
				ps = connection.prepareStatement(sql);
				ps.setString(1, operationName);
			}
			res=ps.executeQuery();
			
			while(res.next()){
				TripleDTO tripleDTO=new TripleDTO(res.getLong(1),res.getLong(2),res.getLong(3));
				dtos.add(tripleDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return dtos;
	}
	
	
	public Long getKnowledgeID(Long attachId,Connection connection){
		
		
		if(attachId==null)
			return null;
		ResultSet res=null;
		PreparedStatement ps=null;
		String sql="SELECT C.KNOWLEDGEID FROM CALTKS_ATTACHMENT C where C.ID=?";
		Long result=null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, attachId);
			res=ps.executeQuery();
			
			if(res.next()){
				result=res.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	
		return result;
	}
	
	public Long getKnowledgeID(String swfPath,Connection connection){
		
		if(swfPath==null)
			return null;
		
		ResultSet res=null;
		PreparedStatement ps=null;
		String sql="SELECT C.ID FROM CALTKS_META_KNOWLEDGE C where C.FLASH_FILE_PATH like '%"+swfPath+"%'";
		//System.out.println(sql);
		Long result=null;
		try {
			ps = connection.prepareStatement(sql);
			res=ps.executeQuery();
			
			if(res.next()){
				result=res.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null)
					res.close();
				if(ps!=null)
					ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		return result;
		
	}



}
