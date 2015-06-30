package wsdl;



import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import wsdl.*;

import javax.xml.namespace.QName;

public class PortTypeImplm implements Serializable, Externalizable{

	public String namespaceURI;
	public String localPart;
    public String prefix;
    public String operations;

    //public List<OperationImplm> operations = new ArrayList<OperationImplm>();
    //public OperationImplm operation;

	public PortTypeImplm(){
		operations ="";
	}

	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException {
		// Read in the server properties from the client representation.
		namespaceURI = (String)in.readObject();
		localPart = (String)in.readObject();
		prefix = (String)in.readObject();
		operations = (String) in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		 out.writeObject(namespaceURI);
         out.writeObject(localPart);
         out.writeObject(prefix);
         out.writeObject(operations);
	}


	public void addOperation(String operation){
		if(this.operations!="")
			this.operations = this.operations+";";
		this.operations = this.operations+operation;
	}


	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getOperations() {
		return this.operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}



}
