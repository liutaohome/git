package lyp.dao;

import java.sql.*;
import javax.sql.RowSet;
import javax.sql.rowset.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class jdbcHelp 
{ 
	Connection con = null; 
    public jdbcHelp(String drive,String url) throws Exception 
	{
		try 
		{ 
            Class.forName(drive); 
            con=DriverManager.getConnection(url);
        } 
		catch (ClassNotFoundException e) 
		{ 
            throw new ExceptionInInitializerError(e); 
        } 
	}
	//
    public int executeNoQuery(String url,String sql) throws Exception 
    {
    	PreparedStatement pst=null;
		ResultSet rs=null;
		int rows=-1;
		try 
        { 
            pst = con.prepareStatement(sql);
            rows=pst.executeUpdate(); 
        } 
        finally 
        { 
        	if (rs!=null) rs.close();
            if (pst!=null) pst.close();
        	if (con!=null)
        	{
        		try {con.close();}
        	    catch (Exception ignore) {}  
        	}
        } 
		return rows;
    }
    //
    public HashMap<String,Object> execute(String url,HashMap<String,String> sqls) throws Exception 
    { 
    	PreparedStatement pst=null;
		ResultSet rs=null;
		HashMap<String,Object> results=new HashMap<String,Object>();
		try 
        { 
            int counts=0;
			pst = con.prepareStatement("");
            pst.execute(); 
            while (true) 
            {
            	int rowCount = pst.getUpdateCount();counts++;
            	if (rowCount > 0) //insert,delete,update
            	{ 
            		results.put(counts+":update",rowCount);
            		pst.getMoreResults();
            		continue;
            	}
            	if (rowCount == 0) //DDL 命令或 0 个更新
            	{ 
            		results.put(counts+":update",rowCount);
            		pst.getMoreResults();
            		continue;
            	}
                // 执行到这里，证明有一个结果集或没有其它结果
                rs = pst.getResultSet();
            	if (rs != null) 
            	{
            	// 使用元数据获得关于结果集列的信息
            	while (rs.next()) 
            	{
            	// 处理结果
            		
            	
            	}
            	pst.getMoreResults();
            	continue;
            	}
            	break; // 没有其它结果
            	}
        } 
        finally 
        { 
        	if (rs!=null) rs.close();
            if (pst!=null) pst.close();
        	if (con!=null)
        	{
        		try {con.close();}
        	    catch (Exception ignore) {}  
        	}
        } 
        return results;
    } 
}
