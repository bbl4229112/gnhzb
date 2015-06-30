package edu.zju.cims201.GOF.util;
import javax.servlet.http.HttpServletResponse;

import org.stringtree.json.ExceptionErrorListener;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;
import org.stringtree.json.JSONValidatingWriter;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.service.ServiceException;
public class JSONUtil {
	
public static Object read(String json)
{  
	//System.out.println(json);
	JSONReader jr=new JSONValidatingReader(new ExceptionErrorListener());
	try{

	return jr.read(json);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new ServiceException("json数据格式不正确");
		
	}
}	
public static void write(HttpServletResponse response,Object o)
{

		  JSONWriter jw = new JSONValidatingWriter(
		    new ExceptionErrorListener());


	
	try{
	
	response.getWriter().print(jw.write(o));
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new ServiceException("对象类型出错无法转换成json数据格式");
		
	}
}	
	
public static String write(Object o)
{

		  JSONWriter jw = new JSONValidatingWriter(
		    new ExceptionErrorListener());


	
	try{
	return jw.write(o);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new ServiceException("对象类型出错无法转换成json数据格式");
		
	}
}	
}
