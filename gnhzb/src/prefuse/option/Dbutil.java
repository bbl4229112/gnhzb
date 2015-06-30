/**
 * 
 */
package prefuse.option;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import prefuse.data.io.sql.ConnectionFactory;
import prefuse.data.io.sql.DatabaseDataSource;

/**
 * @author HE BI
 *
 */
public class Dbutil {
	
	 public DatabaseDataSource getDBsource(String dbname,String dbhost) throws SQLException, ClassNotFoundException{
	   	String driverName   = "com.mysql.jdbc.Driver";    
        String dbURL        = "jdbc:mysql://"+dbhost+":3306/"+dbname;    
        
   //     System.out.println("dburl="+dbURL);
        String userName     =dbname;   
        String userPwd      =  dbname; 
		DatabaseDataSource datasrc = null;
		
      datasrc = ConnectionFactory.getDatabaseConnection(    
              driverName, dbURL, userName, userPwd);  
      return datasrc;

}
	 public DatabaseDataSource getDBsource() throws SQLException, ClassNotFoundException{
		   	String driverName   = "com.mysql.jdbc.Driver";    
	        String dbURL        = "jdbc:mysql://localhost:3306/haierpl";    
	        String userName     = "haierpl";   
	        String userPwd      =  "haierpl"; 
			DatabaseDataSource datasrc = null;
			
	      datasrc = ConnectionFactory.getDatabaseConnection(    
	              driverName, dbURL, userName, userPwd);  
	      return datasrc;

	}
	 public static Connection getDBconn(String dbname) throws SQLException, ClassNotFoundException{
		   	String driverName   = "com.mysql.jdbc.Driver";    
	        String dbURL        = "jdbc:mysql://localhost:3306/"+dbname;    
	        String userName     = dbname;    
	        String userPwd      = dbname; 
		
			 Class.forName(driverName);
		     Connection conn = DriverManager.getConnection(dbURL, userName, userPwd);
	     
	      return conn;
	   
	}
	 
	
}