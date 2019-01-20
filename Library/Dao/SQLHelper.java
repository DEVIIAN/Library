package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLHelper {
	    static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	    static String
	            url="jdbc:sqlserver://127.0.0.1:1433;databaseName=Library",
	            user="sa",pwd="huanhuan20162151";
	    private static Connection conn=null;
	    static{
	        try{
	            Class.forName(driver);
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	    }
	    /*执行cuide语句*/
	    public static int executeUpdate(String sql){
	        int r=0;
	        try{
	            conn=DriverManager.getConnection(url,user,pwd);
	            Statement cmd=conn.createStatement();
	            r=cmd.executeUpdate(sql);
	            conn.close();
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return r;
	    }
	    /*执行查找语句，返回唯一值*/
	    public static Object executeSingleQuery(String singleSQL){
	        Object r=null;
	        ResultSet rs=null;
	        try{
	            conn=DriverManager.getConnection(url,user,pwd);
	            Statement cmd=conn.createStatement();
	            rs=cmd.executeQuery(singleSQL);
	            if(rs.next()){
	                r=rs.getObject(1);
	            }
	            conn.close();
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return r;
	    }
	    /*执行select语句*/
	    public static ResultSet executeQuery(String sql){
	        ResultSet rs=null;
	        try{
	            conn=DriverManager.getConnection(url,user,pwd);
	            Statement cmd=conn.createStatement();
	            rs=cmd.executeQuery(sql);
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return rs;
	    }

	    /*关闭数据库连接*/
	    public static void closeConnection(){
	        try{
	            if(conn!=null && !conn.isClosed()){
	                conn.close();
	            }
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	    }
}
