package wsdl;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Definition;
import javax.wsdl.Operation;
import javax.wsdl.PortType;
import javax.wsdl.WSDLException;
import javax.wsdl.xml.WSDLReader;
import javax.wsdl.factory.WSDLFactory;
import org.xml.sax.*;

import server.Navigator;

import javax.xml.namespace.QName;

import com.ibm.wsdl.OperationImpl;
import com.ibm.wsdl.PortTypeImpl;

import wsdl.*;


public class Wsdl extends Navigator{

	/*
	 * Constructor
	 */
	public Wsdl(){
		super();
	}

	public  List getWsdlPortTypeByUri( String wsdlURI ){

		WSDLFactory fact;
		WSDLReader read;
		Definition def;
		Map map;

		String dir = this.getAppPath(this.getClass());
		dir = dir.replace('/', File.separatorChar);
		dir = dir.substring(0, dir.lastIndexOf(File.separator));
		dir = dir.substring(0, dir.lastIndexOf(File.separator));

		wsdlURI = dir + File.separator + wsdlURI;
		wsdlURI = wsdlURI.replace('\\', File.separatorChar);
		try {
			fact = WSDLFactory.newInstance();
			read = fact.newWSDLReader();
			def = read.readWSDL(wsdlURI);
			map = def.getPortTypes();

			PortType p = new PortTypeImpl() ;
			QName qname = p.getQName();
			//Operation op = p.getOperation(wsdlURI, wsdlURI, wsdlURI);
			//op.getName()

			PortTypeImplm portType;
			OperationImplm operation;
			List<PortTypeImplm> portTypeList = new ArrayList<PortTypeImplm>();
			List<OperationImplm> operationList = new ArrayList<OperationImplm>();

			Set s = map.entrySet();
			Iterator it = s.iterator();
			while(it.hasNext())
	        {
	            // key=value separator this by Map.Entry to get key and value
	            Map.Entry m = (Map.Entry)it.next();

	            // getKey is used to get key of Map
	            Object key = m.getKey();

	            // getValue is used to get value of key in Map
	            PortType pType = (PortTypeImpl)m.getValue();

	            Operation op ;
	            portType = new PortTypeImplm();
	            portType.setLocalPart(pType.getQName().getLocalPart());
	            for(int i=0; i<pType.getOperations().size(); i++){
	            	op = (OperationImpl)pType.getOperations().get(i);
	            	operation = new OperationImplm(op.getName());
	            	portType.addOperation(operation.name);
	            	System.out.println("operation.getname = "+operation.name);
	            }
	            System.out.println("operation.getnames = "+portType.operations);
	            portTypeList.add(portType);


	        }
			System.out.println("portTypeList size_________________: "+portTypeList.get(0));
			//return map;

			//return (((wsdl.PortTypeImplm)portTypeList.get(0)).getOperation().get(0));
			return portTypeList;


		} catch (WSDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("_______________________________null ");
		return null;

	}



	public  List getWsdlPortTypeBycaracterStream( Reader caracterStream ){

		WSDLFactory fact;
		WSDLReader read;
		Definition def;
		Map map;

		try {
			fact = WSDLFactory.newInstance();
			read = fact.newWSDLReader();
			def = read.readWSDL(null,new InputSource(caracterStream));
			map = def.getPortTypes();

			wsdl.PortTypeImplm portType;
			wsdl.OperationImplm operation;
			List<wsdl.PortTypeImplm> portTypeList = new ArrayList<wsdl.PortTypeImplm>();
			List<wsdl.OperationImplm> operationList = new ArrayList<wsdl.OperationImplm>();

			Set s = map.entrySet();
			Iterator it = s.iterator();
			while(it.hasNext())
	        {
	            // key=value separator this by Map.Entry to get key and value
	            Map.Entry m = (Map.Entry)it.next();

	            // getKey is used to get key of Map
	            Object key = m.getKey();

	            // getValue is used to get value of key in Map
	            PortType pType = (PortTypeImpl)m.getValue();

	            Operation op ;
	            portType = new PortTypeImplm();
	            for(int i=0; i<pType.getOperations().size(); i++){
	            	op = (OperationImpl)pType.getOperations().get(i);
	            	operation = new OperationImplm(op.getName());
	            	portType.addOperation(operation.name);
	            	System.out.println("operation.getname = "+operation.name);
	            }
	            portTypeList.add(portType);

	           // System.out.println("Key :"+key+"  Value :"+value);
	        }

			return portTypeList;

		} catch (WSDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return null;

	}



	/**
	 * Main application
	 * @param args
	 */
	public static void main(String[] args) {
		Wsdl wsdl = new Wsdl();
		String wsdlURI = "http://localhost:8080/juddiv3/services/juddi-api?wsdl";
//		Map map = wsdl.getWsdlPortTypeByUri(wsdlURI);
//		//System.out.println("map size: "+map.size());
//		//System.out.println("map : "+map);
//
//		Set s = map.entrySet();
//		Iterator it = s.iterator();
//		while(it.hasNext())
//        {
//            // key=value separator this by Map.Entry to get key and value
//            Map.Entry m =(Map.Entry)it.next();
//
//            // getKey is used to get key of Map
//            Object key=m.getKey();
//
//            // getValue is used to get value of key in Map
//            PortType value = (PortTypeImpl)m.getValue();
//
//            System.out.println("Key :"+key+"  Value :"+value);
//        }


	}





}
