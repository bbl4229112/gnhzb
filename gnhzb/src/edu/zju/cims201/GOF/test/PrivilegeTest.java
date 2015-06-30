package edu.zju.cims201.GOF.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.rs.dto.TripleDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;

public class PrivilegeTest extends BaseActionTest {
	
	private PrivilegeService privilegeService;
	private ApplicationContext ctx = null;  
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = getApplicationContext();  
		privilegeService=(PrivilegeService) ctx.getBean("privilegeServiceImpl"); 
		
	}
	public void testFind() throws SQLException{
		OperationRight a=privilegeService.getOperationRight("A");
		System.out.println(a.getOperationRightName());
		
//		Set<TripleDTO> rs = privilegeService.listRolePrivilegeTriples(new Long(1), "A");
		
		
	}

}
