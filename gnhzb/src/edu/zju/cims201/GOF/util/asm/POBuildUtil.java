package edu.zju.cims201.GOF.util.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.WordUtils; 

import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.util.Assistant;
import org.objectweb.asm.ClassWriter;   
import org.objectweb.asm.MethodVisitor;   
import org.objectweb.asm.Opcodes;  

/**
 * POJO生成工作
 * @author courser.tijichen
 */
public class POBuildUtil extends ClassLoader implements Opcodes {
	
	/**
	 * 根据传入参数创建CLASS文件
	 *
	 * @param 类名
	 * @param 保存路径
	 * @param 属性描述
	 * @return Class
	 */
	public  Class build(String clsname,String savepath,Collection<Property> properties)
	{
		Class cls = null;
		try
		{
			String classname = BuildUtil.transferClassName(clsname);
			ClassWriter cw = new ClassWriter(0);   
			cw.visit(V1_1, ACC_PUBLIC, classname, null, "edu/zju/cims201/GOF/hibernate/pojo/MetaKnowledge", new String[]{"edu/zju/cims201/GOF/hibernate/pojo/Knowledge","java/io/Serializable"});
			//建立构造函数
			MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);   
			mw.visitVarInsn(ALOAD, 0);   
			mw.visitMethodInsn(INVOKESPECIAL, "edu/zju/cims201/GOF/hibernate/pojo/MetaKnowledge", "<init>", "()V");   
			mw.visitInsn(RETURN);   
			mw.visitMaxs(1, 1);   
			mw.visitEnd();
			
			BuildProperty property = null;
			String propertytype = null;
			String propertyname = null;;
			//建立属性
			Iterator iterator = properties.iterator();
			while (iterator.hasNext())
			{
				//建立属性对应的类变量
				property = (BuildProperty)(iterator.next());
				if(!property.getName().equals("knowledgetype"))
				{
				propertytype = BuildUtil.transferClassName(property.getPropertyType());
				propertyname = WordUtils.capitalize(property.getName());
				cw.visitField(ACC_PRIVATE, property.getName(), "L"+propertytype+";", null, null).visitEnd();
				//建立get方法
			    mw = cw.visitMethod(ACC_PUBLIC, "get"+propertyname, "()L"+propertytype+";", null,null); 
			    mw.visitCode(); 
			    mw.visitVarInsn(ALOAD, 0); 
			    mw.visitFieldInsn(GETFIELD, classname, property.getName(), "L"+propertytype+";"); 
			    mw.visitInsn(ARETURN); 
			    mw.visitMaxs(1, 1); 
			    mw.visitEnd(); 
			    //建立set方法
			    mw = cw.visitMethod(ACC_PUBLIC, "set"+propertyname, "(L"+propertytype+";)V", null, null); 
			    mw.visitCode(); 
			    mw.visitVarInsn(ALOAD, 0); 
			    mw.visitVarInsn(ALOAD, 1); 
			    mw.visitFieldInsn(PUTFIELD, classname, property.getName(),"L"+propertytype+";"); 
			    mw.visitMaxs(2, 2); 
			    mw.visitInsn(RETURN); 
			    mw.visitEnd(); 
			}
			}
			
			cw.visitEnd();

		    byte[] code = cw.toByteArray(); 
			if (savepath!=null)
			{   
				
				Assistant.createNewFileFromWeb(savepath);
			    FileOutputStream fos = new FileOutputStream(savepath);   
			    fos.write(code);   
			    fos.close();   
			}
			//web方式下出错，先注释掉
			//cls = this.defineClass(clsname, code, 0, code.length);
			return cls;
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return cls;
	}
}
