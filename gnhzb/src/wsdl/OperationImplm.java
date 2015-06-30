package wsdl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.List;

public class OperationImplm implements Serializable, Externalizable{

	public String name;

	public OperationImplm(){

	}

	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException {
		// Read in the server properties from the client representation.
		name = (String)in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		 out.writeObject(name);
	}


	public OperationImplm(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void SetName(String name){
		this.name = name;
	}
}
