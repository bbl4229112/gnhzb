/**
 * 
 */
package prefuse.option;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import prefuse.data.Node;
import prefuse.data.Tree;


/**
 * @author HE BI
 * 
 */
public class NewsManage {
	
	static ArrayList sqllist=new ArrayList();
	 
     public void Ordercategory(Tree tree,Node n,String userid,String dbname) throws SQLException, ClassNotFoundException{
    	
    	 
    	 String t="delete from usercategory where userid="+userid;
    	 System.out.println(t);
 		sqllist.add(t);
 		 String id = n.getString("categoryId");
 		t="insert into usercategory (userid,categoryid)"
 				+"values( "+userid+" ,"+id+")";
     		sqllist.add(t);
     	//	 System.out.println(t);
     
 		getNodes2(tree,n,userid);
// 	     Iterator children=n.children();
// 	    while(children.hasNext()){
// 	    	
// 	    	Node child=	(Node)children.next();	
// 	    	
// 	    id = child.getString("categoryId");
//   		t="insert into usercategory (userid,categoryid)"
//   			+"values( "+userid+" ,"+id+")";
//      		sqllist.add(t);
// 	   System.out.println(t);
// 	    } 

 		
    	 
    	 Connection cnn =null;
     	   Statement stmt = null;
    	
           try {
        	   cnn= Dbutil.getDBconn(dbname); // 获取连接句柄
               cnn.setAutoCommit(false); // 设置事务处理标志
               stmt = cnn.createStatement();
        for(int i=0;i<sqllist.size();i++){
        	System.out.println("sqllist="+(String)sqllist.get(i));
           	stmt.addBatch((String)sqllist.get(i));
        }stmt.executeBatch();
          sqllist.clear();
		cnn.commit();
       	} catch (SQLException e) {
			throw new SQLException("database error:" + e.toString());
		} finally {
		// rs.close();
			stmt.close();
			cnn.close();
		}

       }
    	
     public void CancelOrdercategory(Tree tree,Node n,String userid,String dbname) throws SQLException, ClassNotFoundException{
    	 String id = n.getString("categoryId");
    	 String t="delete from usercategory where userid="+userid+" and categoryid="+id;
    	 if(!id.equals("0"))
 		 {		sqllist.add(t);}
    		getNodes(tree,n,userid);
    	 Connection cnn =null;
    	
 
    	   Statement stmt = null;
    	
           try {
        	   cnn= Dbutil.getDBconn(dbname); // 获取连接句柄
               cnn.setAutoCommit(false); // 设置事务处理标志
               stmt = cnn.createStatement();
        for(int i=0;i<sqllist.size();i++){
        	System.out.println("sqllist="+(String)sqllist.get(i));
           	stmt.addBatch((String)sqllist.get(i));
        }stmt.executeBatch();
        sqllist.clear();
		cnn.commit();
       	} catch (SQLException e) {
			throw new SQLException("database error:" + e.toString());
		} finally {
		// rs.close();
			stmt.close();
			cnn.close();
		}

       }
     public boolean hasordered(String id,String userid,String dbname) throws SQLException, ClassNotFoundException{
    	// String id = n.getString("categoryId");
    	
    	 Connection cnn =null;
    	   Statement stmt = null;
    		ResultSet rs = null;
           try {
        	   cnn= Dbutil.getDBconn(dbname); // 获取连接句柄
              
               stmt = cnn.createStatement();
               String sql="select * from usercategory where userid="+userid+" and categoryid="+id;
   			System.out.println("@@@sql="+sql);
   			rs = stmt.executeQuery(sql);
   			if(rs.next())
   			return true;
   			else 
   		    return false;
       	} catch (SQLException e) {
			throw new SQLException("database error:" + e.toString());
		} finally {
		   rs.close();
			stmt.close();
			cnn.close();
		}

       }
     
    public void getNodes(Tree tree,Node n,String userid) throws SQLException, ClassNotFoundException
    
    {	for (Iterator iter=tree.children(n);iter.hasNext();) {
		Node node = (Node) iter.next();
		String id = node.getString("categoryId");
		System.out.println("name="+node.getString("name"));
		System.out.println("noid="+id);
		String t="delete from usercategory where userid="+userid+" and categoryid="+id;
		sqllist.add(t);
		getNodes(tree,node,userid);
	}
    	
    	
    }
 public void getNodes2(Tree tree,Node n,String userid) throws SQLException, ClassNotFoundException
    
    {	for (Iterator iter=tree.children(n);iter.hasNext();) {
		Node node = (Node) iter.next();
		String id = node.getString("categoryId");
		System.out.println("name="+node.getString("name"));
		System.out.println("noid="+id);
		String t="insert into usercategory (userid,categoryid)"
								+"values( "+userid+" ,"+id+")";
		sqllist.add(t);
		getNodes2(tree,node,userid);
	}
    	
    	
   }
    	 
}
    	 
   
