package edu.zju.cims201.GOF.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;

public class Word2FlashTest extends TestCase {
	
	private synchronized int convertDOC2SWF(String sourcePath, String realPath, String fileName){
		try {
			String command = realPath + "bin\\FlashPrinter.exe "
					+ sourcePath + " -o "
					+ sourcePath.substring(0, sourcePath.lastIndexOf("/"))
					+ "/" + fileName;
			System.out.println("command:"+command);
			Process pro = Runtime.getRuntime().exec(command);

			return pro.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	

}
	@Test
	public void testConvert()
	{
		System.out.println("convert");
		convertDOC2SWF("c:/test.doc","D:/workspace/caltks/","test.swf");
		
	}
	static void main(Object[] arg)
	{
	System.out.println("test");	
	}
}